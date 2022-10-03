package db.manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import db.entity.User;

public class PostManager {
  EntityManagerFactory emf = Persistence.createEntityManagerFactory("onboarding");
  EntityManager em = emf.createEntityManager();
  EntityTransaction tx = em.getTransaction();

  public void insert(User user) {
    try {
      tx.begin();
      em.persist(user);
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      e.printStackTrace();
    }
  }

  public void findAll() {

  }

  public void findById() {

  }
}
