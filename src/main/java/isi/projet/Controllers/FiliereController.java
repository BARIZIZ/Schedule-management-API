package isi.projet.Controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isi.projet.Models.Filiere;
import isi.projet.Models.FiliereDTO;
import isi.projet.Repository.FiliereRepo;

@RestController
@RequestMapping("/filiere")

public class FiliereController {
	@Autowired
	FiliereRepo filiereRepo;

//  afficher tout les filieres
	@GetMapping("")
	public List<FiliereDTO> getAllFiliere() {
		List<Filiere> filieres = filiereRepo.findAll();
		List<FiliereDTO> filiereDTOs = filieres.stream().map(filiere -> {
			FiliereDTO dto = new FiliereDTO();
			dto.setNomFiliere(filiere.getNomFiliere());
			return dto;
		}).collect(Collectors.toList());

		return filiereDTOs;
	}

// ajouter filiere
	@PostMapping("/add")
	public String addFiliere(@RequestBody FiliereDTO filieredto) {
		Filiere filiere =new Filiere();
		filiere.setNomFiliere(filieredto.getNomFiliere());
		filiereRepo.save(filiere);
		return "done";
	}
//	supprimer filiere
	@DeleteMapping("/delete/{nomFiliere}")
    public ResponseEntity<String> deleteFiliere(@PathVariable String nomFiliere) {
        // Recherchez la filière dans la base de données par son nom
        Filiere existingFiliere = filiereRepo.findById(nomFiliere).orElse(null);

        if (existingFiliere != null) {
            // Supprimez la filière de la base de données
            filiereRepo.delete(existingFiliere);
            return ResponseEntity.ok("Filiere deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	//recupperer filiere par id
	@GetMapping("/{id}")
    public ResponseEntity<FiliereDTO> getFiliereById(@PathVariable String id) {
        Filiere filiere = filiereRepo.findById(id).orElse(null);
        if (filiere != null) {
            FiliereDTO filiereDTO = new FiliereDTO();
            filiereDTO.setNomFiliere(filiere.getNomFiliere());
            filiereDTO.setNomFiliere(filiere.getNomFiliere());
            return ResponseEntity.ok(filiereDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
	}
}
