package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.LongonkeloHistorico;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LongonkeloHistorico entity.
 */
@Repository
public interface LongonkeloHistoricoRepository
    extends JpaRepository<LongonkeloHistorico, Long>, JpaSpecificationExecutor<LongonkeloHistorico> {
    @Query(
        "select longonkeloHistorico from LongonkeloHistorico longonkeloHistorico where longonkeloHistorico.utilizador.login = ?#{principal.username}"
    )
    List<LongonkeloHistorico> findByUtilizadorIsCurrentUser();

    default Optional<LongonkeloHistorico> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<LongonkeloHistorico> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<LongonkeloHistorico> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct longonkeloHistorico from LongonkeloHistorico longonkeloHistorico left join fetch longonkeloHistorico.utilizador",
        countQuery = "select count(distinct longonkeloHistorico) from LongonkeloHistorico longonkeloHistorico"
    )
    Page<LongonkeloHistorico> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct longonkeloHistorico from LongonkeloHistorico longonkeloHistorico left join fetch longonkeloHistorico.utilizador"
    )
    List<LongonkeloHistorico> findAllWithToOneRelationships();

    @Query(
        "select longonkeloHistorico from LongonkeloHistorico longonkeloHistorico left join fetch longonkeloHistorico.utilizador where longonkeloHistorico.id =:id"
    )
    Optional<LongonkeloHistorico> findOneWithToOneRelationships(@Param("id") Long id);
}
