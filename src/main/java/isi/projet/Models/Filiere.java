package isi.projet.Models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="filiere")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Filiere {
@Id
private String NomFiliere;
@OneToMany
private List<Modules> module;

public Filiere(String name) {
	this.NomFiliere=name;
}
}
