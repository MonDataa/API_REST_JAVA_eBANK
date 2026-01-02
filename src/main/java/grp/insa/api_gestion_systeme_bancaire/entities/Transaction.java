package grp.insa.api_gestion_systeme_bancaire.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import grp.insa.api_gestion_systeme_bancaire.entities.enumerateur.typeTransaction;
import grp.insa.api_gestion_systeme_bancaire.entities.enumerateur.typeSource;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class Transaction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double montant;

    @Enumerated(EnumType.STRING)
    private typeTransaction typeTransaction;
    @Enumerated(EnumType.STRING)
    private typeSource typeSource;

    private String idSource;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_creation")
    private Date dateCreation;

    @ManyToOne
    @JoinColumn(name = "compte_iban")
    @JsonBackReference
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Compte compte;


    //@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER,mappedBy="transactionsrc")
    @OneToMany(fetch = FetchType.LAZY,mappedBy="transactionsrc")
    //@JsonBackReference
    @JsonIgnore
    private List<Virement> transactionVirement= new ArrayList<>();;

}
