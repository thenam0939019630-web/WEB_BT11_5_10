package vn.iotstar.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import vn.iotstar.dao.CategoryDao;
import vn.iotstar.entity.Category;

import java.util.List;

public class CategoryDAOImpl extends BaseDAOImpl implements CategoryDao {
    @Override
    public List<Category> findAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Category c ORDER BY c.categoryId", Category.class)
                    .getResultList();
        } finally {
            close(em);
        }
    }

    @Override
    public List<Category> findPage(int firstResult, int maxResults) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Category c ORDER BY c.categoryId", Category.class)
                    .setFirstResult(firstResult)
                    .setMaxResults(maxResults)
                    .getResultList();
        } finally {
            close(em);
        }
    }

    @Override
    public long countAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(c) FROM Category c", Long.class).getSingleResult();
        } finally {
            close(em);
        }
    }

    @Override
    public Category findById(Object id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Category.class, id);
        } finally {
            close(em);
        }
    }

    @Override
    public void insert(Category category) {
        execute(category, Operation.INSERT);
    }

    @Override
    public void update(Category category) {
        execute(category, Operation.UPDATE);
    }

    @Override
    public void delete(Category category) {
        execute(category, Operation.DELETE);
    }

    private void execute(Category category, Operation operation) {
        EntityManager em = getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            switch (operation) {
                case INSERT -> em.persist(category);
                case UPDATE -> em.merge(category);
                case DELETE -> em.remove(em.contains(category) ? category : em.merge(category));
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        } finally {
            close(em);
        }
    }

    private enum Operation { INSERT, UPDATE, DELETE }
}