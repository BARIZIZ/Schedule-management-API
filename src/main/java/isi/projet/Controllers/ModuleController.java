package isi.projet.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import isi.projet.Models.ModuleDTO;
import isi.projet.Models.Modules;
import isi.projet.Repository.FiliereRepo;
import isi.projet.Repository.ModuleRepo;

@RestController
@RequestMapping("/module")
public class ModuleController {
	@Autowired
	ModuleRepo moduleRepo;
	@Autowired
	FiliereRepo filiererepo;

	// Recuperer les modules
	@GetMapping("")
	public List<ModuleDTO> getModules() {
		List<Modules> modules = moduleRepo.findAll();
		List<ModuleDTO> moduleDTOs = new ArrayList<>();

		for (Modules module : modules) {
			ModuleDTO moduleDTO = new ModuleDTO();
			moduleDTO.setIntitule(module.getIntitule());
			moduleDTO.setVhcours(module.getVHCours());
			moduleDTO.setVhtd(module.getVHtd());
			moduleDTO.setVhtp(module.getVHtp());
			moduleDTO.setEvaluation(module.getEvaluation());
			moduleDTO.setNomFiliere(module.getFiliere().getNomFiliere());

			moduleDTOs.add(moduleDTO);
		}

		return moduleDTOs;
	}

	// Ajouter des modlues
	@PostMapping("/add")
	public String addModule(@RequestBody ModuleDTO moduleDTO) {
		// Récupérer les données de la filière depuis le DTO
		String nomFiliere = moduleDTO.getNomFiliere();
		Filiere filiere = filiererepo.findById(nomFiliere).orElse(null);

		if (filiere != null) {
			Modules module = new Modules();
			module.setIntitule(moduleDTO.getIntitule());
			module.setVHCours(moduleDTO.getVhcours());
			module.setVHtd(moduleDTO.getVhtd());
			module.setVHtp(moduleDTO.getVhtp());
			module.setEvaluation(moduleDTO.getEvaluation());
			module.setFiliere(filiere);
			moduleRepo.save(module);
			return "Module ajouté avec succès";
		} else {
			return "Erreur: La filière spécifiée n'existe pas";
		}
	}

//editer des modules
	@PutMapping("/edit/{intitule}")
	public String updateModule(@PathVariable String intitule, @RequestBody ModuleDTO moduleDTO) {
		Modules module = moduleRepo.findById(intitule).orElse(null);
		if (module != null) {
			// Mettre à jour les propriétés du module avec les nouvelles valeurs
			module.setVHCours(moduleDTO.getVhcours());
			module.setVHtd(moduleDTO.getVhtd());
			module.setVHtp(moduleDTO.getVhtp());
			module.setEvaluation(moduleDTO.getEvaluation());

			// Rechercher la nouvelle filière dans la base de données par son nom
			Filiere nouvelleFiliere = filiererepo.findById(moduleDTO.getNomFiliere()).orElse(null);
			if (nouvelleFiliere != null) {
				// Mettre à jour la filière du module
				module.setFiliere(nouvelleFiliere);
			} else {
				return "filiere n'existe pas";
			}

			// Enregistrer les modifications dans la base de données
			moduleRepo.save(module);

			return "Module mis à jour avec succès";
		} else {
			return "Erreur: Le module spécifié n'existe pas";
		}
	}

	// recuperer par id
	@GetMapping("/{id}")
	public ResponseEntity<ModuleDTO> getModuleById(@PathVariable String id) {
		// Récupérer le module depuis la base de données en fonction de son ID
		Modules module = moduleRepo.findById(id).orElse(null);

		if (module != null) {
			ModuleDTO moduleDTO = new ModuleDTO();
			moduleDTO.setIntitule(module.getIntitule());
			moduleDTO.setVhcours(module.getVHCours());
			moduleDTO.setVhtd(module.getVHtd());
			moduleDTO.setVhtp(module.getVHtp());
			moduleDTO.setEvaluation(module.getEvaluation());
			moduleDTO.setNomFiliere(module.getFiliere().getNomFiliere());
			return ResponseEntity.ok(moduleDTO);
		} else {
			// Retourner une réponse avec le statut 404 Not Found si le module n'existe pas
			return ResponseEntity.notFound().build();
		}
	}

//   supprimer un module
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteModuleById(@PathVariable String id) {
		if (moduleRepo.existsById(id)) {
			moduleRepo.deleteById(id);
			return ResponseEntity.ok("Module supprimé avec succès");
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
