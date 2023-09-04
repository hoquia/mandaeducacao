package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.PlanoAula;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PlanoAula entity.
 */
@Repository
public interface PlanoAulaRepository extends JpaRepository<PlanoAula, Long>, JpaSpecificationExecutor<PlanoAula> {
    @Query("select planoAula from PlanoAula planoAula where planoAula.utilizador.login = ?#{principal.username}")
    List<PlanoAula> findByUtilizadorIsCurrentUser();

    default Optional<PlanoAula> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PlanoAula> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PlanoAula> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct planoAula from PlanoAula planoAula left join fetch planoAula.utilizador left join fetch planoAula.unidadeTematica left join fetch planoAula.subUnidadeTematica left join fetch planoAula.turma left join fetch planoAula.docente left join fetch planoAula.disciplinaCurricular",
        countQuery = "select count(distinct planoAula) from PlanoAula planoAula"
    )
    Page<PlanoAula> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct planoAula from PlanoAula planoAula left join fetch planoAula.utilizador left join fetch planoAula.unidadeTematica left join fetch planoAula.subUnidadeTematica left join fetch planoAula.turma left join fetch planoAula.docente left join fetch planoAula.disciplinaCurricular"
    )
    List<PlanoAula> findAllWithToOneRelationships();

    @Query(
        "select planoAula from PlanoAula planoAula left join fetch planoAula.utilizador left join fetch planoAula.unidadeTematica left join fetch planoAula.subUnidadeTematica left join fetch planoAula.turma left join fetch planoAula.docente left join fetch planoAula.disciplinaCurricular where planoAula.id =:id"
    )
    Optional<PlanoAula> findOneWithToOneRelationships(@Param("id") Long id);
}
