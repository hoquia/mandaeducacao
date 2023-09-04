package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.EncarregadoEducacao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EncarregadoEducacao entity.
 */
@Repository
public interface EncarregadoEducacaoRepository
    extends JpaRepository<EncarregadoEducacao, Long>, JpaSpecificationExecutor<EncarregadoEducacao> {
    default Optional<EncarregadoEducacao> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<EncarregadoEducacao> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<EncarregadoEducacao> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct encarregadoEducacao from EncarregadoEducacao encarregadoEducacao left join fetch encarregadoEducacao.grauParentesco left join fetch encarregadoEducacao.tipoDocumento left join fetch encarregadoEducacao.profissao",
        countQuery = "select count(distinct encarregadoEducacao) from EncarregadoEducacao encarregadoEducacao"
    )
    Page<EncarregadoEducacao> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct encarregadoEducacao from EncarregadoEducacao encarregadoEducacao left join fetch encarregadoEducacao.grauParentesco left join fetch encarregadoEducacao.tipoDocumento left join fetch encarregadoEducacao.profissao"
    )
    List<EncarregadoEducacao> findAllWithToOneRelationships();

    @Query(
        "select encarregadoEducacao from EncarregadoEducacao encarregadoEducacao left join fetch encarregadoEducacao.grauParentesco left join fetch encarregadoEducacao.tipoDocumento left join fetch encarregadoEducacao.profissao where encarregadoEducacao.id =:id"
    )
    Optional<EncarregadoEducacao> findOneWithToOneRelationships(@Param("id") Long id);
}
