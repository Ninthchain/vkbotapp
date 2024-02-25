package com.dev.bot.database.dao;

import com.dev.bot.database.model.Person;
import com.dev.bot.database.model.VkGroup;
import com.dev.bot.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;



import java.util.List;


public class PersonDao implements Dao<Person> {
    HibernateCriteriaBuilder criteriaBuilder;


    @Override
    public void persist(Person entityObject) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtil.GetSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(entityObject);
            transaction.commit();
        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }

    @Override
    public void merge(Person entityObject) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.GetSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(entityObject);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public <IdType extends Number> Person getEntityById(IdType id) {

        Person person = null;
        try (Session session = HibernateUtil.GetSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            person = session.get(Person.class, id);
            transaction.commit();

        } catch (Exception e) {

            e.printStackTrace();
        }
        return person;
    }

    @Override
    public <ColumnValueType> List<Person> getEntitiesByColumnValue(String columnName, ColumnValueType value) {

        List<Person> values = null;

        try (Session session = HibernateUtil.GetSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            
            Query<Person> groupQuery = session.createQuery(String.format("from Person where %s = %s", columnName, value.toString()), Person.class);
            values = groupQuery.getResultList();
            transaction.commit();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return values;
    }

    @Override
    public List<Person> getAll() {
        List<Person> values = null;

        try (Session session = HibernateUtil.GetSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();
            
            Query<Person> allQuery = session.createQuery("from Person", Person.class);
            values = allQuery.getResultList();

            transaction.commit();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return values;
    }


}