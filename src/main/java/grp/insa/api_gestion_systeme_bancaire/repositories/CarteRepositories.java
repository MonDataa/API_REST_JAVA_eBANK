package grp.insa.api_gestion_systeme_bancaire.repositories;

import grp.insa.api_gestion_systeme_bancaire.entities.Carte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteRepositories extends JpaRepository<Carte,String> {
    Carte findCarteByTitulaireCarte(String titulaireCarte);
    Carte findByNumeroCarte(String numeroCarte);
}
