package grp.insa.api_gestion_systeme_bancaire.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class PaymentRequest {
    private double montant;
    private boolean isCredit;
}
