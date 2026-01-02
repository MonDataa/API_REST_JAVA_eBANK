package grp.insa.api_gestion_systeme_bancaire.controlleur;

import grp.insa.api_gestion_systeme_bancaire.dto.*;
import grp.insa.api_gestion_systeme_bancaire.entities.Carte;
import grp.insa.api_gestion_systeme_bancaire.entities.Compte;
import grp.insa.api_gestion_systeme_bancaire.entities.Transaction;
import grp.insa.api_gestion_systeme_bancaire.exception.BalanceNotSufficientException;
import grp.insa.api_gestion_systeme_bancaire.exception.InsufficientFundsException;
import grp.insa.api_gestion_systeme_bancaire.exception.getCarteNotFoundException;
import grp.insa.api_gestion_systeme_bancaire.service.CompteServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/comptes")
public class CompteController {
    private final CompteServiceImpl compteService;


    @GetMapping("/compte/{iban}/cartes")
    public ResponseEntity<List<CarteDTO>> getCartesParIban(@PathVariable String iban) {
        try {
            List<CarteDTO> cartesDTO = compteService.getCartesParIban(iban);
            return ResponseEntity.ok(cartesDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<CompteDTO_r_cocl>> getComptesParClient(@PathVariable Long clientId) {
        try {
            List<CompteDTO_r_cocl> comptes = compteService.getComptesParClient(clientId);
            if (comptes.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(comptes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<CompteDTO_add> createNewCompte(@RequestBody CompteDTO_add compteDTO_add) {
        try {
            CompteDTO_add nouveauCompte = compteService.createNewCompte(compteDTO_add);
            return new ResponseEntity<>(nouveauCompte, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add/{iban}/cartes")
    public ResponseEntity<CarteDTO_add> createCarteForCompte(@PathVariable String iban,
                                                             @RequestBody CarteDTO_add carteDTO) {
        try {
            CarteDTO_add nouvelleCarte = compteService.createCarteForCompte(iban, carteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouvelleCarte);
        } catch (RuntimeException e) {
            // Gestion des exceptions, par exemple si le compte n'est pas trouvé
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/{iban}/cartes/{numeroCarte}/paiement")
    public ResponseEntity<?> effectuerPaiement1(@PathVariable String iban,
                                                @PathVariable String numeroCarte,
                                                @RequestBody PaymentRequest paymentRequest) {
        try {
            Transaction transaction = compteService.effectuerPaiement1(
                    iban,
                    numeroCarte,
                    paymentRequest.getMontant(),
                    paymentRequest.isCredit()
            );
            return ResponseEntity.ok(transaction);
        } catch (BalanceNotSufficientException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


}
