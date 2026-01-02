package grp.insa.api_gestion_systeme_bancaire.repositories;

import grp.insa.api_gestion_systeme_bancaire.entities.Transaction;
import grp.insa.api_gestion_systeme_bancaire.entities.Virement;
import grp.insa.api_gestion_systeme_bancaire.entities.enumerateur.typeTransaction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepositories extends JpaRepository<Transaction,Long> {
    Transaction findTransactionById(Long id);

    // Une requête JPQL pour trouver la dernière transaction par IBAN et type
    @Query("SELECT t FROM Transaction t WHERE t.compte.iban = :iban AND t.typeTransaction = :type ORDER BY t.id DESC")
    List<Transaction> findLastTransactionByIbanAndType(@Param("iban") String iban, @Param("type") typeTransaction type, Pageable pageable);

    // Une méthode helper pour obtenir la dernière transaction (un seul résultat)
    default Transaction findLastTransactionByIbanAndType(String iban, typeTransaction type) {
        return findLastTransactionByIbanAndType(iban, type, PageRequest.of(0, 1)).stream().findFirst().orElse(null);
    }

}

