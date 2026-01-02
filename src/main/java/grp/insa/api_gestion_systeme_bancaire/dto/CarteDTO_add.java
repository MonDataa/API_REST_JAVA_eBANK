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

public class CarteDTO_add {
    private String titulaireCarte; // ID du client titulaire de la carte
    private Integer code; // Code de la carte (si nécessaire)
    private String numeroCarte; // Numéro de la carte
    private String dateExpiration;
}
