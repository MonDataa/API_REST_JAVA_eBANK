package grp.insa.api_gestion_systeme_bancaire.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import grp.insa.api_gestion_systeme_bancaire.entities.enumerateur.typeCompte;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "compte")
public class Compte {
    //private String id;
    @Id
    private String iban;
    private double solde;
    private Date dateCreation;

    @Column(name = "intitule_compte")
    private String intituleCompte;

    @Enumerated(EnumType.STRING)
    private typeCompte typeCompte;
    @ManyToMany(mappedBy = "comptes", fetch = FetchType.EAGER)
    //@ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Client> compteClients = new ArrayList<>();


    @OneToMany(mappedBy = "compte", fetch = FetchType.LAZY)
    private List<Carte> cartes= new ArrayList<>();

    @OneToMany(mappedBy = "compte", fetch = FetchType.LAZY)
    //@JsonIgnore
    private List<Transaction> transactions= new ArrayList<>();


}
