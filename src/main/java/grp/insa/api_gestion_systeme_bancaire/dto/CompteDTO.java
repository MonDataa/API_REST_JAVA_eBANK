package grp.insa.api_gestion_systeme_bancaire.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import grp.insa.api_gestion_systeme_bancaire.entities.Carte;
import grp.insa.api_gestion_systeme_bancaire.entities.Client;
import grp.insa.api_gestion_systeme_bancaire.entities.Transaction;
import grp.insa.api_gestion_systeme_bancaire.entities.enumerateur.typeCompte;
import jakarta.persistence.*;
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

public class CompteDTO {
    private String iban;
    private double solde;
    private String intituleCompte;
    private grp.insa.api_gestion_systeme_bancaire.entities.enumerateur.typeCompte typeCompte;
    private List<Client> Compteclient=new ArrayList<>();
    private Transaction transaction;
    private List<ClientIdDTO> titulairesCompte;

}
