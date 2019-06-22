package recaton.study.mybatis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import recaton.study.mybatis.entity.User;
import recaton.study.mybatis.mapper.UserMapper;

import java.util.List;
import java.util.Map;

public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User getUser(Integer id){
        return userMapper.findById(id);
    }

    public int saveUsers(List<User> users){
        return userMapper.insertUsers(users);
    }

    public int saveUser(Map map){
        return 0;
    }


    public User getUsersByName(String name) {
        return userMapper.getUserByName(name);
    }

    public List<User> getUserByLike(String name){
        return null;
    }

    public int deleteUsers() {
        return userMapper.deleteAll();
    }
}
