package vn.iotstar.dao;
import vn.iotstar.entity.User;
public interface UserDao extends GenericDao<User> {
    User findByUsername(String username);
    User findByEmail(String email);
}
