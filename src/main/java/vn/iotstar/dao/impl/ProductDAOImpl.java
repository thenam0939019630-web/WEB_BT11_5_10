package vn.iotstar.dao.impl;


import jakarta.persistence.EntityManager;

import vn.iotstar.dao.ProductDao;
import vn.iotstar.entity.Product;


import java.util.List;



public class ProductDAOImpl
extends BaseDAOImpl
implements ProductDao {



@Override
public List<Product> findAll(){


    EntityManager em =
    getEntityManager();



    List<Product> list =
    em.createQuery(
        "SELECT p FROM Product p LEFT JOIN FETCH p.category LEFT JOIN FETCH p.seller ORDER BY p.productId",
        Product.class
    )
    .getResultList();



    close(em);


    return list;

}

@Override
public List<Product> findPage(int firstResult, int maxResults) {
    EntityManager em = getEntityManager();
    try {
        return em.createQuery(
            "SELECT p FROM Product p LEFT JOIN FETCH p.category LEFT JOIN FETCH p.seller ORDER BY p.productId",
            Product.class
        ).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    } finally {
        close(em);
    }
}

@Override
public long countAll() {
    EntityManager em = getEntityManager();
    try {
        return em.createQuery("SELECT COUNT(p) FROM Product p", Long.class).getSingleResult();
    } finally {
        close(em);
    }
}



@Override
public Product findById(Object id){


    EntityManager em =
    getEntityManager();



    try {
        return em.createQuery(
            "SELECT p FROM Product p LEFT JOIN FETCH p.category LEFT JOIN FETCH p.seller WHERE p.productId = :id",
            Product.class
        ).setParameter("id", id).getSingleResult();
    } catch (jakarta.persistence.NoResultException e) {
        return null;
    } finally {
        close(em);
    }

}





@Override
public List<Product> findBySellerId(Integer sellerId){


    EntityManager em =
    getEntityManager();



    List<Product> list =
    em.createQuery(
        "SELECT p FROM Product p LEFT JOIN FETCH p.category LEFT JOIN FETCH p.seller WHERE p.seller.sellerId = :id ORDER BY p.productId",
        Product.class
    )
    .setParameter(
        "id",
        sellerId
    )
    .getResultList();



    close(em);



    return list;

}





@Override
public void insert(Product product){


    EntityManager em =
    getEntityManager();



    var tr =
    em.getTransaction();



    try{
        tr.begin();
        em.persist(product);
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
public void update(Product product){


    EntityManager em =
    getEntityManager();


    var tr =
    em.getTransaction();


    try{


        tr.begin();

        em.merge(product);

        tr.commit();


    }
    catch(Exception e){

        tr.rollback();

    }
    finally{

        close(em);

    }

}





@Override
public void delete(Product product){


    EntityManager em =
    getEntityManager();


    var tr =
    em.getTransaction();


    try{


        tr.begin();


        em.remove(
            em.contains(product)
            ?
            product
            :
            em.merge(product)
        );


        tr.commit();


    }
    catch(Exception e){

        tr.rollback();

    }
    finally{

        close(em);

    }


}



}