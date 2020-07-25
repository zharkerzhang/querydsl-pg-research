package com.zharker.database.service;

import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.zharker.database.data.UserRepository;
import com.zharker.database.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class UserService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private UserRepository userRepo;


    public List<User> dynamicSearch(Predicate predicate) {
        Iterable<User> result = userRepo.findAll(predicate);
        return Lists.newArrayList(result);
    }

    public Page<User> dynamicSearch(JPAQuery<User> jpaQuery, Pageable pageable) {
        return userRepo.findAll(jpaQuery, pageable);
    }


    public User findById(String id) {
        return userRepo.findById(id);

    }

    public String save(User user) {
        userRepo.save(user);
        return user.getId();
    }
}
