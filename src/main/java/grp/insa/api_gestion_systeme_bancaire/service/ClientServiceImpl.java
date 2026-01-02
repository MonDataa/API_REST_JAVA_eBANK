package grp.insa.api_gestion_systeme_bancaire.service;

import grp.insa.api_gestion_systeme_bancaire.dto.ClientDTO;
import grp.insa.api_gestion_systeme_bancaire.dto.ClientIdDTO;
import grp.insa.api_gestion_systeme_bancaire.entities.Client;
import grp.insa.api_gestion_systeme_bancaire.entities.Compte;
import grp.insa.api_gestion_systeme_bancaire.exception.getClientNotFoundException;
import grp.insa.api_gestion_systeme_bancaire.mappers.ClientMapperImpl;
import grp.insa.api_gestion_systeme_bancaire.repositories.CarteRepositories;
import grp.insa.api_gestion_systeme_bancaire.repositories.ClientRepositories;
import grp.insa.api_gestion_systeme_bancaire.repositories.CompteRepositories;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class ClientServiceImpl implements ClientService{

    private ClientRepositories clientRepositories;
    private CompteRepositories compteRepositories;
    private CarteRepositories carteRepositories;
    private ClientMapperImpl ClientMapperImpl;

    @Override
    public List<Client> ListClients() {
        return clientRepositories.findAll();
    }

    @Override
    public ClientDTO getClients(String nom,String prenom) {
        Client clients = clientRepositories.findByNomAndPrenom(nom,prenom);
        return ClientMapperImpl.fromClient(clients);
    }

    @Override
    public ClientDTO addClient(ClientDTO clientDTO) {
        Client client=ClientMapperImpl.fromClientDTO(clientDTO);
        Client savedclient = clientRepositories.save(client);
        return ClientMapperImpl.fromClient(savedclient);
    }

    @Override
    public ClientDTO editClient(ClientDTO clientDTO) {
        Client client=ClientMapperImpl.fromClientDTO(clientDTO);
        Client savedclient = clientRepositories.save(client);
        return ClientMapperImpl.fromClient(savedclient);
    }

    @Override
    public Client saveClient(Client client) {
        //client.setId(Long.valueOf(UUID.randomUUID().toString()));
        return clientRepositories.save(client);
    }

    @Override
    public Compte addNewCompte(Compte compte) {
        //compte.setId(UUID.randomUUID().toString());
        return compteRepositories.save(compte);
    }

    @Override
    public Compte findCompteByCompteName(String CompteName) {
        return compteRepositories.findCompteByIntituleCompte(CompteName);
    }

    @Override
    public Client findClientByClientName(String ClientName) {
        return clientRepositories.findClientByNom(ClientName);
    }


    @Override
    public void addCompteToClient(String nom, String intituleCompte) {
        Client client=findClientByClientName(nom);
        Compte compte=findCompteByCompteName(intituleCompte);
        if(client.getComptes()!=null){
            client.getComptes().add(compte);
            compte.getCompteClients().add(client);
        }
    }


}
