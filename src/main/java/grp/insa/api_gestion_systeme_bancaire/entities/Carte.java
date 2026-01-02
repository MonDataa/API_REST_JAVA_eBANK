package grp.insa.api_gestion_systeme_bancaire.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cartes")
public class Carte {

    @Id
    private String numeroCarte;
    private String titulaireCarte;
    private Integer code;

    @Column(name = "date_expiration")
    private String dateExpiration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compte_iban")
    private Compte compte;

}
