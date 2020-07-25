package com.zharker.database.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface EntityRepository<T, ID> extends JpaRepository<T, ID> {

    @Query("from #{#entityName} e where e.id in :ids")
    List<T> findByIdIn(@Param("ids") String... ids);

    @Query("from #{#entityName} e where e.id = :id")
    T findById(@Param("id") String id);

    @Query("select e.tId from #{#entityName} e where e.id = :id")
    ID findTIdById(@Param("id") String id);

    @Override
    @Query("from #{#entityName} e where e.tId = :id")
    Optional<T> findById(@Param("id") ID id);

}
