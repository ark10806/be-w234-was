package db.manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import db.entity.User;
import exception.UserException;
import exception.UserExceptionMessage;

public class UserManager {
  private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("onboarding");
  private static EntityManager em;
  private static EntityTransaction tx;


  public void insert(User user) {
    try {
      createEntityManager();
      beginTransaction();
      tx.begin();
      em.persist(user);
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      throw new UserException(UserExceptionMessage.USER_ALREADY_EXISTS);
    } finally {
      closeEntityManager();
    }
  }

  public User findById(String userId) {
    createEntityManager();
    User user = em.find(User.class, userId);
    if (user == null) throw new UserException(UserExceptionMessage.USER_NOT_FOUND);
    closeEntityManager();
    return user;
  }

  private void createEntityManager() {
    em = emf.createEntityManager();
  }

  private void closeEntityManager() {
    em.close();
  }

  private void beginTransaction() {
    tx = em.getTransaction();
  }

}
