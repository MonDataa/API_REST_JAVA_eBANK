package grp.insa.api_gestion_systeme_bancaire;

import grp.insa.api_gestion_systeme_bancaire.dto.ClientDTO;
import grp.insa.api_gestion_systeme_bancaire.dto.CompteDTO;
import grp.insa.api_gestion_systeme_bancaire.entities.Carte;
import grp.insa.api_gestion_systeme_bancaire.entities.Client;
import grp.insa.api_gestion_systeme_bancaire.entities.Compte;
import grp.insa.api_gestion_systeme_bancaire.entities.Transaction;
import grp.insa.api_gestion_systeme_bancaire.entities.enumerateur.typeCompte;
import grp.insa.api_gestion_systeme_bancaire.entities.enumerateur.typeSource;
import grp.insa.api_gestion_systeme_bancaire.entities.enumerateur.typeTransaction;
import grp.insa.api_gestion_systeme_bancaire.repositories.CompteRepositories;
import grp.insa.api_gestion_systeme_bancaire.service.ClientService;
import grp.insa.api_gestion_systeme_bancaire.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class ApiGestionSystemeBancaireApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGestionSystemeBancaireApplication.class, args);
    }
    @Bean
    CommandLineRunner start(ClientService clientService, CompteService compteService){
        return args -> {
            System.out.println("*******insertion data : Client*************");
            Client c=new Client();
            //c.setId(1L);
            c.setNom("m1");
            c.setPrenom("elk");
            c.setTelephone("07");
            c.setDateNaissance(new Date());
            c.setAdressePostale("valenciennes");
            c.setCodeBanque("12345");
            c.setCodeGuichet("67890");
            clientService.saveClient(c);

            Client c1=new Client();
            //c.setId(2L);
            c1.setNom("m2");
            c1.setPrenom("elk2");
            c1.setTelephone("06");
            c1.setDateNaissance(new Date());
            c1.setAdressePostale("lens");
            c1.setCodeBanque("12346");
            c1.setCodeGuichet("67891");
            clientService.saveClient(c1);

            System.out.println("*******insertion data : Compte*************");

            Stream.of("compte1", "compte2", "compte3").forEach(cc -> {
                Compte compte = new Compte();

                // Génération d'un IBAN aléatoire
                Random random = new Random();
                String iban = "FR76" + (30000 + random.nextInt(1000)) +
                        "30205" + (4000 + random.nextInt(1000)) +
                        String.format("%011d", random.nextInt(1000000000)) +
                        "49"; // Format simplifié d'un IBAN

                // Génération d'un solde aléatoire entre 0 et 1000
                double solde = Math.round(random.nextDouble() * 1000 * 100.0) / 100.0; // Arrondi à deux décimales

                compte.setIban(iban);
                compte.setSolde(solde);
                compte.setDateCreation(new Date());
                compte.setIntituleCompte(cc);
                compte.setTypeCompte(typeCompte.COMPTE_COURANT_JOINT);

                clientService.addNewCompte(compte);
            });

            System.out.println("******* addCompteToClien *************");

            clientService.addCompteToClient("m1","compte1");
            clientService.addCompteToClient("m2","compte2");
            clientService.addCompteToClient("m2","compte3");

            System.out.println("*******insertion data : Carte*************");
            Carte carte=new Carte();
            carte.setNumeroCarte("carteid123");
            carte.setCode(9852);
            carte.setTitulaireCarte("mounsif elk");
            carte.setDateExpiration("11/01/2024");
            compteService.saveCarte(carte);

            Carte carte2=new Carte();
            carte2.setCode(2052);
            carte2.setTitulaireCarte("toufique elk");
            carte2.setNumeroCarte("carte2id123");
            carte2.setDateExpiration("11/02/2024");
            compteService.saveCarte(carte2);

            System.out.println("******* addCompteToCarte *************");

            compteService.addCompteToCarte("toufique elk","compte1");
            compteService.addCompteToCarte("mounsif elk","compte2");

            System.out.println("*******insertion data : Transaction*************");

            Transaction transaction=new Transaction();
            transaction.setMontant(20.00);
            //transaction.setId(124L);
            transaction.setTypeSource(typeSource.VIREMENT);
            transaction.setIdSource("v1");
            transaction.setTypeTransaction(typeTransaction.DEBIT);
            transaction.setDateCreation(new Date());
            compteService.saveTransaction(transaction);

            Transaction transaction2=new Transaction();
            transaction2.setMontant(50.00);
            //transaction2.setId(174L);
            transaction2.setTypeSource(typeSource.CARTE);
            transaction2.setIdSource("c1");
            transaction2.setTypeTransaction(typeTransaction.CREDIT);
            transaction2.setDateCreation(new Date());
            compteService.saveTransaction(transaction2);

            System.out.println("******* addCompteToTransaction *************");

            compteService.addCompteToTransaction(transaction2.getId(),"compte1");
            compteService.addCompteToTransaction(transaction.getId(),"compte2");

            System.out.println("******* test *************");

        };
    }

}
