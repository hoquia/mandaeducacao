package com.ravunana.longonkelo.repository;

import com.ravunana.longonkelo.domain.LookupItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LookupItem entity.
 */
@Repository
public interface LookupItemRepository extends JpaRepository<LookupItem, Long>, JpaSpecificationExecutor<LookupItem> {
    default Optional<LookupItem> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<LookupItem> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<LookupItem> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct lookupItem from LookupItem lookupItem left join fetch lookupItem.lookup",
        countQuery = "select count(distinct lookupItem) from LookupItem lookupItem"
    )
    Page<LookupItem> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct lookupItem from LookupItem lookupItem left join fetch lookupItem.lookup")
    List<LookupItem> findAllWithToOneRelationships();

    @Query("select lookupItem from LookupItem lookupItem left join fetch lookupItem.lookup where lookupItem.id =:id")
    Optional<LookupItem> findOneWithToOneRelationships(@Param("id") Long id);
}
