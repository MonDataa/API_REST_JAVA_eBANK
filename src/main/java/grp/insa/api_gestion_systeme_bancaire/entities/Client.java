package grp.insa.api_gestion_systeme_bancaire.entities;



import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "clients")
public class Client {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String prenom;
    private String nom;

    @Column(name = "date_naissance")
    private Date dateNaissance;

    private String telephone;

    @Column(name = "adresse_postale")
    private String adressePostale;

    @Column(name = "code_banque")
    private String codeBanque;

    @Column(name = "code_guichet")
    private String codeGuichet;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "compte_client",
            joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "compte_iban", referencedColumnName = "iban")
    )
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Compte> comptes = new ArrayList<>();

}
