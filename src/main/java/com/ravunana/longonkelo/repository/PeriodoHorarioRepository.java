package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.PeriodoHorario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PeriodoHorario entity.
 */
@Repository
public interface PeriodoHorarioRepository extends JpaRepository<PeriodoHorario, Long>, JpaSpecificationExecutor<PeriodoHorario> {
    default Optional<PeriodoHorario> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PeriodoHorario> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PeriodoHorario> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct periodoHorario from PeriodoHorario periodoHorario left join fetch periodoHorario.turno",
        countQuery = "select count(distinct periodoHorario) from PeriodoHorario periodoHorario"
    )
    Page<PeriodoHorario> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct periodoHorario from PeriodoHorario periodoHorario left join fetch periodoHorario.turno")
    List<PeriodoHorario> findAllWithToOneRelationships();

    @Query("select periodoHorario from PeriodoHorario periodoHorario left join fetch periodoHorario.turno where periodoHorario.id =:id")
    Optional<PeriodoHorario> findOneWithToOneRelationships(@Param("id") Long id);
}
