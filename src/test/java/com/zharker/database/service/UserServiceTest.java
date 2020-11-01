package com.zharker.database.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.zharker.database.data.customized.Array;
import com.zharker.database.data.customized.Jsonb;
import com.zharker.database.domain.*;
import com.zharker.database.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Config.class)
public class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    EntityManager entityManager;

    @Before
    public void setUp() {
    }

    @Test
    @Sql(scripts = {"/sql/user.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/sql/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
    public void findById() {
        String testId = "test_user_id4";
        User user = userService.findById(testId);
        assertNotNull(user);
    }

    @Test
    @Sql(scripts = {"/sql/user.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/sql/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
    public void queryJson() {

        QUser qUser = QUser.user;

        BooleanExpression booleanExpression = Jsonb.of(qUser.customizedAttributes).eq("a", 2333);
        List<User> users = userService.dynamicSearch(booleanExpression);
        assertFalse(users.isEmpty());
        assertTrue(users.size() == 1);

        booleanExpression = Array.of(qUser.userTypes).contants(new String[]{"Driver", "ServiceUser"});
        users = userService.dynamicSearch(booleanExpression);
        assertFalse(users.isEmpty());
        assertTrue(users.size() == 2);
        assertTrue(users.stream().allMatch(user ->
                user.getId().equalsIgnoreCase("test_user_types_1")
                        || user.getId().equalsIgnoreCase("test_user_types_2")));

        booleanExpression = Array.of(qUser.numArr).contants(new Integer[]{4, 7});
        users = userService.dynamicSearch(booleanExpression);
        assertFalse(users.isEmpty());
        assertTrue(users.size() == 3);
        assertTrue(users.stream().allMatch(user ->
                user.getId().equalsIgnoreCase("test_user_types_1")
                        || user.getId().equalsIgnoreCase("test_user_types_2")
                        || user.getId().equalsIgnoreCase("test_user_types_3")
        ));
    }

    @Test
    @Sql(scripts = {"/sql/user.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/sql/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
    public void save(){
        User user = new User();
        user.setGender(Gender.male);
        user.setAge(33);
//        user.setJobs(new Job[]{Job.Doctor,Job.Driver,Job.Manager});

        String id = userService.save(user);

        user = userService.findById(id);
        assertNotNull(user);
        assertEquals(user.getGender(),Gender.male);
        assertEquals(user.getAgeRange(), AgeRange.middle);
    }

    @Test
    @Sql(scripts = {"/sql/user.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/sql/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
    public void enumQuery(){
        QUser qUser = QUser.user;

        BooleanExpression booleanExpression = qUser.ageRange.eq(AgeRange.child);
        List<User> users = userService.dynamicSearch(booleanExpression);
        assertFalse(users.isEmpty());
        assertTrue(users.size() == 1);
        assertEquals(users.stream().findFirst().orElse(null).getGender(),Gender.male);
    }

    @Test
    public void saveDateTimeTest(){

        User user = new User();
        user.setGender(Gender.male);
        user.setAge(33);
        Date date = Date.from(Instant.now());
        user.setDateOfBirth(date);
        user.setDateOfDeath(date);
        log.info(date.toString());
        userService.save(user);

//        List<User> users = userService.dynamicSearch(QUser.user.dateOfBirth.eq(date));
        List<User> users = userService.findByDateOfBirthLessThanEqual(date);
        users.stream().map(User::getId).forEach(System.out::println);

    }

    @Test
    public void utilDate2StringTest(){

        Date date = Date.from(Instant.now());
        String dateStr = Utils.date2String(date);
        log.info(dateStr);

        Date date1 = Utils.string2Date(dateStr);
        Assert.assertEquals(date1,date);

    }
}