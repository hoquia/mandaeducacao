package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.ResponsavelTurno;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ResponsavelTurno entity.
 */
@Repository
public interface ResponsavelTurnoRepository extends JpaRepository<ResponsavelTurno, Long>, JpaSpecificationExecutor<ResponsavelTurno> {
    @Query(
        "select responsavelTurno from ResponsavelTurno responsavelTurno where responsavelTurno.utilizador.login = ?#{principal.username}"
    )
    List<ResponsavelTurno> findByUtilizadorIsCurrentUser();

    default Optional<ResponsavelTurno> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ResponsavelTurno> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ResponsavelTurno> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct responsavelTurno from ResponsavelTurno responsavelTurno left join fetch responsavelTurno.utilizador left join fetch responsavelTurno.turno",
        countQuery = "select count(distinct responsavelTurno) from ResponsavelTurno responsavelTurno"
    )
    Page<ResponsavelTurno> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct responsavelTurno from ResponsavelTurno responsavelTurno left join fetch responsavelTurno.utilizador left join fetch responsavelTurno.turno"
    )
    List<ResponsavelTurno> findAllWithToOneRelationships();

    @Query(
        "select responsavelTurno from ResponsavelTurno responsavelTurno left join fetch responsavelTurno.utilizador left join fetch responsavelTurno.turno where responsavelTurno.id =:id"
    )
    Optional<ResponsavelTurno> findOneWithToOneRelationships(@Param("id") Long id);
}
