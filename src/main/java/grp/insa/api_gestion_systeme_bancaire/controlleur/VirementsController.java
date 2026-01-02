package grp.insa.api_gestion_systeme_bancaire.controlleur;

import grp.insa.api_gestion_systeme_bancaire.dto.*;
import grp.insa.api_gestion_systeme_bancaire.entities.Client;
import grp.insa.api_gestion_systeme_bancaire.entities.Virement;
import grp.insa.api_gestion_systeme_bancaire.exception.BalanceNotSufficientException;
import grp.insa.api_gestion_systeme_bancaire.service.ClientServiceImpl;
import grp.insa.api_gestion_systeme_bancaire.service.CompteServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/virements")
public class VirementsController {

    private final CompteServiceImpl compteService;

    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws  BalanceNotSufficientException {
        this.compteService.debit(debitDTO.getIban(),debitDTO.getMontant());
        return debitDTO;
    }
    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) {
        this.compteService.credit(creditDTO.getIban(),creditDTO.getMontant());
        return creditDTO;
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody VirementDTO transferRequestDTO) {
        try {
            compteService.transfer(
                    transferRequestDTO.getIbanCompteEmetteur(),
                    transferRequestDTO.getIbanCompteBeneficiaire(),
                    transferRequestDTO.getMontant());

            return ResponseEntity.ok("Transfert réussi.");
        } catch (BalanceNotSufficientException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Échec du transfert : " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur interne du serveur.");
        }
    }


    @PostMapping("/create")
    public ResponseEntity<?> createVirement3(@RequestBody VirementRequest virementRequest) {
        try {
            Virement virement = compteService.createVirement3(
                    virementRequest.getIbanCompteEmetteur(),
                    virementRequest.getIbanCompteBeneficiaire(),
                    virementRequest.getMontant(),
                    virementRequest.getLibelleVirement()
            );
            return ResponseEntity.ok(virement);
        } catch (BalanceNotSufficientException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


}
