package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Statement statement;

        try {
            statement = Util.getConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            statement.executeUpdate("USE UsersDB");
            statement.executeUpdate("CREATE TABLE Users (id BIGINT AUTO_INCREMENT, name  VARCHAR(255),lastName  VARCHAR(255),age TINYINT UNSIGNED, PRIMARY KEY (id)  )");

            statement.executeUpdate("insert into hibernate_sequence(next_val) value (1)");
        } catch (SQLException e) {

        }
    }

    @Override
    public void dropUsersTable() {
        Statement statement;

        try {
            statement = Util.getConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            statement.executeUpdate("USE UsersDB");
            statement.executeUpdate("DROP table Users");
        } catch (SQLException e) {
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session;

        session = factory.getCurrentSession();
        session.beginTransaction();
        session.save(new User(name,lastName,age));
        session.getTransaction().commit();
       // session.close();
    }

    @Override
    public void removeUserById(long id) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session;

        session = factory.getCurrentSession();
        session.beginTransaction();
        User user = session.get(User.class, id );
        session.remove(user);
        session.getTransaction().commit();
       // session.close();
    }

    @Override
    public List<User> getAllUsers() {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session;
        List<User> user;
        session = factory.getCurrentSession();
        session.beginTransaction();
        user = session.createQuery("from User").getResultList();
        session.getTransaction().commit();
       // session.close();
        return user;
    }

    @Override
    public void cleanUsersTable() {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session;

        session = factory.getCurrentSession();
        session.beginTransaction();
        session.createQuery("delete from User").executeUpdate();
        session.getTransaction().commit();
        //session.close();
    }
}
