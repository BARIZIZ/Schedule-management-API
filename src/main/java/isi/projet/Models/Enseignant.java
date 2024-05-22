package isi.projet.Models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "enseignant")
public class Enseignant {
	@Id
	private String email;
	private String nom;
	private String prenom;
	@OneToMany(mappedBy = "enseignant")
    private Set<Intervention> interventions = new HashSet<>();


}
