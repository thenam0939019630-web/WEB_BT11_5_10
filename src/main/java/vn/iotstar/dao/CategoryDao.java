package vn.iotstar.dao;
import vn.iotstar.entity.Category;
import java.util.List;

public interface CategoryDao extends GenericDao<Category> {
	List<Category> findPage(int firstResult, int maxResults);
	long countAll();
}
