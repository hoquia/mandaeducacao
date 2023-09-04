package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.CategoriaOcorrencia;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CategoriaOcorrencia entity.
 */
@Repository
public interface CategoriaOcorrenciaRepository
    extends JpaRepository<CategoriaOcorrencia, Long>, JpaSpecificationExecutor<CategoriaOcorrencia> {
    default Optional<CategoriaOcorrencia> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<CategoriaOcorrencia> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<CategoriaOcorrencia> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct categoriaOcorrencia from CategoriaOcorrencia categoriaOcorrencia left join fetch categoriaOcorrencia.encaminhar left join fetch categoriaOcorrencia.medidaDisciplinar",
        countQuery = "select count(distinct categoriaOcorrencia) from CategoriaOcorrencia categoriaOcorrencia"
    )
    Page<CategoriaOcorrencia> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct categoriaOcorrencia from CategoriaOcorrencia categoriaOcorrencia left join fetch categoriaOcorrencia.encaminhar left join fetch categoriaOcorrencia.medidaDisciplinar"
    )
    List<CategoriaOcorrencia> findAllWithToOneRelationships();

    @Query(
        "select categoriaOcorrencia from CategoriaOcorrencia categoriaOcorrencia left join fetch categoriaOcorrencia.encaminhar left join fetch categoriaOcorrencia.medidaDisciplinar where categoriaOcorrencia.id =:id"
    )
    Optional<CategoriaOcorrencia> findOneWithToOneRelationships(@Param("id") Long id);
}
