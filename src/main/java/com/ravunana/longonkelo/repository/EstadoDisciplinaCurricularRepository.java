package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.EstadoDisciplinaCurricular;
import com.ravunana.longonkelo.domain.enumeration.CategoriaClassificacao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EstadoDisciplinaCurricular entity.
 *
 * When extending this class, extend EstadoDisciplinaCurricularRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface EstadoDisciplinaCurricularRepository
    extends
        EstadoDisciplinaCurricularRepositoryWithBagRelationships,
        JpaRepository<EstadoDisciplinaCurricular, Long>,
        JpaSpecificationExecutor<EstadoDisciplinaCurricular> {
    default Optional<EstadoDisciplinaCurricular> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<EstadoDisciplinaCurricular> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<EstadoDisciplinaCurricular> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }

    Optional<EstadoDisciplinaCurricular> findByClassificacao(CategoriaClassificacao classificacao);
}
