package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.Ocorrencia;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ocorrencia entity.
 */
@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long>, JpaSpecificationExecutor<Ocorrencia> {
    @Query("select ocorrencia from Ocorrencia ocorrencia where ocorrencia.utilizador.login = ?#{principal.username}")
    List<Ocorrencia> findByUtilizadorIsCurrentUser();

    default Optional<Ocorrencia> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Ocorrencia> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Ocorrencia> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct ocorrencia from Ocorrencia ocorrencia left join fetch ocorrencia.utilizador left join fetch ocorrencia.docente left join fetch ocorrencia.matricula left join fetch ocorrencia.estado left join fetch ocorrencia.licao",
        countQuery = "select count(distinct ocorrencia) from Ocorrencia ocorrencia"
    )
    Page<Ocorrencia> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct ocorrencia from Ocorrencia ocorrencia left join fetch ocorrencia.utilizador left join fetch ocorrencia.docente left join fetch ocorrencia.matricula left join fetch ocorrencia.estado left join fetch ocorrencia.licao"
    )
    List<Ocorrencia> findAllWithToOneRelationships();

    @Query(
        "select ocorrencia from Ocorrencia ocorrencia left join fetch ocorrencia.utilizador left join fetch ocorrencia.docente left join fetch ocorrencia.matricula left join fetch ocorrencia.estado left join fetch ocorrencia.licao where ocorrencia.id =:id"
    )
    Optional<Ocorrencia> findOneWithToOneRelationships(@Param("id") Long id);
}
