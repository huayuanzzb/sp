package recaton.test.study.mybatis;

import recaton.study.mybatis.entity.User;
import recaton.study.mybatis.service.UserService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;


public class TestDB {

    private SqlSessionFactory sessionFactory = null;
    private SqlSession session = null;
    private UserService userService;

    @Before
    public void setup() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream in = Resources.getResourceAsStream(resource);
        sessionFactory = new SqlSessionFactoryBuilder().build(in);
        session = sessionFactory.openSession();
        userService = new UserService(session);
    }

    @Test
    public void testInsertUser(){
        User user = new User();
        user.setName("lv");
        user.setId(1);
        int r = userService.saveUser(user);
        Assert.assertTrue(r > 0);
    }

    @Test
    public void testGetUser(){
        int id = 1;
        User user = new User();
        user.setName("lv");
        user.setId(id);
        int r = userService.saveUser(user);
        Assert.assertEquals(r, id);
        User savedUser = userService.getUser(r);
        System.out.println(user);
        Assert.assertEquals(user.getName(), savedUser.getName());
    }


    @After
    public void tearDown(){
        try{
            session.close();
        }catch (Exception e){
            // do nothing
        }
    }

}
