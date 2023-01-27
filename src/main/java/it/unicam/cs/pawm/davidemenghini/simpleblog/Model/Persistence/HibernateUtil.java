package it.unicam.cs.pawm.davidemenghini.simpleblog.Model.Persistence;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

    /**
     * Created by Ариорх on 27.05.2017.
     */
public class HibernateUtil {
private static SessionFactory sessionFactory = null;

static {
            Configuration cfg = new Configuration().configure();
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
            sessionFactory = cfg.buildSessionFactory(builder.build());
    }

public static SessionFactory getSessionFactory() {
    return sessionFactory;
    }
}
