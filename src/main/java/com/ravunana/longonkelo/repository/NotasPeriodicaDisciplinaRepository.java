package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.NotasPeriodicaDisciplina;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NotasPeriodicaDisciplina entity.
 */
@Repository
public interface NotasPeriodicaDisciplinaRepository
    extends JpaRepository<NotasPeriodicaDisciplina, Long>, JpaSpecificationExecutor<NotasPeriodicaDisciplina> {
    @Query(
        "select notasPeriodicaDisciplina from NotasPeriodicaDisciplina notasPeriodicaDisciplina where notasPeriodicaDisciplina.utilizador.login = ?#{principal.username}"
    )
    List<NotasPeriodicaDisciplina> findByUtilizadorIsCurrentUser();

    default Optional<NotasPeriodicaDisciplina> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<NotasPeriodicaDisciplina> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<NotasPeriodicaDisciplina> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct notasPeriodicaDisciplina from NotasPeriodicaDisciplina notasPeriodicaDisciplina left join fetch notasPeriodicaDisciplina.utilizador left join fetch notasPeriodicaDisciplina.turma left join fetch notasPeriodicaDisciplina.docente left join fetch notasPeriodicaDisciplina.disciplinaCurricular left join fetch notasPeriodicaDisciplina.matricula left join fetch notasPeriodicaDisciplina.estado",
        countQuery = "select count(distinct notasPeriodicaDisciplina) from NotasPeriodicaDisciplina notasPeriodicaDisciplina"
    )
    Page<NotasPeriodicaDisciplina> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct notasPeriodicaDisciplina from NotasPeriodicaDisciplina notasPeriodicaDisciplina left join fetch notasPeriodicaDisciplina.utilizador left join fetch notasPeriodicaDisciplina.turma left join fetch notasPeriodicaDisciplina.docente left join fetch notasPeriodicaDisciplina.disciplinaCurricular left join fetch notasPeriodicaDisciplina.matricula left join fetch notasPeriodicaDisciplina.estado"
    )
    List<NotasPeriodicaDisciplina> findAllWithToOneRelationships();

    @Query(
        "select notasPeriodicaDisciplina from NotasPeriodicaDisciplina notasPeriodicaDisciplina left join fetch notasPeriodicaDisciplina.utilizador left join fetch notasPeriodicaDisciplina.turma left join fetch notasPeriodicaDisciplina.docente left join fetch notasPeriodicaDisciplina.disciplinaCurricular left join fetch notasPeriodicaDisciplina.matricula left join fetch notasPeriodicaDisciplina.estado where notasPeriodicaDisciplina.id =:id"
    )
    Optional<NotasPeriodicaDisciplina> findOneWithToOneRelationships(@Param("id") Long id);
}
