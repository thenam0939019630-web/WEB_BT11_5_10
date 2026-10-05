package vn.iotstar.service.impl;


import vn.iotstar.dao.ProductDao;
import vn.iotstar.dao.impl.ProductDAOImpl;

import vn.iotstar.entity.Product;

import vn.iotstar.service.ProductService;


import java.util.List;



public class ProductServiceImpl
implements ProductService {



private ProductDao productDAO;



public ProductServiceImpl(){

    productDAO =
    new ProductDAOImpl();

}



@Override
public List<Product> findAll(){

    return productDAO.findAll();

}

@Override
public List<Product> findPage(int firstResult, int maxResults) {
    return productDAO.findPage(firstResult, maxResults);
}

@Override
public long countAll() {
    return productDAO.countAll();
}



@Override
public Product findById(Integer id){

    return productDAO.findById(id);

}



@Override
public void insert(Product product){

    productDAO.insert(product);

}



@Override
public void update(Product product){

    productDAO.update(product);

}



@Override
public void delete(Product product){

    productDAO.delete(product);

}

@Override
public List<Product> findBySellerId(Integer sellerId){

    return productDAO.findBySellerId(sellerId);

}


}