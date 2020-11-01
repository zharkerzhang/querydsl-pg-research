package com.zharker.database.data;

import com.zharker.database.data.customized.ExtendedQueryDslJpaRepository;
import com.zharker.database.domain.Verb;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;
import java.util.UUID;

public interface VerbRepository extends EntityRepository<Verb, UUID>, ExtendedQueryDslJpaRepository<Verb> {

    @Query("select tId from Verb where id in :ids")
    Set<UUID> findTIdByIdIn(@Param("ids") Set<String> ids);
}
