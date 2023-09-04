package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.HistoricoSaude;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HistoricoSaude entity.
 */
@Repository
public interface HistoricoSaudeRepository extends JpaRepository<HistoricoSaude, Long>, JpaSpecificationExecutor<HistoricoSaude> {
    @Query("select historicoSaude from HistoricoSaude historicoSaude where historicoSaude.utilizador.login = ?#{principal.username}")
    List<HistoricoSaude> findByUtilizadorIsCurrentUser();

    default Optional<HistoricoSaude> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<HistoricoSaude> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<HistoricoSaude> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct historicoSaude from HistoricoSaude historicoSaude left join fetch historicoSaude.utilizador left join fetch historicoSaude.discente",
        countQuery = "select count(distinct historicoSaude) from HistoricoSaude historicoSaude"
    )
    Page<HistoricoSaude> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct historicoSaude from HistoricoSaude historicoSaude left join fetch historicoSaude.utilizador left join fetch historicoSaude.discente"
    )
    List<HistoricoSaude> findAllWithToOneRelationships();

    @Query(
        "select historicoSaude from HistoricoSaude historicoSaude left join fetch historicoSaude.utilizador left join fetch historicoSaude.discente where historicoSaude.id =:id"
    )
    Optional<HistoricoSaude> findOneWithToOneRelationships(@Param("id") Long id);
}
