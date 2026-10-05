package vn.iotstar.dao.impl;


import vn.iotstar.config.JPAConfig;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;



public class BaseDAOImpl {


    protected EntityManager getEntityManager(){

        return JPAConfig
                .getEntityManager();

    }



    protected void close(EntityManager em){

        if(em!=null)
            em.close();

    }


}