package isi.projet.Controllers;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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

import isi.projet.Models.Enseignant;
import isi.projet.Models.EnseignantDTO;
import isi.projet.Models.ModuleDTO;
import isi.projet.Repository.EnseignantRepo;

@RestController
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
	@PostMapping("/add")
	public String addEnseignant(@RequestBody EnseignantDTO enseignantdto) {
		Enseignant enseignant=new Enseignant();
		enseignant.setEmail(enseignantdto.getEmail());
		enseignant.setNom(enseignantdto.getNom());
		enseignant.setPrenom(enseignantdto.getPrenom());
		ensignantrepo.save(enseignant);
		return "done";
		
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
	public String updateEnseignat(@PathVariable String email ,@RequestBody EnseignantDTO enseignantdto) {
		Enseignant enseignant =ensignantrepo.findById(email).orElse(null);
		if(enseignant!=null) {
			enseignant.setNom(enseignantdto.getNom());
			enseignant.setPrenom(enseignantdto.getPrenom());
			ensignantrepo.save(enseignant);
			return "done";
			
		}else {
			return "erreur";
		}
		
	}
	//supprimer enseignant
    @DeleteMapping("/delete/{email}")
	public ResponseEntity<String> deleteEnseignant(@PathVariable String email) {
		if (ensignantrepo.existsById(email)) {
			ensignantrepo.deleteById(email);
			return ResponseEntity.ok("done");
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
