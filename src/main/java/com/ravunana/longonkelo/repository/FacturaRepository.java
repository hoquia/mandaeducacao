package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.Factura;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Factura entity.
 */
@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long>, JpaSpecificationExecutor<Factura> {
    @Query("select factura from Factura factura where factura.utilizador.login = ?#{principal.username}")
    List<Factura> findByUtilizadorIsCurrentUser();

    default Optional<Factura> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Factura> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Factura> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct factura from Factura factura left join fetch factura.utilizador left join fetch factura.motivoAnulacao left join fetch factura.matricula left join fetch factura.referencia left join fetch factura.documentoComercial",
        countQuery = "select count(distinct factura) from Factura factura"
    )
    Page<Factura> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct factura from Factura factura left join fetch factura.utilizador left join fetch factura.motivoAnulacao left join fetch factura.matricula left join fetch factura.referencia left join fetch factura.documentoComercial"
    )
    List<Factura> findAllWithToOneRelationships();

    @Query(
        "select factura from Factura factura left join fetch factura.utilizador left join fetch factura.motivoAnulacao left join fetch factura.matricula left join fetch factura.referencia left join fetch factura.documentoComercial where factura.id =:id"
    )
    Optional<Factura> findOneWithToOneRelationships(@Param("id") Long id);
}
