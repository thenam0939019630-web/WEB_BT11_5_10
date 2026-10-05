package vn.iotstar.service;


import vn.iotstar.entity.Product;

import java.util.List;



public interface ProductService {


    List<Product> findAll();

    List<Product> findPage(int firstResult, int maxResults);

    long countAll();

    List<Product> findBySellerId(Integer sellerId);


    Product findById(Integer id);



    void insert(Product product);


    void update(Product product);


    void delete(Product product);


}