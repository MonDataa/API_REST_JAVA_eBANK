package grp.insa.api_gestion_systeme_bancaire.service;

import grp.insa.api_gestion_systeme_bancaire.dto.*;
import grp.insa.api_gestion_systeme_bancaire.entities.Carte;
import grp.insa.api_gestion_systeme_bancaire.entities.Transaction;
import grp.insa.api_gestion_systeme_bancaire.entities.Virement;
import grp.insa.api_gestion_systeme_bancaire.exception.BalanceNotSufficientException;
import grp.insa.api_gestion_systeme_bancaire.exception.getCompteNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CompteService {
    Transaction saveTransaction(Transaction transaction);
    Carte saveCarte(Carte carte);

    void addCompteToCarte(String titulaireCarte, String intituleCompte);
    void addCompteToTransaction(Long id, String intituleCompte);

    List<CompteDTO_r_cocl> getComptesParClient(Long clientId) throws getCompteNotFoundException;
    List<CarteDTO> getCartesParIban(String iban);
    CompteDTO_add createNewCompte(CompteDTO_add compteDTO_add)throws Exception;
    CarteDTO_add createCarteForCompte(String iban, CarteDTO_add carteDTO_add);

    void debit(String accountId, double amount) throws BalanceNotSufficientException;
    void credit(String accountId, double amount);
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws  BalanceNotSufficientException;

    Transaction effectuerPaiement1(String iban, String numeroCarte, double montant, boolean isCredit) throws BalanceNotSufficientException;
    Virement createVirement3(String ibanCompteEmetteur, String ibanCompteBeneficiaire, double montant, String libelleVirement) throws BalanceNotSufficientException;
}
