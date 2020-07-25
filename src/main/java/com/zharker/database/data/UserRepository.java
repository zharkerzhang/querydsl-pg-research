package com.zharker.database.data;

import com.zharker.database.data.customized.ExtendedQueryDslJpaRepository;
import com.zharker.database.domain.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends EntityRepository<User, UUID>, ExtendedQueryDslJpaRepository<User> {

    List<User> findByIdIn(UUID[] userIds);
}
