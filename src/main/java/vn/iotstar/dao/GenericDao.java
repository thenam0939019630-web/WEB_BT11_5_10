package vn.iotstar.dao;
import java.util.List;
public interface GenericDao<T> {
    List<T> findAll();
    T findById(Object id);
    void insert(T entity);
    void update(T entity);
    void delete(T entity);
}
