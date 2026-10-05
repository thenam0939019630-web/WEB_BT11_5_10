package vn.iotstar.service;


import vn.iotstar.entity.Category;

import java.util.List;


public interface CategoryService {


    List<Category> findAll();

    List<Category> findPage(int firstResult, int maxResults);

    long countAll();


    Category findById(Integer id);


    void insert(Category category);


    void update(Category category);


    void delete(Category category);


}