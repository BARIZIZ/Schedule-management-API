package isi.projet.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isi.projet.Models.Enseignant;

public interface EnseignantRepo extends JpaRepository<Enseignant,String>{

}
