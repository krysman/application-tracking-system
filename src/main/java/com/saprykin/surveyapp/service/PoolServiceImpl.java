package com.saprykin.surveyapp.service;

import com.saprykin.surveyapp.dao.PoolDao;
import com.saprykin.surveyapp.model.Pool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("poolService")
@Transactional
public class PoolServiceImpl implements PoolService {

    @Autowired
    private PoolDao dao;

    @Override
    public void savePool(Pool pool) {
        dao.savePool(pool);
    }

    @Override
    public List<Pool> findAllPools() {
        return dao.findAllPools();
    }

    @Override
    public void deletePoolById(int id) {
        dao.deletePoolById(id);
    }

    @Override
    public Pool findPoolById(int id) {
        return dao.findPoolById(id);
    }

    @Override
    public void updatePool(Pool pool) {
        dao.updatePool(pool);
    }
}
