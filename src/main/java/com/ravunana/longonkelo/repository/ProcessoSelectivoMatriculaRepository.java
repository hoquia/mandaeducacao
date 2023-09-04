package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.ProcessoSelectivoMatricula;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProcessoSelectivoMatricula entity.
 */
@Repository
public interface ProcessoSelectivoMatriculaRepository
    extends JpaRepository<ProcessoSelectivoMatricula, Long>, JpaSpecificationExecutor<ProcessoSelectivoMatricula> {
    @Query(
        "select processoSelectivoMatricula from ProcessoSelectivoMatricula processoSelectivoMatricula where processoSelectivoMatricula.utilizador.login = ?#{principal.username}"
    )
    List<ProcessoSelectivoMatricula> findByUtilizadorIsCurrentUser();

    default Optional<ProcessoSelectivoMatricula> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ProcessoSelectivoMatricula> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ProcessoSelectivoMatricula> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct processoSelectivoMatricula from ProcessoSelectivoMatricula processoSelectivoMatricula left join fetch processoSelectivoMatricula.utilizador left join fetch processoSelectivoMatricula.turma left join fetch processoSelectivoMatricula.discente",
        countQuery = "select count(distinct processoSelectivoMatricula) from ProcessoSelectivoMatricula processoSelectivoMatricula"
    )
    Page<ProcessoSelectivoMatricula> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct processoSelectivoMatricula from ProcessoSelectivoMatricula processoSelectivoMatricula left join fetch processoSelectivoMatricula.utilizador left join fetch processoSelectivoMatricula.turma left join fetch processoSelectivoMatricula.discente"
    )
    List<ProcessoSelectivoMatricula> findAllWithToOneRelationships();

    @Query(
        "select processoSelectivoMatricula from ProcessoSelectivoMatricula processoSelectivoMatricula left join fetch processoSelectivoMatricula.utilizador left join fetch processoSelectivoMatricula.turma left join fetch processoSelectivoMatricula.discente where processoSelectivoMatricula.id =:id"
    )
    Optional<ProcessoSelectivoMatricula> findOneWithToOneRelationships(@Param("id") Long id);
}
