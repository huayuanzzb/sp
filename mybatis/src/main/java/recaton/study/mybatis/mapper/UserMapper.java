package recaton.study.mybatis.mapper;

import recaton.study.mybatis.entity.User;

import java.util.List;

public interface UserMapper {

    User findById(Integer id);

    int insertUsers(List<User> users);

    int deleteAll();

    User getUserByName(String name);
}
