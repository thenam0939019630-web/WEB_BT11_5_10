package vn.iotstar.service.impl;


import vn.iotstar.dao.UserDao;
import vn.iotstar.dao.impl.UserDAOImpl;


import vn.iotstar.entity.User;
import vn.iotstar.service.UserService;



public class UserServiceImpl
implements UserService {



private UserDao userDAO;



public UserServiceImpl(){

    userDAO =
    new UserDAOImpl();

}




@Override
public User login(
String username,
String password
){


    User user =
    userDAO.findByUsername(username);


    if(user!=null 
       && user.getPassword()
       .equals(password)){


        return user;

    }


    return null;

}





@Override
public User findByUsername(
String username
){

    return userDAO.findByUsername(username);

}




@Override
public User findByEmail(
String email
){

    return userDAO.findByEmail(email);

}





@Override
public void register(User user){

    userDAO.insert(user);

}



}