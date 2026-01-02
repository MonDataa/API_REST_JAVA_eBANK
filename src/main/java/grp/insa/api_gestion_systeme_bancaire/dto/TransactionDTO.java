package grp.insa.api_gestion_systeme_bancaire.dto;

import grp.insa.api_gestion_systeme_bancaire.entities.Compte;
import grp.insa.api_gestion_systeme_bancaire.entities.enumerateur.typeSource;
import grp.insa.api_gestion_systeme_bancaire.entities.enumerateur.typeTransaction;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionDTO {
    private Long id;
    private double montant;
    private grp.insa.api_gestion_systeme_bancaire.entities.enumerateur.typeTransaction typeTransaction;
    private grp.insa.api_gestion_systeme_bancaire.entities.enumerateur.typeSource typeSource;
    private String idSource;
}

/*
public class TransactionDTO {
    private Long id;
    private double montant;
    private grp.insa.api_gestion_systeme_bancaire.entities.enumerateur.typeTransaction typeTransaction;
}*/