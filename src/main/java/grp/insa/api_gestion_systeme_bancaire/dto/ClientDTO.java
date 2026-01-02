package grp.insa.api_gestion_systeme_bancaire.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class ClientDTO {
    private Long id;
    private String prenom;
    private String nom;
    private Date dateNaissance;
    private String telephone;
    private String adressePostale;
}
