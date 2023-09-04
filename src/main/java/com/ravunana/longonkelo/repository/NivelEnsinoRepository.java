package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.NivelEnsino;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NivelEnsino entity.
 */
@Repository
public interface NivelEnsinoRepository extends JpaRepository<NivelEnsino, Long>, JpaSpecificationExecutor<NivelEnsino> {
    default Optional<NivelEnsino> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<NivelEnsino> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<NivelEnsino> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct nivelEnsino from NivelEnsino nivelEnsino left join fetch nivelEnsino.referencia",
        countQuery = "select count(distinct nivelEnsino) from NivelEnsino nivelEnsino"
    )
    Page<NivelEnsino> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct nivelEnsino from NivelEnsino nivelEnsino left join fetch nivelEnsino.referencia")
    List<NivelEnsino> findAllWithToOneRelationships();

    @Query("select nivelEnsino from NivelEnsino nivelEnsino left join fetch nivelEnsino.referencia where nivelEnsino.id =:id")
    Optional<NivelEnsino> findOneWithToOneRelationships(@Param("id") Long id);
}
