package grp.insa.api_gestion_systeme_bancaire.dto;

import grp.insa.api_gestion_systeme_bancaire.entities.Client;
import grp.insa.api_gestion_systeme_bancaire.entities.Transaction;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class CompteDTO_add {
    //private String iban;
    //private double solde;
    private String intituleCompte;
    private grp.insa.api_gestion_systeme_bancaire.entities.enumerateur.typeCompte typeCompte;
    //private Date DateCreation;
    private List<ClientIdDTO> titulairesCompte;

}
