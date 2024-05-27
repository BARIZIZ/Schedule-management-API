package isi.projet.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isi.projet.Models.Modules;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ModuleRepo extends JpaRepository<Modules, String> {


}
