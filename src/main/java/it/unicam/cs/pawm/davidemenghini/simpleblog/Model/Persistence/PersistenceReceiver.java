package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.jdbc.object.SqlQuery;

import java.util.Map;


/**
 *
 * Questa interfaccia funzionale definisce l'azione di prendere dei dati di esempio nel modello di persistenza.
 * @param <T>  Il tipo di oggetto che andrai a prendere dal modello di persistenza.
 */
@FunctionalInterface
public interface PersistenceReceiver<T>{


    T getData(Integer data);


    default  Post getMysqlPost(Integer data){
        Post p;
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        p = s.get(Post.class,data);
        tx.commit();
        return p;
    }

    default Comment getMysqlComment(Integer id){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        Comment c = s.get(Comment.class,id);
        tx.commit();
        return c;
    }

    default DefaultUser getMysqlUser(Integer id){
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        DefaultUser c = s.get(DefaultUser.class,id);
        tx.commit();
        return c;
    }

}
