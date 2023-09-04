package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.NotasGeralDisciplina;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NotasGeralDisciplina entity.
 */
@Repository
public interface NotasGeralDisciplinaRepository
    extends JpaRepository<NotasGeralDisciplina, Long>, JpaSpecificationExecutor<NotasGeralDisciplina> {
    @Query(
        "select notasGeralDisciplina from NotasGeralDisciplina notasGeralDisciplina where notasGeralDisciplina.utilizador.login = ?#{principal.username}"
    )
    List<NotasGeralDisciplina> findByUtilizadorIsCurrentUser();

    default Optional<NotasGeralDisciplina> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<NotasGeralDisciplina> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<NotasGeralDisciplina> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct notasGeralDisciplina from NotasGeralDisciplina notasGeralDisciplina left join fetch notasGeralDisciplina.utilizador left join fetch notasGeralDisciplina.docente left join fetch notasGeralDisciplina.disciplinaCurricular left join fetch notasGeralDisciplina.matricula left join fetch notasGeralDisciplina.estado",
        countQuery = "select count(distinct notasGeralDisciplina) from NotasGeralDisciplina notasGeralDisciplina"
    )
    Page<NotasGeralDisciplina> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct notasGeralDisciplina from NotasGeralDisciplina notasGeralDisciplina left join fetch notasGeralDisciplina.utilizador left join fetch notasGeralDisciplina.docente left join fetch notasGeralDisciplina.disciplinaCurricular left join fetch notasGeralDisciplina.matricula left join fetch notasGeralDisciplina.estado"
    )
    List<NotasGeralDisciplina> findAllWithToOneRelationships();

    @Query(
        "select notasGeralDisciplina from NotasGeralDisciplina notasGeralDisciplina left join fetch notasGeralDisciplina.utilizador left join fetch notasGeralDisciplina.docente left join fetch notasGeralDisciplina.disciplinaCurricular left join fetch notasGeralDisciplina.matricula left join fetch notasGeralDisciplina.estado where notasGeralDisciplina.id =:id"
    )
    Optional<NotasGeralDisciplina> findOneWithToOneRelationships(@Param("id") Long id);
}
