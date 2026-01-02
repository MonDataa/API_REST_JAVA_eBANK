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

public class VirementDTO {
    private Long idVirement;
    private Date dateCreation;
    private String ibanCompteEmetteur;
    private String ibanCompteBeneficiaire;
    private double montant;
    private String libelleVirement;
    private List<TransactionDTO> transactions;
}
