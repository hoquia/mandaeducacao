package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.InstituicaoEnsino;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InstituicaoEnsino entity.
 */
@Repository
public interface InstituicaoEnsinoRepository extends JpaRepository<InstituicaoEnsino, Long>, JpaSpecificationExecutor<InstituicaoEnsino> {
    default Optional<InstituicaoEnsino> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<InstituicaoEnsino> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<InstituicaoEnsino> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct instituicaoEnsino from InstituicaoEnsino instituicaoEnsino left join fetch instituicaoEnsino.categoriaInstituicao left join fetch instituicaoEnsino.unidadePagadora left join fetch instituicaoEnsino.tipoVinculo left join fetch instituicaoEnsino.tipoInstalacao left join fetch instituicaoEnsino.sede",
        countQuery = "select count(distinct instituicaoEnsino) from InstituicaoEnsino instituicaoEnsino"
    )
    Page<InstituicaoEnsino> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct instituicaoEnsino from InstituicaoEnsino instituicaoEnsino left join fetch instituicaoEnsino.categoriaInstituicao left join fetch instituicaoEnsino.unidadePagadora left join fetch instituicaoEnsino.tipoVinculo left join fetch instituicaoEnsino.tipoInstalacao left join fetch instituicaoEnsino.sede"
    )
    List<InstituicaoEnsino> findAllWithToOneRelationships();

    @Query(
        "select instituicaoEnsino from InstituicaoEnsino instituicaoEnsino left join fetch instituicaoEnsino.categoriaInstituicao left join fetch instituicaoEnsino.unidadePagadora left join fetch instituicaoEnsino.tipoVinculo left join fetch instituicaoEnsino.tipoInstalacao left join fetch instituicaoEnsino.sede where instituicaoEnsino.id =:id"
    )
    Optional<InstituicaoEnsino> findOneWithToOneRelationships(@Param("id") Long id);
}
