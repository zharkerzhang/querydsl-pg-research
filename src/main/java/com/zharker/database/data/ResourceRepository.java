package com.zharker.database.data;


import com.zharker.database.data.customized.ExtendedQueryDslJpaRepository;
import com.zharker.database.domain.Resource;

import java.util.UUID;

public interface ResourceRepository extends EntityRepository<Resource, UUID>, ExtendedQueryDslJpaRepository<Resource> {
}
