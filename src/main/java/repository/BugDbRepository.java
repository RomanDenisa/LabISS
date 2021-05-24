package repository;

import domain.Bug;
import domain.BugStatus;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class BugDbRepository implements BugRepository{

    @Override
    public Bug findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Bug> getAll() {
        List<Bug> bugs = new ArrayList<>();
        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query query = session.createQuery("from Bug AS B", Bug.class);
                bugs = query.list();
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return bugs;
    }

    @Override
    public void add(Bug entity) {
        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(entity);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Bug entity) {
        Bug bug = entity;
        try(Session session = HibernateUtils.getSessionFactory().openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                bug = session.get(Bug.class,entity.getId());
                if(bug != null ) {
                    if(entity.getAdditionalInfo() != null){
                        bug.setAdditionalInfo(entity.getAdditionalInfo());
                    }
                    if(entity.getProgrammer()!=null){
                        bug.setProgrammer(entity.getProgrammer());
                    }
                    bug.setBugStatus(entity.getBugStatus());
                }
                tx.commit();
            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }

    @Override
    public Iterable<Bug> getBugsFoundByProgrammer(Integer id) {
        List<Bug> bugs = new ArrayList<>();
        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query query = session.createQuery("from Bug AS B where B.programmer.id=:pId and (B.bugStatus=:status OR B.bugStatus=:status2) ", Bug.class);
                query.setParameter("pId",id);
                query.setParameter("status",BugStatus.Assigned);
                query.setParameter("status2",BugStatus.Reopened);
                bugs = query.list();
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return bugs;
    }

    @Override
    public Iterable<Bug> getBugsFoundByTester(Integer id) {
        List<Bug> bugs = new ArrayList<>();
        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query query = session.createQuery("from Bug AS B where B.tester.id=:tId ", Bug.class);
                query.setParameter("tId",id);
                bugs = query.list();
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return bugs;
    }

    @Override
    public Iterable<Bug> getBugsWithStatus(BugStatus status){
        List<Bug> bugs = new ArrayList<>();
        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query query = session.createQuery("from Bug AS B where B.bugStatus =: statusBug", Bug.class);
                query.setParameter("statusBug",status);
                bugs = query.list();
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return bugs;
    }

    @Override
    public Iterable<Bug> getBugsFixedFoundByTester(Integer id){
        List<Bug> bugs = new ArrayList<>();
        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query query = session.createQuery("from Bug AS B where B.bugStatus =: statusBug", Bug.class);
                query.setParameter("statusBug",BugStatus.Fixed);
                bugs = query.list();
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return bugs;
    }
}
