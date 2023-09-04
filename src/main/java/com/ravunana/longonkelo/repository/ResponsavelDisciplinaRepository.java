package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.ResponsavelDisciplina;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ResponsavelDisciplina entity.
 */
@Repository
public interface ResponsavelDisciplinaRepository
    extends JpaRepository<ResponsavelDisciplina, Long>, JpaSpecificationExecutor<ResponsavelDisciplina> {
    @Query(
        "select responsavelDisciplina from ResponsavelDisciplina responsavelDisciplina where responsavelDisciplina.utilizador.login = ?#{principal.username}"
    )
    List<ResponsavelDisciplina> findByUtilizadorIsCurrentUser();

    default Optional<ResponsavelDisciplina> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ResponsavelDisciplina> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ResponsavelDisciplina> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct responsavelDisciplina from ResponsavelDisciplina responsavelDisciplina left join fetch responsavelDisciplina.utilizador left join fetch responsavelDisciplina.disciplina",
        countQuery = "select count(distinct responsavelDisciplina) from ResponsavelDisciplina responsavelDisciplina"
    )
    Page<ResponsavelDisciplina> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct responsavelDisciplina from ResponsavelDisciplina responsavelDisciplina left join fetch responsavelDisciplina.utilizador left join fetch responsavelDisciplina.disciplina"
    )
    List<ResponsavelDisciplina> findAllWithToOneRelationships();

    @Query(
        "select responsavelDisciplina from ResponsavelDisciplina responsavelDisciplina left join fetch responsavelDisciplina.utilizador left join fetch responsavelDisciplina.disciplina where responsavelDisciplina.id =:id"
    )
    Optional<ResponsavelDisciplina> findOneWithToOneRelationships(@Param("id") Long id);
}
