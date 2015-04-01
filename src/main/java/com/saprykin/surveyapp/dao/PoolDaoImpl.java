package com.saprykin.surveyapp.dao;

import com.saprykin.surveyapp.model.Pool;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("poolDao")
public class PoolDaoImpl extends AbstractDao implements PoolDao {

    @Override
    public void savePool(Pool pool) {
        persist(pool);
    }

    @Override
    public List<Pool> findAllPools() {
        Criteria criteria = getSession().createCriteria(Pool.class);
        return (List<Pool>) criteria.list();
    }

    @Override
    public void deletePoolById(int id) {
        Query query = getSession().createSQLQuery("delete from POOLS where id = :id");
        query.setInteger("id", id);
        query.executeUpdate();
    }

    @Override
    public Pool findPoolById(int id) {
        Criteria criteria = getSession().createCriteria(Pool.class);
        criteria.add(Restrictions.eq("id", id));
        return (Pool) criteria.uniqueResult();
    }

    @Override
    public void updatePool(Pool pool) {
        getSession().update(pool);
    }
}
