package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.ResumoAcademico;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ResumoAcademico entity.
 */
@Repository
public interface ResumoAcademicoRepository extends JpaRepository<ResumoAcademico, Long>, JpaSpecificationExecutor<ResumoAcademico> {
    @Query("select resumoAcademico from ResumoAcademico resumoAcademico where resumoAcademico.utilizador.login = ?#{principal.username}")
    List<ResumoAcademico> findByUtilizadorIsCurrentUser();

    default Optional<ResumoAcademico> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ResumoAcademico> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ResumoAcademico> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct resumoAcademico from ResumoAcademico resumoAcademico left join fetch resumoAcademico.utilizador left join fetch resumoAcademico.ultimaTurmaMatriculada left join fetch resumoAcademico.discente left join fetch resumoAcademico.situacao",
        countQuery = "select count(distinct resumoAcademico) from ResumoAcademico resumoAcademico"
    )
    Page<ResumoAcademico> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct resumoAcademico from ResumoAcademico resumoAcademico left join fetch resumoAcademico.utilizador left join fetch resumoAcademico.ultimaTurmaMatriculada left join fetch resumoAcademico.discente left join fetch resumoAcademico.situacao"
    )
    List<ResumoAcademico> findAllWithToOneRelationships();

    @Query(
        "select resumoAcademico from ResumoAcademico resumoAcademico left join fetch resumoAcademico.utilizador left join fetch resumoAcademico.ultimaTurmaMatriculada left join fetch resumoAcademico.discente left join fetch resumoAcademico.situacao where resumoAcademico.id =:id"
    )
    Optional<ResumoAcademico> findOneWithToOneRelationships(@Param("id") Long id);
}
