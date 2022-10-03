package db.manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import db.entity.User;
import exception.UserException;
import exception.UserExceptionMessage;

public class UserManager {
  EntityManagerFactory emf = Persistence.createEntityManagerFactory("onboarding");
  EntityManager em = emf.createEntityManager();
  EntityTransaction tx = em.getTransaction();
  private static UserManager instance = new UserManager();

  public static UserManager getInstance() {
    return instance;
  }

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

  public User findById(String userId) {
    User user = em.find(User.class, userId);
    if (user == null)
      throw new UserException(UserExceptionMessage.USER_NOT_FOUND);
    return user;
  }

}
