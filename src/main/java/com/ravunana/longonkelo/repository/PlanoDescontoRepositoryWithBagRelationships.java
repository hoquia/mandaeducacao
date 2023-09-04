package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.PlanoDesconto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface PlanoDescontoRepositoryWithBagRelationships {
    Optional<PlanoDesconto> fetchBagRelationships(Optional<PlanoDesconto> planoDesconto);

    List<PlanoDesconto> fetchBagRelationships(List<PlanoDesconto> planoDescontos);

    Page<PlanoDesconto> fetchBagRelationships(Page<PlanoDesconto> planoDescontos);
}
