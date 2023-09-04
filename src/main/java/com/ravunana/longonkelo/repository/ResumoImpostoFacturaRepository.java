package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.ResumoImpostoFactura;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ResumoImpostoFactura entity.
 */
@Repository
public interface ResumoImpostoFacturaRepository extends JpaRepository<ResumoImpostoFactura, Long> {
    default Optional<ResumoImpostoFactura> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ResumoImpostoFactura> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ResumoImpostoFactura> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct resumoImpostoFactura from ResumoImpostoFactura resumoImpostoFactura left join fetch resumoImpostoFactura.factura",
        countQuery = "select count(distinct resumoImpostoFactura) from ResumoImpostoFactura resumoImpostoFactura"
    )
    Page<ResumoImpostoFactura> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct resumoImpostoFactura from ResumoImpostoFactura resumoImpostoFactura left join fetch resumoImpostoFactura.factura"
    )
    List<ResumoImpostoFactura> findAllWithToOneRelationships();

    @Query(
        "select resumoImpostoFactura from ResumoImpostoFactura resumoImpostoFactura left join fetch resumoImpostoFactura.factura where resumoImpostoFactura.id =:id"
    )
    Optional<ResumoImpostoFactura> findOneWithToOneRelationships(@Param("id") Long id);
}
