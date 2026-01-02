package grp.insa.api_gestion_systeme_bancaire.mappers;

import grp.insa.api_gestion_systeme_bancaire.dto.CarteDTO;
import grp.insa.api_gestion_systeme_bancaire.dto.CarteDTO_add;
import grp.insa.api_gestion_systeme_bancaire.dto.CompteDTO;
import grp.insa.api_gestion_systeme_bancaire.entities.Carte;
import grp.insa.api_gestion_systeme_bancaire.entities.Compte;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CarteMapperImpl {
    public CarteDTO fromCarte(Carte carte){
        if (carte == null) {
            return null;
        }
        CarteDTO carteDTO=new CarteDTO();
        BeanUtils.copyProperties(carte,carteDTO);
        return  carteDTO;
    }
    public Carte fromCarteDTO(CarteDTO carteDTO){
        Carte carte=new Carte();
        BeanUtils.copyProperties(carteDTO,carte);
        return  carte;
    }

    public CarteDTO_add fromCarte_add(Carte carte){
        if (carte == null) {
            return null;
        }
        CarteDTO_add carteDTO_add=new CarteDTO_add();
        BeanUtils.copyProperties(carte,carteDTO_add);
        return  carteDTO_add;
    }
    public Carte fromCarteDTO_add(CarteDTO_add carteDTO_add){
        Carte carte=new Carte();
        BeanUtils.copyProperties(carteDTO_add,carte);
        return  carte;
    }
}
