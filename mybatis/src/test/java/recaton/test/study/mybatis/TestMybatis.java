package recaton.test.study.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import recaton.study.mybatis.entity.Company;
import recaton.study.mybatis.entity.User;
import recaton.study.mybatis.mappers.CompanyMapper;
import recaton.study.mybatis.mappers.UserMapper;
import recaton.study.mybatis.service.CompanyService;
import recaton.study.mybatis.service.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@FixMethodOrder(MethodSorters.JVM)
public class TestMybatis {

    private Logger logger = LoggerFactory.getLogger(TestMybatis.class);

    private static SqlSessionFactory sessionFactory = null;
    private static SqlSession session = null;
    private static UserService userService;
    private static CompanyService companyService;

    @BeforeClass
    public static void setup() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream in = Resources.getResourceAsStream(resource);
        sessionFactory = new SqlSessionFactoryBuilder().build(in);
        session = sessionFactory.openSession();
        userService = new UserService(session.getMapper(UserMapper.class));
        companyService = new CompanyService(session.getMapper(CompanyMapper.class));
    }

    // delete 表
    @Test
    public void testDelete() {
        Assert.assertTrue(userService.deleteUsers() >= 0);
        Assert.assertTrue(companyService.deleteCompanys() >= 0);
    }

    // insert 单个对象
    @Test
    public void testInsertCompany(){
        Company google = new Company(null, "google");
        companyService.saveCompany(google);
    }

    // select 对象列表
    @Test
    public void testSelectCompany() {
        Assert.assertTrue(companyService.getCompanys("google").size() > 0);
    }

    // batch insert
    @Test
    public void testInsertUsers(){
        Company google = companyService.getCompanys("google").get(0);
        logger.info("company is: {}", google);
        User jack = new User(google.getId(), "jack");
        User mark = new User(google.getId(), "mark");
        User lucy = new User(google.getId(), "lucy");
        List<User> users = Arrays.asList(jack, mark, lucy);
        Assert.assertEquals(users.size(), userService.saveUsers(users));
    }

    // select 复杂对象
    @Test
    public void testSelectComplexObject(){
        logger.info("company with users is {}", companyService.getCompanys("google"));
    }

    @Test
    public void testGetUser(){
        logger.info("开始第一次查询");
        Assert.assertNotNull(userService.getUsersByName("jack"));
        logger.info("开始第二次查询, 本次查询应该从一级缓存中获取查询结果, 不会访问数据库");
        Assert.assertNotNull(userService.getUsersByName("jack"));
        logger.info("第二次查询结束");
    }

    @AfterClass
    public static void tearDown(){
        try{
            session.commit();
            session.close();
        }catch (Exception e){
            // do nothing
        }
    }

}
