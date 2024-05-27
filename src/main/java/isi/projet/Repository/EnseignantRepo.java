package isi.projet.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import isi.projet.Models.Enseignant;
import isi.projet.Models.EnseignantDTO;
import isi.projet.Models.EnseignantModuleDTO;

public interface EnseignantRepo extends JpaRepository<Enseignant, String> {
	@Query(value = "SELECT e.email, " +
            "GROUP_CONCAT(DISTINCT i.module_intitule ORDER BY i.module_intitule SEPARATOR ', ') AS modules, " +
            "SUM(i.evaluation_inter + i.vhtp_inter + i.vhcours_inter) AS heures " +
            "FROM enseignant e " +
            "JOIN intervention i ON e.email = i.enseignant_email " +
            "GROUP BY e.email", 
    nativeQuery = true)
    public List<Object> getEnseingantModulesHeures();
}
