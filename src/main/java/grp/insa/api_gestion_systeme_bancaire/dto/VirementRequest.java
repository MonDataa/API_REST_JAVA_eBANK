package grp.insa.api_gestion_systeme_bancaire.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class VirementRequest {
    private String ibanCompteEmetteur;
    private String ibanCompteBeneficiaire;
    private double montant;
    private String libelleVirement;
}
