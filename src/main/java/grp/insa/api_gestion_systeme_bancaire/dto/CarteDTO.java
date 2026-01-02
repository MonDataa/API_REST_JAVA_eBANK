package grp.insa.api_gestion_systeme_bancaire.dto;

import grp.insa.api_gestion_systeme_bancaire.entities.Client;
import grp.insa.api_gestion_systeme_bancaire.entities.Compte;
import grp.insa.api_gestion_systeme_bancaire.entities.Transaction;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarteDTO {
    private String numeroCarte;
    private String dateExpiration;
    private TitulaireCarteDTO titulaireCarte;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TitulaireCarteDTO {
        private Long idClient;
    }
}

/*public class CarteDTO {
    private String numeroCarte;
    private String dateExpiration;
    private List<Compte> compteCarte=new ArrayList<>();
}*/
