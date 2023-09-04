package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.PeriodoLancamentoNota;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PeriodoLancamentoNota entity.
 *
 * When extending this class, extend PeriodoLancamentoNotaRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface PeriodoLancamentoNotaRepository
    extends
        PeriodoLancamentoNotaRepositoryWithBagRelationships,
        JpaRepository<PeriodoLancamentoNota, Long>,
        JpaSpecificationExecutor<PeriodoLancamentoNota> {
    @Query(
        "select periodoLancamentoNota from PeriodoLancamentoNota periodoLancamentoNota where periodoLancamentoNota.utilizador.login = ?#{principal.username}"
    )
    List<PeriodoLancamentoNota> findByUtilizadorIsCurrentUser();

    default Optional<PeriodoLancamentoNota> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<PeriodoLancamentoNota> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<PeriodoLancamentoNota> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct periodoLancamentoNota from PeriodoLancamentoNota periodoLancamentoNota left join fetch periodoLancamentoNota.utilizador",
        countQuery = "select count(distinct periodoLancamentoNota) from PeriodoLancamentoNota periodoLancamentoNota"
    )
    Page<PeriodoLancamentoNota> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct periodoLancamentoNota from PeriodoLancamentoNota periodoLancamentoNota left join fetch periodoLancamentoNota.utilizador"
    )
    List<PeriodoLancamentoNota> findAllWithToOneRelationships();

    @Query(
        "select periodoLancamentoNota from PeriodoLancamentoNota periodoLancamentoNota left join fetch periodoLancamentoNota.utilizador where periodoLancamentoNota.id =:id"
    )
    Optional<PeriodoLancamentoNota> findOneWithToOneRelationships(@Param("id") Long id);
}
