package isi.projet.Repository;

import isi.projet.Models.Modules;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepoPagination extends PagingAndSortingRepository<Modules, String> {
}
