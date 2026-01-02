package grp.insa.api_gestion_systeme_bancaire.dto;

import grp.insa.api_gestion_systeme_bancaire.entities.Compte;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class DebitDTO {
    private String iban;
    private double montant;
}
