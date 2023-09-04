package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.PlanoCurricular;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PlanoCurricular entity.
 */
@Repository
public interface PlanoCurricularRepository extends JpaRepository<PlanoCurricular, Long>, JpaSpecificationExecutor<PlanoCurricular> {
    @Query("select planoCurricular from PlanoCurricular planoCurricular where planoCurricular.utilizador.login = ?#{principal.username}")
    List<PlanoCurricular> findByUtilizadorIsCurrentUser();

    default Optional<PlanoCurricular> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PlanoCurricular> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PlanoCurricular> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct planoCurricular from PlanoCurricular planoCurricular left join fetch planoCurricular.utilizador left join fetch planoCurricular.classe left join fetch planoCurricular.curso",
        countQuery = "select count(distinct planoCurricular) from PlanoCurricular planoCurricular"
    )
    Page<PlanoCurricular> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct planoCurricular from PlanoCurricular planoCurricular left join fetch planoCurricular.utilizador left join fetch planoCurricular.classe left join fetch planoCurricular.curso"
    )
    List<PlanoCurricular> findAllWithToOneRelationships();

    @Query(
        "select planoCurricular from PlanoCurricular planoCurricular left join fetch planoCurricular.utilizador left join fetch planoCurricular.classe left join fetch planoCurricular.curso where planoCurricular.id =:id"
    )
    Optional<PlanoCurricular> findOneWithToOneRelationships(@Param("id") Long id);
}
