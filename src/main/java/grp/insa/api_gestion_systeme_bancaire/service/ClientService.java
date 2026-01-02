package grp.insa.api_gestion_systeme_bancaire.service;

import grp.insa.api_gestion_systeme_bancaire.dto.ClientDTO;
import grp.insa.api_gestion_systeme_bancaire.dto.ClientIdDTO;
import grp.insa.api_gestion_systeme_bancaire.entities.Client;
import grp.insa.api_gestion_systeme_bancaire.entities.Compte;
import grp.insa.api_gestion_systeme_bancaire.exception.getClientNotFoundException;

import java.util.List;

public interface ClientService {

    List<Client> ListClients() ;
    ClientDTO getClients(String nom,String prenom) ;
    ClientDTO addClient(ClientDTO clientDTO);
    ClientDTO editClient(ClientDTO clientDTO);
    Client saveClient(Client client);
    Compte addNewCompte(Compte compte);
    Compte findCompteByCompteName(String CompteName);
    Client findClientByClientName(String ClientName);
    void addCompteToClient(String nom,String intituleCompte);

}
