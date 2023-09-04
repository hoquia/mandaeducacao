package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.ItemFactura;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ItemFactura entity.
 */
@Repository
public interface ItemFacturaRepository extends JpaRepository<ItemFactura, Long>, JpaSpecificationExecutor<ItemFactura> {
    default Optional<ItemFactura> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ItemFactura> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ItemFactura> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct itemFactura from ItemFactura itemFactura left join fetch itemFactura.factura left join fetch itemFactura.emolumento",
        countQuery = "select count(distinct itemFactura) from ItemFactura itemFactura"
    )
    Page<ItemFactura> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct itemFactura from ItemFactura itemFactura left join fetch itemFactura.factura left join fetch itemFactura.emolumento"
    )
    List<ItemFactura> findAllWithToOneRelationships();

    @Query(
        "select itemFactura from ItemFactura itemFactura left join fetch itemFactura.factura left join fetch itemFactura.emolumento where itemFactura.id =:id"
    )
    Optional<ItemFactura> findOneWithToOneRelationships(@Param("id") Long id);
}
