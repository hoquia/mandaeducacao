package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.ResponsavelAreaFormacao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ResponsavelAreaFormacao entity.
 */
@Repository
public interface ResponsavelAreaFormacaoRepository
    extends JpaRepository<ResponsavelAreaFormacao, Long>, JpaSpecificationExecutor<ResponsavelAreaFormacao> {
    @Query(
        "select responsavelAreaFormacao from ResponsavelAreaFormacao responsavelAreaFormacao where responsavelAreaFormacao.utilizador.login = ?#{principal.username}"
    )
    List<ResponsavelAreaFormacao> findByUtilizadorIsCurrentUser();

    default Optional<ResponsavelAreaFormacao> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ResponsavelAreaFormacao> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ResponsavelAreaFormacao> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct responsavelAreaFormacao from ResponsavelAreaFormacao responsavelAreaFormacao left join fetch responsavelAreaFormacao.utilizador left join fetch responsavelAreaFormacao.areaFormacao",
        countQuery = "select count(distinct responsavelAreaFormacao) from ResponsavelAreaFormacao responsavelAreaFormacao"
    )
    Page<ResponsavelAreaFormacao> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct responsavelAreaFormacao from ResponsavelAreaFormacao responsavelAreaFormacao left join fetch responsavelAreaFormacao.utilizador left join fetch responsavelAreaFormacao.areaFormacao"
    )
    List<ResponsavelAreaFormacao> findAllWithToOneRelationships();

    @Query(
        "select responsavelAreaFormacao from ResponsavelAreaFormacao responsavelAreaFormacao left join fetch responsavelAreaFormacao.utilizador left join fetch responsavelAreaFormacao.areaFormacao where responsavelAreaFormacao.id =:id"
    )
    Optional<ResponsavelAreaFormacao> findOneWithToOneRelationships(@Param("id") Long id);
}
