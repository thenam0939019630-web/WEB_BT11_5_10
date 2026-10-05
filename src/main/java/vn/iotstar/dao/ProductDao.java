package vn.iotstar.dao;
import vn.iotstar.entity.Product;
import java.util.List;
public interface ProductDao extends GenericDao<Product> {
    List<Product> findBySellerId(Integer sellerId);
    List<Product> findPage(int firstResult, int maxResults);
    long countAll();
}
