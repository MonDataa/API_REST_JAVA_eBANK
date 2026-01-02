package grp.insa.api_gestion_systeme_bancaire.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class CreditDTO {
    private String iban;
    private double montant;
}
