package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.Discente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Discente entity.
 */
@Repository
public interface DiscenteRepository extends JpaRepository<Discente, Long>, JpaSpecificationExecutor<Discente> {
    default Optional<Discente> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Discente> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Discente> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct discente from Discente discente left join fetch discente.nacionalidade left join fetch discente.naturalidade left join fetch discente.tipoDocumento left join fetch discente.profissao left join fetch discente.grupoSanguinio left join fetch discente.necessidadeEspecial left join fetch discente.encarregadoEducacao",
        countQuery = "select count(distinct discente) from Discente discente"
    )
    Page<Discente> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct discente from Discente discente left join fetch discente.nacionalidade left join fetch discente.naturalidade left join fetch discente.tipoDocumento left join fetch discente.profissao left join fetch discente.grupoSanguinio left join fetch discente.necessidadeEspecial left join fetch discente.encarregadoEducacao"
    )
    List<Discente> findAllWithToOneRelationships();

    @Query(
        "select discente from Discente discente left join fetch discente.nacionalidade left join fetch discente.naturalidade left join fetch discente.tipoDocumento left join fetch discente.profissao left join fetch discente.grupoSanguinio left join fetch discente.necessidadeEspecial left join fetch discente.encarregadoEducacao where discente.id =:id"
    )
    Optional<Discente> findOneWithToOneRelationships(@Param("id") Long id);
}
