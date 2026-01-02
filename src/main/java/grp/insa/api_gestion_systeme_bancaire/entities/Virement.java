package grp.insa.api_gestion_systeme_bancaire.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "virements")
public class Virement {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_creation")
    private Date dateCreation;

    @Column(name = "iban_compte_emetteur")
    private String ibanCompteEmetteur;

    @Column(name = "iban_compte_beneficiaire")
    private String ibanCompteBeneficiaire;

    @Column(name = "montant")
    private double montant;

    @Column(name = "libelle_virement")
    private String libelleVirement;


    @ManyToOne
    @JsonManagedReference
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Transaction transactionsrc;
}
