package grp.insa.api_gestion_systeme_bancaire.repositories;


import grp.insa.api_gestion_systeme_bancaire.entities.Virement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VirementRepositories extends JpaRepository<Virement,Long> {


}
