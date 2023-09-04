package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.Recibo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Recibo entity.
 */
@Repository
public interface ReciboRepository extends JpaRepository<Recibo, Long>, JpaSpecificationExecutor<Recibo> {
    @Query("select recibo from Recibo recibo where recibo.utilizador.login = ?#{principal.username}")
    List<Recibo> findByUtilizadorIsCurrentUser();

    default Optional<Recibo> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Recibo> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Recibo> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct recibo from Recibo recibo left join fetch recibo.utilizador left join fetch recibo.matricula left join fetch recibo.documentoComercial left join fetch recibo.transacao",
        countQuery = "select count(distinct recibo) from Recibo recibo"
    )
    Page<Recibo> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct recibo from Recibo recibo left join fetch recibo.utilizador left join fetch recibo.matricula left join fetch recibo.documentoComercial left join fetch recibo.transacao"
    )
    List<Recibo> findAllWithToOneRelationships();

    @Query(
        "select recibo from Recibo recibo left join fetch recibo.utilizador left join fetch recibo.matricula left join fetch recibo.documentoComercial left join fetch recibo.transacao where recibo.id =:id"
    )
    Optional<Recibo> findOneWithToOneRelationships(@Param("id") Long id);
}
