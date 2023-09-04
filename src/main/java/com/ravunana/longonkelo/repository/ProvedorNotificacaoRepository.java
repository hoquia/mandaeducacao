package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.ProvedorNotificacao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProvedorNotificacao entity.
 */
@Repository
public interface ProvedorNotificacaoRepository extends JpaRepository<ProvedorNotificacao, Long> {
    default Optional<ProvedorNotificacao> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ProvedorNotificacao> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ProvedorNotificacao> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct provedorNotificacao from ProvedorNotificacao provedorNotificacao left join fetch provedorNotificacao.instituicao",
        countQuery = "select count(distinct provedorNotificacao) from ProvedorNotificacao provedorNotificacao"
    )
    Page<ProvedorNotificacao> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct provedorNotificacao from ProvedorNotificacao provedorNotificacao left join fetch provedorNotificacao.instituicao"
    )
    List<ProvedorNotificacao> findAllWithToOneRelationships();

    @Query(
        "select provedorNotificacao from ProvedorNotificacao provedorNotificacao left join fetch provedorNotificacao.instituicao where provedorNotificacao.id =:id"
    )
    Optional<ProvedorNotificacao> findOneWithToOneRelationships(@Param("id") Long id);
}
