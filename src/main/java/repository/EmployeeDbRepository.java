package repository;

import domain.CompanyEmployee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class EmployeeDbRepository implements EmployeeRepository {
    @Override
    public CompanyEmployee findOne(Integer integer) {
        CompanyEmployee emp = null;
        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query query = session.createQuery("from CompanyEmployee AS C where C.id=:aId",CompanyEmployee.class);
                query.setParameter("aId",integer);
                List<CompanyEmployee> artists = query.list();
                emp = artists.get(0);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return emp;
    }

    @Override
    public Iterable<CompanyEmployee> getAll() {
        return null;
    }

    @Override
    public void add(CompanyEmployee entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(CompanyEmployee entity) {

    }
}
