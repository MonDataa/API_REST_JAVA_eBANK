package grp.insa.api_gestion_systeme_bancaire.repositories;

import grp.insa.api_gestion_systeme_bancaire.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompteRepositories extends JpaRepository<Compte,String> {
    Compte findCompteByIntituleCompte(String intituleCompte);
    Compte findByIban(String iban);
    @Query("SELECT c FROM Compte c JOIN c.compteClients cc WHERE cc.id = :clientId")
    List<Compte> findByClientId(@Param("clientId") Long clientId);

}
