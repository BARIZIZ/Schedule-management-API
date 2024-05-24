package isi.projet.Controllers;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import isi.projet.Models.Enseignant;
import isi.projet.Models.EnseignantDTO;
import isi.projet.Models.ModuleDTO;
import isi.projet.Repository.EnseignantRepo;

@RestController
@CrossOrigin(origins ="http://localhost:4200")
@RequestMapping("/enseignant")
public class EnseignantController {
	@Autowired
	EnseignantRepo ensignantrepo;

	// afficher tous les enseiganant
	@GetMapping("")
	public List<EnseignantDTO> getAllEnseignant() {
		List<Enseignant> enseignants = ensignantrepo.findAll();
		List<EnseignantDTO> enseignantDTOs = new ArrayList<>();
		for (Enseignant enseignant : enseignants) {
			EnseignantDTO enseignantdto = new EnseignantDTO();
			enseignantdto.setEmail(enseignant.getEmail());
			enseignantdto.setNom(enseignant.getNom());
			enseignantdto.setPrenom(enseignant.getPrenom());
			enseignantDTOs.add(enseignantdto);
		}

		return enseignantDTOs;

	}
//    Ajouter des enseigants
	@PostMapping("")
	public ResponseEntity< String> addEnseignant(@RequestBody EnseignantDTO enseignantdto) {
		Enseignant enseignant=new Enseignant();
		enseignant.setEmail(enseignantdto.getEmail());
		enseignant.setNom(enseignantdto.getNom());
		enseignant.setPrenom(enseignantdto.getPrenom());
		ensignantrepo.save(enseignant);
		return ResponseEntity.ok().build();
		
	}
//	recuperer enseignant par sont id
	@GetMapping("/{id}")
    public ResponseEntity<EnseignantDTO> getEnseignantById(@PathVariable String id) {
        Enseignant enseignant = ensignantrepo.findById(id).orElse(null);
        
        if (enseignant != null) {
        	EnseignantDTO enseignantdto =new EnseignantDTO();
        	enseignantdto.setEmail(enseignant.getEmail());
        	enseignantdto.setNom(enseignant.getNom());
        	enseignantdto.setPrenom(enseignant.getPrenom());
            return ResponseEntity.ok(enseignantdto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
//	editer enseignant
	@PutMapping("/edit/{email}")
	public ResponseEntity<String> updateEnseignat(@PathVariable String email ,@RequestBody EnseignantDTO enseignantdto) {
		Enseignant enseignant =ensignantrepo.findById(email).orElse(null);
		if(enseignant!=null) {
			enseignant.setNom(enseignantdto.getNom());
			enseignant.setPrenom(enseignantdto.getPrenom());
			ensignantrepo.save(enseignant);
			return ResponseEntity.ok().build();
			
		}else {
			return ResponseEntity.notFound().build();
		}
		
	}
	//supprimer enseignant
    @DeleteMapping("/{email}")
	public ResponseEntity<String> deleteEnseignant(@PathVariable String email) {
		if (ensignantrepo.existsById(email)) {
			ensignantrepo.deleteById(email);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
