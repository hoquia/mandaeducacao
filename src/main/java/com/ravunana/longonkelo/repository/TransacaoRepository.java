package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.Transacao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Transacao entity.
 */
@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long>, JpaSpecificationExecutor<Transacao> {
    @Query("select transacao from Transacao transacao where transacao.utilizador.login = ?#{principal.username}")
    List<Transacao> findByUtilizadorIsCurrentUser();

    default Optional<Transacao> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Transacao> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Transacao> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct transacao from Transacao transacao left join fetch transacao.utilizador left join fetch transacao.moeda left join fetch transacao.matricula left join fetch transacao.meioPagamento left join fetch transacao.conta",
        countQuery = "select count(distinct transacao) from Transacao transacao"
    )
    Page<Transacao> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct transacao from Transacao transacao left join fetch transacao.utilizador left join fetch transacao.moeda left join fetch transacao.matricula left join fetch transacao.meioPagamento left join fetch transacao.conta"
    )
    List<Transacao> findAllWithToOneRelationships();

    @Query(
        "select transacao from Transacao transacao left join fetch transacao.utilizador left join fetch transacao.moeda left join fetch transacao.matricula left join fetch transacao.meioPagamento left join fetch transacao.conta where transacao.id =:id"
    )
    Optional<Transacao> findOneWithToOneRelationships(@Param("id") Long id);
}
