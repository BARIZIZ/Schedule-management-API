package isi.projet.Services;

import isi.projet.Models.Modules;
import isi.projet.Repository.ModuleRepoPagination;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import isi.projet.Repository.ModuleRepo;
import org.springframework.stereotype.Service;

@Service
public class ModuleService implements ModuleServiceInterface{


    ModuleRepoPagination moduleRepoPagination;

    public ModuleService(ModuleRepoPagination moduleRepoPagination) {
        this.moduleRepoPagination = moduleRepoPagination;
    }

    public Page<Modules> getModules(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return moduleRepoPagination.findAll(pageable);
    }
}
