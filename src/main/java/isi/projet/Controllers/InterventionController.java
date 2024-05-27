package isi.projet.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import isi.projet.Repository.FiliereRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import isi.projet.Models.Enseignant;
import isi.projet.Models.Intervention;
import isi.projet.Models.InterventionDTO;
import isi.projet.Models.InterventionId;
import isi.projet.Models.Modules;
import isi.projet.Repository.EnseignantRepo;
import isi.projet.Repository.InterventionRepo;
import isi.projet.Repository.ModuleRepo;

@RestController
@CrossOrigin(origins ="http://localhost:4200")
@RequestMapping("/intervention")
public class InterventionController {
	@Autowired
	InterventionRepo interventionrepo;
	@Autowired
	ModuleRepo modulerepo;
	@Autowired
	EnseignantRepo ensignantrepo;
	@Autowired
	FiliereRepo filiereRepo;

//	ajouter intervention
	@PostMapping("")
	public ResponseEntity< String> addIntervention(@RequestBody InterventionDTO interventiondto) {
		Modules module = modulerepo.findById(interventiondto.getIntitule()).orElse(null);
		Enseignant enseignant = ensignantrepo.findById(interventiondto.getEmail()).orElse(null);
		if (module != null && enseignant != null) {
			InterventionId interventionid = new InterventionId(interventiondto.getIntitule(),
					interventiondto.getEmail());
			Intervention intervention = new Intervention();

			intervention.setInterventionId(interventionid);
			intervention.setVHCoursInter(interventiondto.getVhcoursInter());
			intervention.setVHtdInter(interventiondto.getVhtdInter());
			intervention.setVHtpInter(interventiondto.getVhtpInter());
			intervention.setEvaluationInter(interventiondto.getEvaluationInter());
			intervention.setEnseignant(enseignant);
			intervention.setModule(module);
			interventionrepo.save(intervention);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();

		}

	}

//recupper les intervention 
	@GetMapping("")
	public List<InterventionDTO> getAllInterventions() {
		List<Intervention> interventions = interventionrepo.findAll();

		// Mapper les objets Intervention aux objets InterventionDTO
		List<InterventionDTO> interventionDTOs = interventions.stream().map(intervention -> {
			InterventionDTO dto = new InterventionDTO();
			dto.setEmail(intervention.getEnseignant().getEmail());
			dto.setIntitule(intervention.getModule().getIntitule());
			dto.setVhcoursInter(intervention.getVHCoursInter());
			dto.setVhtdInter(intervention.getVHtdInter());
			dto.setEvaluationInter(intervention.getEvaluationInter());
			dto.setVhtpInter(intervention.getVHtpInter());
			return dto;
		}).collect(Collectors.toList());

		return interventionDTOs;
	}

	// editer intervention
	@PutMapping("{moduleId}/{enseignantId}")
	public ResponseEntity<String> updateIntervention(@PathVariable String moduleId, @PathVariable String enseignantId,
			@RequestBody InterventionDTO updatedInterventiondto) {
		// Recherchez l'intervention dans la base de données par sa clé composite
		InterventionId interventionId = new InterventionId(moduleId, enseignantId);
		Intervention existingIntervention = interventionrepo.findById(interventionId).orElse(null);

		if (existingIntervention != null) {
			// Mettre à jour les champs modifiables de l'intervention existante avec les
			// valeurs de l'intervention mise à jour
			existingIntervention.setVHCoursInter(updatedInterventiondto.getVhcoursInter());
			existingIntervention.setVHtdInter(updatedInterventiondto.getVhtdInter());
			existingIntervention.setVHtpInter(updatedInterventiondto.getVhtpInter());
			existingIntervention.setEvaluationInter(updatedInterventiondto.getEvaluationInter());

			// Enregistrer les modifications dans la base de données
			interventionrepo.save(existingIntervention);

			return ResponseEntity.ok("Intervention updated successfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

//	supprimer intervention
	@DeleteMapping("/{moduleId}/{enseignantId}")
	public ResponseEntity<String> deleteIntervention(@PathVariable String moduleId, @PathVariable String enseignantId) {
		// Recherchez l'intervention dans la base de données par sa clé composite
		InterventionId interventionId = new InterventionId(moduleId, enseignantId);
		Intervention existingIntervention = interventionrepo.findById(interventionId).orElse(null);

		if (existingIntervention != null) {
			// Supprimer l'intervention de la base de données
			interventionrepo.delete(existingIntervention);
			return ResponseEntity.ok("Intervention deleted successfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/count")
	public Map<String,Long> countModule(){
		 Map<String,Long> count =new HashMap<>();

		 count.put("module",modulerepo.count());
		 count.put("enseignant",ensignantrepo.count());
		 count.put("filiere",filiereRepo.count());
		 return count;
	}
}
