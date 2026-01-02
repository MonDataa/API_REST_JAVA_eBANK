package grp.insa.api_gestion_systeme_bancaire.mappers;

import grp.insa.api_gestion_systeme_bancaire.dto.ClientDTO;
import grp.insa.api_gestion_systeme_bancaire.dto.CompteDTO;
import grp.insa.api_gestion_systeme_bancaire.dto.CompteDTO_add;
import grp.insa.api_gestion_systeme_bancaire.dto.CompteDTO_r_cocl;
import grp.insa.api_gestion_systeme_bancaire.entities.Client;
import grp.insa.api_gestion_systeme_bancaire.entities.Compte;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CompteMapperImpl {
    public CompteDTO fromCompte(Compte compte){
        if (compte == null) {
            return null;
        }
        CompteDTO compteDTO=new CompteDTO();
        BeanUtils.copyProperties(compte,compteDTO);
        return  compteDTO;
    }
    public Compte fromCompteDTO(CompteDTO compteDTO){
        Compte compte=new Compte();
        BeanUtils.copyProperties(compteDTO,compte);
        return  compte;
    }

    public CompteDTO_add fromCompte_add(Compte compte){
        if (compte == null) {
            return null;
        }
        CompteDTO_add compteDTO_add=new CompteDTO_add();
        BeanUtils.copyProperties(compte,compteDTO_add);
        return  compteDTO_add;
    }
    public Compte fromCompteDTO_add(CompteDTO_add compteDTO_add){
        Compte compte=new Compte();
        BeanUtils.copyProperties(compteDTO_add,compte);
        return  compte;
    }

    public CompteDTO_r_cocl fromCompte_r_cocl(Compte compte){
        if (compte == null) {
            return null;
        }
        CompteDTO_r_cocl compteDTO_r_cocl=new CompteDTO_r_cocl();
        BeanUtils.copyProperties(compte,compteDTO_r_cocl);
        return  compteDTO_r_cocl;
    }
    public Compte fromCompteDTO_r_cocl(CompteDTO_r_cocl compteDTO_r_cocl){
        Compte compte=new Compte();
        BeanUtils.copyProperties(compteDTO_r_cocl,compte);
        return  compte;
    }
}
