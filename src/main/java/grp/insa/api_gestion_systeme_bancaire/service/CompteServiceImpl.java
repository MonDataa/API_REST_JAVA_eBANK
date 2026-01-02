package grp.insa.api_gestion_systeme_bancaire.service;

import grp.insa.api_gestion_systeme_bancaire.dto.*;
import grp.insa.api_gestion_systeme_bancaire.entities.*;
import grp.insa.api_gestion_systeme_bancaire.entities.enumerateur.typeSource;
import grp.insa.api_gestion_systeme_bancaire.entities.enumerateur.typeTransaction;
import grp.insa.api_gestion_systeme_bancaire.exception.BalanceNotSufficientException;
import grp.insa.api_gestion_systeme_bancaire.mappers.CarteMapperImpl;
import grp.insa.api_gestion_systeme_bancaire.mappers.CompteMapperImpl;
import grp.insa.api_gestion_systeme_bancaire.mappers.TransactionMapperImpl;
import grp.insa.api_gestion_systeme_bancaire.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class CompteServiceImpl implements CompteService {
    private CarteRepositories carteRepositories;
    private CarteMapperImpl carteMapper;
    private TransactionRepositories transactionRepositories;
    private TransactionMapperImpl transactionMapper;
    private CompteRepositories compteRepositories;
    private CompteMapperImpl compteMapper;
    private ClientRepositories clientRepositories;
    private VirementRepositories virementRepositories;
    private static final int ACCOUNT_NUMBER_LENGTH = 10; // Longueur standard pour l'exemple


    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepositories.save(transaction);
    }

    @Override
    public Carte saveCarte(Carte carte) {
        return carteRepositories.save(carte);
    }


    public int calculerCleRib(String codeBanque, String codeGuichet, String numeroCompte) {
        String ribKey = codeBanque + codeGuichet + numeroCompte;
        long total = 0;
        for (int i = 0; i < ribKey.length(); i++) {
            int numericValue = Character.getNumericValue(ribKey.charAt(i));
            if (numericValue == -1) {
                throw new IllegalArgumentException("Invalid RIB characters");
            }
            total = (total * 10 + numericValue) % 97;
        }
        return 97 - (int) total;
    }

    @Override
    public void addCompteToTransaction(Long id, String intituleCompte) {
        Transaction transaction = transactionRepositories.findTransactionById(id);
        if (transaction == null) {
            throw new RuntimeException("transaction non trouvée pour l'id : " + id);
        }

        Compte compte = compteRepositories.findCompteByIntituleCompte(intituleCompte);
        if (compte == null) {
            throw new RuntimeException("Compte non trouvé avec l'intitulé : " + intituleCompte);
        }
        transaction.setCompte(compte);
        transactionRepositories.save(transaction);
    }

    @Override
    public void addCompteToCarte(String titulaireCarte, String intituleCompte) {
        Carte carte = carteRepositories.findCarteByTitulaireCarte(titulaireCarte);
        if (carte == null) {
            throw new RuntimeException("Carte non trouvée pour le titulaire : " + titulaireCarte);
        }

        Compte compte = compteRepositories.findCompteByIntituleCompte(intituleCompte);
        if (compte == null) {
            throw new RuntimeException("Compte non trouvé avec l'intitulé : " + intituleCompte);
        }

        carte.setCompte(compte);

        carteRepositories.save(carte);
    }

    @Override
    public List<CompteDTO_r_cocl> getComptesParClient(Long clientId) {
        List<Compte> comptes = compteRepositories.findByClientId(clientId); // Implement in the repository

        return comptes.stream().map(compte -> {
            CompteDTO_r_cocl compteDTO = new CompteDTO_r_cocl();
            compteDTO.setIban(compte.getIban());
            compteDTO.setSolde(compte.getSolde());
            compteDTO.setIntituleCompte(compte.getIntituleCompte());
            compteDTO.setTypeCompte(compte.getTypeCompte());

            List<ClientIdDTO> titulaires = compte.getCompteClients().stream()
                    .map(client -> new ClientIdDTO(client.getId()))
                    .collect(Collectors.toList());
            compteDTO.setTitulairesCompte(titulaires);

            List<TransactionDTO> transactions = compte.getTransactions().stream()
                    .map(transaction -> new TransactionDTO(
                            transaction.getId(),
                            transaction.getMontant(),
                            transaction.getTypeTransaction(),
                            transaction.getTypeSource(),
                            transaction.getIdSource()))
                    .collect(Collectors.toList());
            compteDTO.setTransactions(transactions);

            return compteDTO;
        }).collect(Collectors.toList());
    }

    public List<CarteDTO> getCartesParIban(String iban) {
        Compte compte = compteRepositories.findByIban(iban);
        if (compte == null) {
            throw new EntityNotFoundException("Compte non trouvé avec l'IBAN : " + iban);
        }

        List<Carte> cartes = compte.getCartes();

        return cartes.stream()
                .map(carte -> {
                    CarteDTO carteDTO = new CarteDTO();
                    carteDTO.setNumeroCarte(carte.getNumeroCarte());
                    carteDTO.setDateExpiration(carte.getDateExpiration());

                    CarteDTO.TitulaireCarteDTO titulaireCarteDTO = new CarteDTO.TitulaireCarteDTO();
                    titulaireCarteDTO.setIdClient(carte.getCompte().getCompteClients().get(0).getId()); // Assuming the first client is the cardholder
                    carteDTO.setTitulaireCarte(titulaireCarteDTO);

                    return carteDTO;
                })
                .collect(Collectors.toList());
    }


    public static String generateAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();

        for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
            int digit = random.nextInt(10);
            accountNumber.append(digit);
        }

        return accountNumber.toString();
    }
    @Override
    public CompteDTO_add createNewCompte(CompteDTO_add compteDTO_add) throws Exception {
        Compte nouveauCompte = new Compte();
        nouveauCompte.setIntituleCompte(compteDTO_add.getIntituleCompte());
        nouveauCompte.setTypeCompte(compteDTO_add.getTypeCompte());
        nouveauCompte.setDateCreation(new Date());

        if (compteDTO_add.getTitulairesCompte().isEmpty()) {
            throw new Exception("Au moins un client doit être associé au compte");
        }

        ClientIdDTO premierTitulaireDTO = compteDTO_add.getTitulairesCompte().get(0);
        Client premierClient = clientRepositories.findById(premierTitulaireDTO.getId())
                .orElseThrow(() -> new Exception("Client not found"));

        String codeBanque = premierClient.getCodeBanque();
        String codeGuichet = premierClient.getCodeGuichet();

        String numeroCompte = generateAccountNumber();
        int cleRib = calculerCleRib(codeBanque, codeGuichet, numeroCompte);

        String iban = "FR" + cleRib + codeBanque + codeGuichet + numeroCompte;
        nouveauCompte.setIban(iban);

        for (ClientIdDTO clientIdDTO : compteDTO_add.getTitulairesCompte()) {
            Client client = clientRepositories.findById(clientIdDTO.getId())
                    .orElseThrow(() -> new Exception("Client not found"));
            nouveauCompte.getCompteClients().add(client);
            client.getComptes().add(nouveauCompte);
        }

        Compte compteEnregistre = compteRepositories.save(nouveauCompte);

        CompteDTO_add compteDTOCreated = new CompteDTO_add();
        //compteDTOCreated.setIban(compteEnregistre.getIban());
        //compteDTOCreated.setSolde(compteEnregistre.getSolde());
        compteDTOCreated.setIntituleCompte(compteEnregistre.getIntituleCompte());
        compteDTOCreated.setTypeCompte(compteEnregistre.getTypeCompte());
        //compteDTOCreated.setDateCreation(compteEnregistre.getDateCreation());
        compteDTOCreated.setTitulairesCompte(compteEnregistre.getCompteClients().stream()
                .map(client -> new ClientIdDTO(client.getId()))
                .collect(Collectors.toList()));

        return compteDTOCreated;
    }

    @Override
    public CarteDTO_add createCarteForCompte(String iban, CarteDTO_add carteDTO) {
        Compte compte = compteRepositories.findById(iban)
                .orElseThrow(() -> new RuntimeException("Compte non trouvé avec l'IBAN : " + iban));

        if (compte.getCartes().size() >= 2) {
            throw new RuntimeException("Le compte a déjà le nombre maximum de cartes associées");
        }

        Carte nouvelleCarte = new Carte();
        nouvelleCarte.setNumeroCarte(carteDTO.getNumeroCarte());
        nouvelleCarte.setTitulaireCarte(carteDTO.getTitulaireCarte());
        nouvelleCarte.setCode(carteDTO.getCode());
        nouvelleCarte.setDateExpiration(carteDTO.getDateExpiration());

        nouvelleCarte.setCompte(compte);

        carteRepositories.save(nouvelleCarte);
        compteRepositories.save(compte);

        CarteDTO_add carteDTOCreated = new CarteDTO_add();
        carteDTOCreated.setTitulaireCarte(nouvelleCarte.getTitulaireCarte());
        carteDTOCreated.setCode(nouvelleCarte.getCode());
        carteDTOCreated.setNumeroCarte(nouvelleCarte.getNumeroCarte());
        carteDTOCreated.setDateExpiration(nouvelleCarte.getDateExpiration());

        return carteDTOCreated;
    }


    @Override
    public void debit(String accountId, double amount) throws BalanceNotSufficientException {
        Compte compte = compteRepositories.findByIban(accountId);
        if (compte.getSolde() < amount) {
            throw new BalanceNotSufficientException("Balance not sufficient");
        }
        Transaction transaction = new Transaction();
        transaction.setTypeTransaction(typeTransaction.DEBIT);
        transaction.setMontant(amount);
        transaction.setCompte(compte);
        transactionRepositories.save(transaction);
        compte.setSolde(compte.getSolde() - amount);
        compteRepositories.save(compte);
    }
    @Override
    public void credit(String accountId, double amount) {
        Compte compte = compteRepositories.findByIban(accountId);
        Transaction transaction = new Transaction();
        transaction.setTypeTransaction(typeTransaction.CREDIT);
        transaction.setMontant(amount);
        transaction.setCompte(compte);
        transactionRepositories.save(transaction);
        compte.setSolde(compte.getSolde() + amount);
        compteRepositories.save(compte);
    }
    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BalanceNotSufficientException {
        debit(accountIdSource, amount);
        credit(accountIdDestination, amount);
    }
    @Override
    public Transaction effectuerPaiement1(String iban, String numeroCarte, double montant, boolean isCredit) throws BalanceNotSufficientException {
        Compte compte = compteRepositories.findByIban(iban);
        Carte carte = carteRepositories.findByNumeroCarte(numeroCarte);

        if (!carte.getCompte().equals(compte)) {
            throw new IllegalArgumentException("Card is not associated with the provided IBAN");
        }

        if (!isCredit && compte.getSolde() - montant < 0) {
            throw new BalanceNotSufficientException("Solde insuffisant pour effectuer le débit");
        }

        Transaction transaction = new Transaction();
        transaction.setMontant(montant);
        transaction.setTypeTransaction(isCredit ? typeTransaction.CREDIT : typeTransaction.DEBIT);
        transaction.setTypeSource(typeSource.CARTE);
        transaction.setIdSource(carte.getNumeroCarte());
        transaction.setCompte(compte);
        transaction.setDateCreation(new Date());

        if (isCredit) {
            compte.setSolde(compte.getSolde() + montant);
        } else {
            compte.setSolde(compte.getSolde() - montant);
        }

        transactionRepositories.save(transaction);
        compteRepositories.save(compte);

        return transaction;
    }
    @Override
    public Virement createVirement3(String ibanCompteEmetteur, String ibanCompteBeneficiaire, double montant, String libelleVirement) throws BalanceNotSufficientException {
        Compte compteEmetteur = compteRepositories.findByIban(ibanCompteEmetteur);
        Compte compteBeneficiaire = compteRepositories.findByIban(ibanCompteBeneficiaire);

        if (compteEmetteur == null) {
            throw new IllegalArgumentException("Compte émetteur non trouvé avec l'IBAN : " + ibanCompteEmetteur);
        }

        if (compteBeneficiaire == null) {
            throw new IllegalArgumentException("Compte bénéficiaire non trouvé avec l'IBAN : " + ibanCompteBeneficiaire);
        }

        if (compteEmetteur.getSolde() < montant) {
            throw new BalanceNotSufficientException("Solde insuffisant pour effectuer le virement");
        }

        Virement virement = new Virement();
        virement.setIbanCompteEmetteur(ibanCompteEmetteur);
        virement.setIbanCompteBeneficiaire(ibanCompteBeneficiaire);
        virement.setMontant(montant);
        virement.setLibelleVirement(libelleVirement);
        virement.setDateCreation(new Date());

        virementRepositories.save(virement);

        Transaction debitTransaction = new Transaction();
        debitTransaction.setTypeTransaction(typeTransaction.DEBIT);
        debitTransaction.setTypeSource(typeSource.VIREMENT);
        debitTransaction.setMontant(montant);
        debitTransaction.setIdSource(virement.getId().toString());
        debitTransaction.setCompte(compteEmetteur);
        debitTransaction.setDateCreation(new Date());
        compteEmetteur.setSolde(compteEmetteur.getSolde()-montant);
        transactionRepositories.save(debitTransaction);

        Transaction creditTransaction = new Transaction();
        creditTransaction.setTypeTransaction(typeTransaction.CREDIT);
        creditTransaction.setTypeSource(typeSource.VIREMENT);
        creditTransaction.setMontant(montant);
        creditTransaction.setIdSource(virement.getId().toString());
        creditTransaction.setCompte(compteBeneficiaire);
        creditTransaction.setDateCreation(new Date());
        compteBeneficiaire.setSolde(compteBeneficiaire.getSolde()+montant);
        transactionRepositories.save(creditTransaction);

        virement.setTransactionsrc(debitTransaction);

        return virement;
    }

}
