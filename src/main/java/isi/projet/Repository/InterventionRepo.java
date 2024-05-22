package isi.projet.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isi.projet.Models.Intervention;
import isi.projet.Models.InterventionId;

public interface InterventionRepo extends JpaRepository<Intervention, InterventionId>{

}
