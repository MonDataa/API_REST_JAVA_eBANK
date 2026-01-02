package grp.insa.api_gestion_systeme_bancaire.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class PaiementDTO {
    private double montant;
    private Date dateCreation;
}
