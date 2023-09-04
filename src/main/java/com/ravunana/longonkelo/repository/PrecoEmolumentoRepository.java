package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.PrecoEmolumento;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PrecoEmolumento entity.
 */
@Repository
public interface PrecoEmolumentoRepository extends JpaRepository<PrecoEmolumento, Long>, JpaSpecificationExecutor<PrecoEmolumento> {
    @Query("select precoEmolumento from PrecoEmolumento precoEmolumento where precoEmolumento.utilizador.login = ?#{principal.username}")
    List<PrecoEmolumento> findByUtilizadorIsCurrentUser();

    default Optional<PrecoEmolumento> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PrecoEmolumento> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PrecoEmolumento> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct precoEmolumento from PrecoEmolumento precoEmolumento left join fetch precoEmolumento.utilizador left join fetch precoEmolumento.emolumento left join fetch precoEmolumento.areaFormacao left join fetch precoEmolumento.curso left join fetch precoEmolumento.classe left join fetch precoEmolumento.turno left join fetch precoEmolumento.planoMulta",
        countQuery = "select count(distinct precoEmolumento) from PrecoEmolumento precoEmolumento"
    )
    Page<PrecoEmolumento> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct precoEmolumento from PrecoEmolumento precoEmolumento left join fetch precoEmolumento.utilizador left join fetch precoEmolumento.emolumento left join fetch precoEmolumento.areaFormacao left join fetch precoEmolumento.curso left join fetch precoEmolumento.classe left join fetch precoEmolumento.turno left join fetch precoEmolumento.planoMulta"
    )
    List<PrecoEmolumento> findAllWithToOneRelationships();

    @Query(
        "select precoEmolumento from PrecoEmolumento precoEmolumento left join fetch precoEmolumento.utilizador left join fetch precoEmolumento.emolumento left join fetch precoEmolumento.areaFormacao left join fetch precoEmolumento.curso left join fetch precoEmolumento.classe left join fetch precoEmolumento.turno left join fetch precoEmolumento.planoMulta where precoEmolumento.id =:id"
    )
    Optional<PrecoEmolumento> findOneWithToOneRelationships(@Param("id") Long id);
}
