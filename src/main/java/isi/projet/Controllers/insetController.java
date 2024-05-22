package isi.projet.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isi.projet.Repository.EnseignantRepo;
import isi.projet.Repository.FiliereRepo;
import isi.projet.Repository.InterventionRepo;
import isi.projet.Repository.ModuleRepo;
import isi.projet.Models.Enseignant;
import isi.projet.Models.Filiere;
import isi.projet.Models.Intervention;
import isi.projet.Models.InterventionId;
import isi.projet.Models.Modules;

@RestController
@RequestMapping("/add")
public class insetController {
	@Autowired
	ModuleRepo modulerepo;
	@Autowired
	EnseignantRepo ensignantrepo;
	@Autowired
	InterventionRepo iterventionrepo;
	@Autowired
	FiliereRepo filiererepo;

	@PostMapping("/module")
	public String addModule() {
		Filiere filiere = filiererepo.findById("ISI").orElse(null);
		if (filiere != null) {
			Modules module = new Modules();
			module.setIntitule("JAVA");
			module.setVHCours(30L);
			module.setVHtd(20L);
			module.setVHtp(10L);
			module.setEvaluation(3L);
			module.setFiliere(filiere);
			modulerepo.save(module);
			return "module added succefuly";
		} else {
			return "erreur";
		}

	}

	@PostMapping("/enseignant")
	public Enseignant addenseingant() {
		Enseignant e = new Enseignant();
		e.setEmail("samad@gmail.com");
		e.setNom("samad");
		e.setPrenom("BARIZIZ");
		return ensignantrepo.save(e);
	}

	@PostMapping("/intervention")
	public String addinter() {
		Modules module=modulerepo.findById("JAVA").orElse(null);
		Enseignant enseignant =ensignantrepo.findById("samad@gmail.com").orElse(null);
		// Créer une intervention et associer le module et l'enseignant
		Intervention intervention = new Intervention();
		InterventionId interventionId1 = new InterventionId();
		interventionId1.setModuleId(module.getIntitule());
		interventionId1.setEnseigantId(enseignant.getEmail());
		intervention.setInterventionId(interventionId1);
		intervention.setVHCoursInter(10L);
		intervention.setVHtdInter(5L);
		intervention.setVHtpInter(5L);
		intervention.setEvaluationInter(2L);
		intervention.setModule(module);
		intervention.setEnseignant(enseignant);
		iterventionrepo.save(intervention);

		return "done";
	}

	@PostMapping("/filiere")
	public String addfiliere() {
		Filiere filiere = new Filiere();
		filiere.setNomFiliere("ISI");
		filiererepo.save(filiere);
		return "filiere add succfully";
	}

}
