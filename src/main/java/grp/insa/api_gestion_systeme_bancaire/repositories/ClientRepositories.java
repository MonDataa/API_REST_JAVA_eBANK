package grp.insa.api_gestion_systeme_bancaire.repositories;

import grp.insa.api_gestion_systeme_bancaire.entities.Client;
import grp.insa.api_gestion_systeme_bancaire.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepositories extends JpaRepository<Client,Long> {
    Client findClientByNom(String Nom);
    Client findByNomAndPrenom(String nom,String prenom);
}
