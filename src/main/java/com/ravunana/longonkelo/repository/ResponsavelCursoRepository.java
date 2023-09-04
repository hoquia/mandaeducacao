package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.ResponsavelCurso;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ResponsavelCurso entity.
 */
@Repository
public interface ResponsavelCursoRepository extends JpaRepository<ResponsavelCurso, Long>, JpaSpecificationExecutor<ResponsavelCurso> {
    @Query(
        "select responsavelCurso from ResponsavelCurso responsavelCurso where responsavelCurso.utilizador.login = ?#{principal.username}"
    )
    List<ResponsavelCurso> findByUtilizadorIsCurrentUser();

    default Optional<ResponsavelCurso> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ResponsavelCurso> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ResponsavelCurso> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct responsavelCurso from ResponsavelCurso responsavelCurso left join fetch responsavelCurso.utilizador left join fetch responsavelCurso.curso",
        countQuery = "select count(distinct responsavelCurso) from ResponsavelCurso responsavelCurso"
    )
    Page<ResponsavelCurso> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct responsavelCurso from ResponsavelCurso responsavelCurso left join fetch responsavelCurso.utilizador left join fetch responsavelCurso.curso"
    )
    List<ResponsavelCurso> findAllWithToOneRelationships();

    @Query(
        "select responsavelCurso from ResponsavelCurso responsavelCurso left join fetch responsavelCurso.utilizador left join fetch responsavelCurso.curso where responsavelCurso.id =:id"
    )
    Optional<ResponsavelCurso> findOneWithToOneRelationships(@Param("id") Long id);
}
