package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.Imposto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Imposto entity.
 */
@Repository
public interface ImpostoRepository extends JpaRepository<Imposto, Long> {
    default Optional<Imposto> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Imposto> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Imposto> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct imposto from Imposto imposto left join fetch imposto.tipoImposto left join fetch imposto.codigoImposto left join fetch imposto.motivoIsencaoCodigo left join fetch imposto.motivoIsencaoDescricao",
        countQuery = "select count(distinct imposto) from Imposto imposto"
    )
    Page<Imposto> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct imposto from Imposto imposto left join fetch imposto.tipoImposto left join fetch imposto.codigoImposto left join fetch imposto.motivoIsencaoCodigo left join fetch imposto.motivoIsencaoDescricao"
    )
    List<Imposto> findAllWithToOneRelationships();

    @Query(
        "select imposto from Imposto imposto left join fetch imposto.tipoImposto left join fetch imposto.codigoImposto left join fetch imposto.motivoIsencaoCodigo left join fetch imposto.motivoIsencaoDescricao where imposto.id =:id"
    )
    Optional<Imposto> findOneWithToOneRelationships(@Param("id") Long id);
}
