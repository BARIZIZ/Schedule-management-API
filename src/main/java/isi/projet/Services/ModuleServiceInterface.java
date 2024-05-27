package isi.projet.Services;

import isi.projet.Models.Modules;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface ModuleServiceInterface {
     Page<Modules> getModules(int page, int size) ;
}
