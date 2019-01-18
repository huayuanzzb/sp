package bingoal.study.mybatis.service;

import bingoal.study.mybatis.entity.User;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class UserService {

    private SqlSession session;

    public UserService(SqlSession session) {
        this.session = session;
    }

    public User getUser(Integer id){
        // session.selectOne("bingoal.study.mybatis.entity.UserMapper.selectUserEntity", id) 有相同的作用
        return session.selectOne("bingoal.study.mybatis.entity.UserMapper.selectUserMap", id);
    }

    public int saveUser(User user){
        return session.insert("bingoal.study.mybatis.entity.UserMapper.insertUser", user);
    }

    public List<User> getUserByLike(String name){
        return null;
    }
}
