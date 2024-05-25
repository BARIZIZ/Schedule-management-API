package isi.projet.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import isi.projet.Models.Filiere;
import isi.projet.Models.ModuleDTO;
import isi.projet.Models.Modules;
import isi.projet.Repository.FiliereRepo;
import isi.projet.Repository.ModuleRepo;

@RestController
@CrossOrigin(origins ="http://localhost:4200")
@RequestMapping("/modules")
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
			moduleDTO.setVHcours(module.getVHCours());
			moduleDTO.setVHTd(module.getVHtd());
			moduleDTO.setVHTp(module.getVHtp());
			moduleDTO.setEvaluation(module.getEvaluation());
			moduleDTO.setNomFiliere(module.getFiliere().getNomFiliere());

			moduleDTOs.add(moduleDTO);
		}

		return moduleDTOs;
	}

	// Ajouter des modlues
	@PostMapping
	public ResponseEntity<String> addModule(@RequestBody ModuleDTO moduleDTO) {
		// Récupérer les données de la filière depuis le DTO
		String nomFiliere = moduleDTO.getNomFiliere();
		Filiere filiere = filiererepo.findById(nomFiliere).orElse(null);


		System.out.println("vhtd "+moduleDTO);
		if (filiere != null) {
			Modules module = new Modules();
			module.setIntitule(moduleDTO.getIntitule());
			module.setVHCours(moduleDTO.getVHcours());
			module.setVHtd(moduleDTO.getVHTd());
			module.setVHtp(moduleDTO.getVHTp());
			module.setEvaluation(moduleDTO.getEvaluation());
			module.setFiliere(filiere);
			moduleRepo.save(module);

			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build() ;
		}
	}

//editer des modules
	@PutMapping("/{intitule}")
	public ResponseEntity< String> updateModule(@PathVariable String intitule, @RequestBody ModuleDTO moduleDTO) {
		Modules module = moduleRepo.findById(intitule).orElse(null);
		if (module != null) {
			// Mettre à jour les propriétés du module avec les nouvelles valeurs
			module.setVHCours(moduleDTO.getVHcours());
			module.setVHtd(moduleDTO.getVHTd());
			module.setVHtp(moduleDTO.getVHTp());
			module.setEvaluation(moduleDTO.getEvaluation());

			// Rechercher la nouvelle filière dans la base de données par son nom
			Filiere nouvelleFiliere = filiererepo.findById(moduleDTO.getNomFiliere()).orElse(null);
			if (nouvelleFiliere != null) {
				// Mettre à jour la filière du module
				module.setFiliere(nouvelleFiliere);
			} else {
				return ResponseEntity.notFound().build();
			}

			// Enregistrer les modifications dans la base de données
			moduleRepo.save(module);

			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
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
			moduleDTO.setVHcours(module.getVHCours());
			moduleDTO.setVHTd(module.getVHtd());
			moduleDTO.setVHTp(module.getVHtp());
			moduleDTO.setEvaluation(module.getEvaluation());
			moduleDTO.setNomFiliere(module.getFiliere().getNomFiliere());
			return ResponseEntity.ok(moduleDTO);
		} else {
			// Retourner une réponse avec le statut 404 Not Found si le module n'existe pas
			return ResponseEntity.notFound().build();
		}
	}

//   supprimer un module
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteModuleById(@PathVariable String id) {
		if (moduleRepo.existsById(id)) {
			moduleRepo.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
