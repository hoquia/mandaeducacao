package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.DisciplinaCurricular;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DisciplinaCurricular entity.
 *
 * When extending this class, extend DisciplinaCurricularRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface DisciplinaCurricularRepository
    extends
        DisciplinaCurricularRepositoryWithBagRelationships,
        JpaRepository<DisciplinaCurricular, Long>,
        JpaSpecificationExecutor<DisciplinaCurricular> {
    default Optional<DisciplinaCurricular> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<DisciplinaCurricular> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<DisciplinaCurricular> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct disciplinaCurricular from DisciplinaCurricular disciplinaCurricular left join fetch disciplinaCurricular.componente left join fetch disciplinaCurricular.regime left join fetch disciplinaCurricular.disciplina left join fetch disciplinaCurricular.referencia",
        countQuery = "select count(distinct disciplinaCurricular) from DisciplinaCurricular disciplinaCurricular"
    )
    Page<DisciplinaCurricular> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct disciplinaCurricular from DisciplinaCurricular disciplinaCurricular left join fetch disciplinaCurricular.componente left join fetch disciplinaCurricular.regime left join fetch disciplinaCurricular.disciplina left join fetch disciplinaCurricular.referencia"
    )
    List<DisciplinaCurricular> findAllWithToOneRelationships();

    @Query(
        "select disciplinaCurricular from DisciplinaCurricular disciplinaCurricular left join fetch disciplinaCurricular.componente left join fetch disciplinaCurricular.regime left join fetch disciplinaCurricular.disciplina left join fetch disciplinaCurricular.referencia where disciplinaCurricular.id =:id"
    )
    Optional<DisciplinaCurricular> findOneWithToOneRelationships(@Param("id") Long id);
}
