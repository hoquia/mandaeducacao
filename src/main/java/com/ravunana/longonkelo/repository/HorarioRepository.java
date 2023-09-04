package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.Horario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Horario entity.
 */
@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long>, JpaSpecificationExecutor<Horario> {
    @Query("select horario from Horario horario where horario.utilizador.login = ?#{principal.username}")
    List<Horario> findByUtilizadorIsCurrentUser();

    default Optional<Horario> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Horario> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Horario> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct horario from Horario horario left join fetch horario.utilizador left join fetch horario.turma left join fetch horario.periodo left join fetch horario.docente left join fetch horario.disciplinaCurricular",
        countQuery = "select count(distinct horario) from Horario horario"
    )
    Page<Horario> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct horario from Horario horario left join fetch horario.utilizador left join fetch horario.turma left join fetch horario.periodo left join fetch horario.docente left join fetch horario.disciplinaCurricular"
    )
    List<Horario> findAllWithToOneRelationships();

    @Query(
        "select horario from Horario horario left join fetch horario.utilizador left join fetch horario.turma left join fetch horario.periodo left join fetch horario.docente left join fetch horario.disciplinaCurricular where horario.id =:id"
    )
    Optional<Horario> findOneWithToOneRelationships(@Param("id") Long id);
}
