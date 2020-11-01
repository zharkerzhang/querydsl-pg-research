package com.zharker.database.data;

import com.zharker.database.data.customized.ExtendedQueryDslJpaRepository;
import com.zharker.database.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface UserRepository extends EntityRepository<User, UUID>, ExtendedQueryDslJpaRepository<User> {

    List<User> findByIdIn(UUID[] userIds);

    @Query(nativeQuery = true, value = "select u.* from \"user\" u where u.date_of_birth = :date")
    List<User> findByDateOfBirthLessThanEqual(@Param("date") Date date);
}
