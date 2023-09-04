package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.Emolumento;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Emolumento entity.
 */
@Repository
public interface EmolumentoRepository extends JpaRepository<Emolumento, Long>, JpaSpecificationExecutor<Emolumento> {
    default Optional<Emolumento> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Emolumento> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Emolumento> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct emolumento from Emolumento emolumento left join fetch emolumento.categoria left join fetch emolumento.imposto left join fetch emolumento.referencia left join fetch emolumento.planoMulta",
        countQuery = "select count(distinct emolumento) from Emolumento emolumento"
    )
    Page<Emolumento> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct emolumento from Emolumento emolumento left join fetch emolumento.categoria left join fetch emolumento.imposto left join fetch emolumento.referencia left join fetch emolumento.planoMulta"
    )
    List<Emolumento> findAllWithToOneRelationships();

    @Query(
        "select emolumento from Emolumento emolumento left join fetch emolumento.categoria left join fetch emolumento.imposto left join fetch emolumento.referencia left join fetch emolumento.planoMulta where emolumento.id =:id"
    )
    Optional<Emolumento> findOneWithToOneRelationships(@Param("id") Long id);
}
