package grp.insa.api_gestion_systeme_bancaire.mappers;

import grp.insa.api_gestion_systeme_bancaire.dto.ClientDTO;
import grp.insa.api_gestion_systeme_bancaire.dto.TransactionDTO;
import grp.insa.api_gestion_systeme_bancaire.entities.Client;
import grp.insa.api_gestion_systeme_bancaire.entities.Transaction;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class TransactionMapperImpl {
    public TransactionDTO fromTransaction(Transaction transaction){
        TransactionDTO transactionDTO=new TransactionDTO();
        BeanUtils.copyProperties(transaction,transactionDTO);
        return  transactionDTO;
    }
    public Transaction fromTransactionDTO(TransactionDTO transactionDTO){
        Transaction transaction=new Transaction();
        BeanUtils.copyProperties(transactionDTO,transaction);
        return  transaction;
    }
}
