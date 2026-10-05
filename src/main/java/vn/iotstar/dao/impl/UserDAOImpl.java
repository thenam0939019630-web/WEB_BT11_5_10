package vn.iotstar.dao.impl;


import vn.iotstar.dao.UserDao;
import vn.iotstar.entity.User;
import vn.iotstar.entity.UserRole;


import jakarta.persistence.EntityManager;


public class UserDAOImpl
extends BaseDAOImpl
implements UserDao {



@Override
public User findByUsername(String username){


EntityManager em=getEntityManager();


try{


return em.createQuery(
"SELECT u FROM User u LEFT JOIN FETCH u.role LEFT JOIN FETCH u.seller WHERE u.username=:username",
User.class)

.setParameter(
"username",
username)

.getSingleResult();



}
catch(Exception e){

return null;

}

finally{

close(em);

}


}




@Override
public User findByEmail(String email){


EntityManager em=getEntityManager();


try{


return em.createQuery(
"SELECT u FROM User u LEFT JOIN FETCH u.role LEFT JOIN FETCH u.seller WHERE u.email=:email",
User.class)

.setParameter(
"email",
email)

.getSingleResult();



}
catch(Exception e){

return null;

}

finally{

close(em);

}


}




@Override
public java.util.List<User> findAll(){
	EntityManager em = getEntityManager();
	try {
		return em.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.role", User.class).getResultList();
	} finally {
		close(em);
	}

}


@Override
public User findById(Object id){
	EntityManager em = getEntityManager();
	try {
		return em.find(User.class, id);
	} finally {
		close(em);
	}

}


@Override
public void insert(User user){


EntityManager em=getEntityManager();


var tr=em.getTransaction();


try{


tr.begin();
if (user.getRole() != null && user.getRole().getRoleId() != null) {
	user.setRole(em.getReference(UserRole.class, user.getRole().getRoleId()));
}

em.persist(user);

tr.commit();


}
catch(RuntimeException e){
if (tr.isActive()) tr.rollback();
throw e;
}
finally{

close(em);

}


}



@Override
public void update(User user){
	EntityManager em = getEntityManager();
	var transaction = em.getTransaction();
	try {
		transaction.begin();
		em.merge(user);
		transaction.commit();
	} catch (RuntimeException e) {
		if (transaction.isActive()) transaction.rollback();
		throw e;
	} finally {
		close(em);
	}
}



@Override
public void delete(User user){
	EntityManager em = getEntityManager();
	var transaction = em.getTransaction();
	try {
		transaction.begin();
		em.remove(em.contains(user) ? user : em.merge(user));
		transaction.commit();
	} catch (RuntimeException e) {
		if (transaction.isActive()) transaction.rollback();
		throw e;
	} finally {
		close(em);
	}
}



}