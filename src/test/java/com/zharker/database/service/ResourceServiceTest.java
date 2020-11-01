package com.zharker.database.service;

import com.google.common.collect.Sets;
import com.zharker.database.domain.Resource;
import com.zharker.database.domain.Verb;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Config.class)
public class ResourceServiceTest {

    @Autowired
    private ResourceService resourceService;

    @Test
    @Sql(scripts = "/sql/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
    public void saveNewResourceNewVerbs() {

        Resource resource = getCreateResource();
        resourceService.save(resource);
    }

    @Test
    @Sql(scripts = {"/sql/resource.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/sql/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
    public void saveNewResourceExistVerbs() {

        Resource resource = getCreateResource();
        Verb v = new Verb();
        v.setId("edit");
        Set<Verb> verbs = Sets.newHashSet(v);
        resource.setVerbs(verbs);
        resourceService.save(resource);
    }

    @Test
    @Sql(scripts = {"/sql/resource.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/sql/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
    public void saveNewResourceNewAndExistVerbs() {

        Resource resource = getCreateResource();
        Verb existVerb = new Verb();
        existVerb.setId("edit");
        Set<Verb> verbs = Sets.newHashSet(existVerb);
        resource.setVerbs(verbs);
        Verb newVerb = new Verb();
        newVerb.setId("new");
        verbs.add(newVerb);
        resourceService.save(resource);
    }

    @Test
    @Sql(scripts = {"/sql/resource.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/sql/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
    public void saveExistResourceNewVerbs() {

        Resource existResource = resourceService.getById("test resource");
        existResource.setName("update name");
        Verb newVerb = new Verb();
        newVerb.setId("new");
        newVerb.setName("new");
        Set<Verb> verbs = Sets.newHashSet(newVerb);
        verbs.add(newVerb);
        resourceService.update(existResource, verbs);
    }

    @Test
    @Sql(scripts = {"/sql/resource.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/sql/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
    public void saveExistResourceNewAndExistVerbs(){
        Resource existResource = resourceService.getById("test resource2");
        existResource.setName("update name2");
        Verb existVerb = new Verb();
        existVerb.setId("edit");
        Set<Verb> verbs = Sets.newHashSet(existVerb);
        Verb newVerb = new Verb();
        newVerb.setId("new");
        newVerb.setName("new");
        verbs.add(newVerb);
        resourceService.update(existResource, verbs);
    }

    @Test
    @Sql(scripts = {"/sql/resource.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/sql/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
    public void saveExistResourceNewAndRemoveVerbs(){
        Resource existResource = resourceService.getById("test resource3");
        existResource.setName("update name3");
        Verb newVerb = new Verb();
        newVerb.setId("new");
        newVerb.setName("new");
        Set<Verb> verbs = Sets.newHashSet(newVerb);
        resourceService.update(existResource, verbs);
    }



    private Resource getCreateResource() {
        Resource r = new Resource();
        r.setAsset(RandomStringUtils.randomAlphabetic(10));
        r.setName(RandomStringUtils.randomAlphabetic(10));
        r.setUri(RandomStringUtils.randomAlphabetic(100));
        Verb v1 = new Verb();
        v1.setName(RandomStringUtils.randomAlphabetic(100));
        Verb v2 = new Verb();
        v2.setName(RandomStringUtils.randomAlphabetic(100));
        Set<Verb> verbs = Sets.newHashSet();
        verbs.add(v1);
        verbs.add(v2);
        r.setVerbs(Sets.newHashSet(v1,v2));

        return r;
    }
}
