package db.manager;

import db.entity.Post;
import exception.PostException;
import exception.PostExceptionMessage;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class PostManager {
  private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("onboarding");
  private static EntityManager em;
  private static EntityTransaction tx;

  public void insert(Post post) {
    try {
      createEntityManager();
      createTransaction();
      tx.begin();
      em.persist(post);
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      e.printStackTrace();
    }
  }

  public Post findById(String postId) {
    createEntityManager();
    Post post = em.find(Post.class, postId);
    if (post == null) {
      throw new PostException(PostExceptionMessage.POST_NOT_FOUND);
    }
    closeEntityManager();
    return post;
  }

  public List<Post> findAll() {
    createEntityManager();
    List<Post> posts = em.createQuery("select p from Post p", Post.class).getResultList();
    closeEntityManager();
    return posts;
  }

  private void createEntityManager() {
    em = emf.createEntityManager();
  }

  private void closeEntityManager() {
    em.close();
  }

  private void createTransaction() {
    tx = em.getTransaction();
  }

}
