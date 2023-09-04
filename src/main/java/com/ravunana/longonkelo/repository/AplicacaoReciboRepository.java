package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.AplicacaoRecibo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AplicacaoRecibo entity.
 */
@Repository
public interface AplicacaoReciboRepository extends JpaRepository<AplicacaoRecibo, Long> {
    default Optional<AplicacaoRecibo> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AplicacaoRecibo> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AplicacaoRecibo> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct aplicacaoRecibo from AplicacaoRecibo aplicacaoRecibo left join fetch aplicacaoRecibo.factura left join fetch aplicacaoRecibo.recibo",
        countQuery = "select count(distinct aplicacaoRecibo) from AplicacaoRecibo aplicacaoRecibo"
    )
    Page<AplicacaoRecibo> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct aplicacaoRecibo from AplicacaoRecibo aplicacaoRecibo left join fetch aplicacaoRecibo.factura left join fetch aplicacaoRecibo.recibo"
    )
    List<AplicacaoRecibo> findAllWithToOneRelationships();

    @Query(
        "select aplicacaoRecibo from AplicacaoRecibo aplicacaoRecibo left join fetch aplicacaoRecibo.factura left join fetch aplicacaoRecibo.recibo where aplicacaoRecibo.id =:id"
    )
    Optional<AplicacaoRecibo> findOneWithToOneRelationships(@Param("id") Long id);
}
