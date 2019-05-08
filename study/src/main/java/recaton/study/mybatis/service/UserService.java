package recaton.study.mybatis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import recaton.study.mybatis.entity.User;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private SqlSession session;

    public UserService(SqlSession session) {
        this.session = session;
    }

    public User getUser(Integer id){
        // session.selectOne("bingoal.study.mybatis.entity.UserMapper.selectUserEntity", id) 有相同的作用
        return session.selectOne("bingoal.study.mybatis.entity.UserMapper.selectUserMap", id);
    }

    public int saveUsers(List<User> users){
        return session.insert("insertUsers", users);
    }

    public int saveUser(Map map){
        logger.info("save a user: {}", map);
        return session.insert("bingoal.study.mybatis.entity.UserMapper.insertUserMap", map);
    }


    public List<User> getUsersByName(Map name) {
        return session.selectList("bingoal.study.mybatis.entity.UserMapper.getUsersByName", name);
    }

    public List<User> getUserByLike(String name){
        return null;
    }

    public int deleteUsers() {
        return session.delete("deleteUsers");
    }
}
