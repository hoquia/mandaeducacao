package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.Classe;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ClasseRepositoryWithBagRelationships {
    Optional<Classe> fetchBagRelationships(Optional<Classe> classe);

    List<Classe> fetchBagRelationships(List<Classe> classes);

    Page<Classe> fetchBagRelationships(Page<Classe> classes);
}
