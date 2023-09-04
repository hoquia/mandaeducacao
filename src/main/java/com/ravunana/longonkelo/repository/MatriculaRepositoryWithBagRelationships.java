package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.Matricula;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface MatriculaRepositoryWithBagRelationships {
    Optional<Matricula> fetchBagRelationships(Optional<Matricula> matricula);

    List<Matricula> fetchBagRelationships(List<Matricula> matriculas);

    Page<Matricula> fetchBagRelationships(Page<Matricula> matriculas);
}
