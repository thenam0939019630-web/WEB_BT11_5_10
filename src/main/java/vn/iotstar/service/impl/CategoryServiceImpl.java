package vn.iotstar.service.impl;


import vn.iotstar.dao.CategoryDao;
import vn.iotstar.dao.impl.CategoryDAOImpl;

import vn.iotstar.entity.Category;
import vn.iotstar.service.CategoryService;


import java.util.List;



public class CategoryServiceImpl 
implements CategoryService {



private CategoryDao categoryDAO;



public CategoryServiceImpl(){

    categoryDAO =
    new CategoryDAOImpl();

}




@Override
public List<Category> findAll(){

    return categoryDAO.findAll();

}

@Override
public List<Category> findPage(int firstResult, int maxResults) {
    return categoryDAO.findPage(firstResult, maxResults);
}

@Override
public long countAll() {
    return categoryDAO.countAll();
}




@Override
public Category findById(Integer id){

    return categoryDAO.findById(id);

}




@Override
public void insert(Category category){

    categoryDAO.insert(category);

}




@Override
public void update(Category category){

    categoryDAO.update(category);

}




@Override
public void delete(Category category){

    categoryDAO.delete(category);

}



}