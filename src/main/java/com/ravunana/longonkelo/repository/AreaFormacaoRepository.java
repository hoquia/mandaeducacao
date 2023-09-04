package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.AreaFormacao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AreaFormacao entity.
 */
@Repository
public interface AreaFormacaoRepository extends JpaRepository<AreaFormacao, Long> {
    default Optional<AreaFormacao> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AreaFormacao> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AreaFormacao> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct areaFormacao from AreaFormacao areaFormacao left join fetch areaFormacao.nivelEnsino",
        countQuery = "select count(distinct areaFormacao) from AreaFormacao areaFormacao"
    )
    Page<AreaFormacao> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct areaFormacao from AreaFormacao areaFormacao left join fetch areaFormacao.nivelEnsino")
    List<AreaFormacao> findAllWithToOneRelationships();

    @Query("select areaFormacao from AreaFormacao areaFormacao left join fetch areaFormacao.nivelEnsino where areaFormacao.id =:id")
    Optional<AreaFormacao> findOneWithToOneRelationships(@Param("id") Long id);
}
