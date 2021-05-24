package repository;

import domain.Account;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class AccountDbRepository implements AccountRepository {
    @Override
    public Account findOne(String s) {
        Account acc = null;
        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query query = session.createQuery("from Account AS A where A.id=:aId",Account.class);
                query.setParameter("aId",s);
                List<Account> accs = query.list();
                acc = accs.get(0);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return acc;
    }

    @Override
    public Iterable<Account> getAll() {
        return null;
    }

    @Override
    public void add(Account entity) {

    }

    @Override
    public void delete(String s) {

    }

    @Override
    public void update(Account entity) {

    }
}
