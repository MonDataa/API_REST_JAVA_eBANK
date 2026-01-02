package grp.insa.api_gestion_systeme_bancaire.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class CompteDTO_r_cocl {
    private String iban;
    private double solde;
    private String intituleCompte;
    private grp.insa.api_gestion_systeme_bancaire.entities.enumerateur.typeCompte typeCompte;
    private List<ClientIdDTO> titulairesCompte=new ArrayList<>();
    private List<TransactionDTO> transactions=new ArrayList<>();

}
