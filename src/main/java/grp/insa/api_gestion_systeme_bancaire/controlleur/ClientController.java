package grp.insa.api_gestion_systeme_bancaire.controlleur;

import grp.insa.api_gestion_systeme_bancaire.dto.ClientDTO;
import grp.insa.api_gestion_systeme_bancaire.dto.ClientIdDTO;
import grp.insa.api_gestion_systeme_bancaire.entities.Client;
import grp.insa.api_gestion_systeme_bancaire.entities.Compte;
import grp.insa.api_gestion_systeme_bancaire.exception.getClientNotFoundException;
import grp.insa.api_gestion_systeme_bancaire.service.ClientServiceImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("clients")
public class ClientController {
    private final ClientServiceImpl clientService;

    @GetMapping("/getclients/{nom}/{prenom}")
    public ResponseEntity GetClient(@PathVariable(name = "nom") String nom,@PathVariable(name = "prenom") String prenom) {
            ClientDTO clients = clientService.getClients(nom,prenom);
            return ResponseEntity.ok(clients);
    }
    /*@GetMapping("/getclients/{id}")
    public ClientDTO GetClient(@PathVariable(name = "id") Long clientId) throws getClientNotFoundException {
        return clientService.getClients(clientId);
    }*/
    @PostMapping("/addclients")
    public ClientDTO saveCustomer(@RequestBody ClientDTO clientDTO){
        return clientService.addClient(clientDTO);
    }
    @PutMapping("/updateclients/{id}")
    public ClientDTO updateCustomer(@PathVariable Long id, @RequestBody ClientDTO clientDTO){
        clientDTO.setId(id);
        return clientService.editClient(clientDTO);
    }

}
