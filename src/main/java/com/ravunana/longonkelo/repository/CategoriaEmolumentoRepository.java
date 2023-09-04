package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.CategoriaEmolumento;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CategoriaEmolumento entity.
 */
@Repository
public interface CategoriaEmolumentoRepository
    extends JpaRepository<CategoriaEmolumento, Long>, JpaSpecificationExecutor<CategoriaEmolumento> {
    default Optional<CategoriaEmolumento> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<CategoriaEmolumento> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<CategoriaEmolumento> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct categoriaEmolumento from CategoriaEmolumento categoriaEmolumento left join fetch categoriaEmolumento.planoMulta",
        countQuery = "select count(distinct categoriaEmolumento) from CategoriaEmolumento categoriaEmolumento"
    )
    Page<CategoriaEmolumento> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct categoriaEmolumento from CategoriaEmolumento categoriaEmolumento left join fetch categoriaEmolumento.planoMulta"
    )
    List<CategoriaEmolumento> findAllWithToOneRelationships();

    @Query(
        "select categoriaEmolumento from CategoriaEmolumento categoriaEmolumento left join fetch categoriaEmolumento.planoMulta where categoriaEmolumento.id =:id"
    )
    Optional<CategoriaEmolumento> findOneWithToOneRelationships(@Param("id") Long id);
}
