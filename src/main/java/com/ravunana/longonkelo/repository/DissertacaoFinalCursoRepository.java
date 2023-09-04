package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.DissertacaoFinalCurso;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DissertacaoFinalCurso entity.
 */
@Repository
public interface DissertacaoFinalCursoRepository
    extends JpaRepository<DissertacaoFinalCurso, Long>, JpaSpecificationExecutor<DissertacaoFinalCurso> {
    @Query(
        "select dissertacaoFinalCurso from DissertacaoFinalCurso dissertacaoFinalCurso where dissertacaoFinalCurso.utilizador.login = ?#{principal.username}"
    )
    List<DissertacaoFinalCurso> findByUtilizadorIsCurrentUser();

    default Optional<DissertacaoFinalCurso> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DissertacaoFinalCurso> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DissertacaoFinalCurso> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct dissertacaoFinalCurso from DissertacaoFinalCurso dissertacaoFinalCurso left join fetch dissertacaoFinalCurso.utilizador left join fetch dissertacaoFinalCurso.turma left join fetch dissertacaoFinalCurso.orientador left join fetch dissertacaoFinalCurso.especialidade left join fetch dissertacaoFinalCurso.discente left join fetch dissertacaoFinalCurso.estado left join fetch dissertacaoFinalCurso.natureza",
        countQuery = "select count(distinct dissertacaoFinalCurso) from DissertacaoFinalCurso dissertacaoFinalCurso"
    )
    Page<DissertacaoFinalCurso> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct dissertacaoFinalCurso from DissertacaoFinalCurso dissertacaoFinalCurso left join fetch dissertacaoFinalCurso.utilizador left join fetch dissertacaoFinalCurso.turma left join fetch dissertacaoFinalCurso.orientador left join fetch dissertacaoFinalCurso.especialidade left join fetch dissertacaoFinalCurso.discente left join fetch dissertacaoFinalCurso.estado left join fetch dissertacaoFinalCurso.natureza"
    )
    List<DissertacaoFinalCurso> findAllWithToOneRelationships();

    @Query(
        "select dissertacaoFinalCurso from DissertacaoFinalCurso dissertacaoFinalCurso left join fetch dissertacaoFinalCurso.utilizador left join fetch dissertacaoFinalCurso.turma left join fetch dissertacaoFinalCurso.orientador left join fetch dissertacaoFinalCurso.especialidade left join fetch dissertacaoFinalCurso.discente left join fetch dissertacaoFinalCurso.estado left join fetch dissertacaoFinalCurso.natureza where dissertacaoFinalCurso.id =:id"
    )
    Optional<DissertacaoFinalCurso> findOneWithToOneRelationships(@Param("id") Long id);
}
