package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.PlanoDesconto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PlanoDesconto entity.
 *
 * When extending this class, extend PlanoDescontoRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface PlanoDescontoRepository
    extends PlanoDescontoRepositoryWithBagRelationships, JpaRepository<PlanoDesconto, Long>, JpaSpecificationExecutor<PlanoDesconto> {
    default Optional<PlanoDesconto> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<PlanoDesconto> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<PlanoDesconto> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
