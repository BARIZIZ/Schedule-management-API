package isi.projet.Repository;

import isi.projet.Models.Modules;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepoPagination extends PagingAndSortingRepository<Modules, String> {


}
