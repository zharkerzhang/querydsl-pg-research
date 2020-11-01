package com.zharker.database.data;

import com.zharker.database.data.customized.ExtendedQueryDslJpaRepository;
import com.zharker.database.domain.ResourceVerbRelation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;
import java.util.UUID;

public interface ResourceVerbRelationRepository extends EntityRepository<ResourceVerbRelation, UUID>, ExtendedQueryDslJpaRepository<ResourceVerbRelation> {

    @Query("select verbId from ResourceVerbRelation where resourceId = :resourceId")
    Set<UUID> findVerbIdByResourceId(@Param("resourceId") UUID resourceId);

    @Query("from ResourceVerbRelation where resourceId = :resourceId and verbId in :verbIds")
    Set<ResourceVerbRelation> findByResourceIdAndVerbIdIn(@Param("resourceId") UUID resourceId, @Param("verbIds") UUID[] verbIds);
}