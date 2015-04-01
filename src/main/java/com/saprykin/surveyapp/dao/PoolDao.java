package com.saprykin.surveyapp.dao;

import com.saprykin.surveyapp.model.Pool;

import java.util.List;

public interface PoolDao {

    void savePool(Pool pool);

    List<Pool> findAllPools();

    void deletePoolById(int id);

    Pool findPoolById(int id);

    void updatePool(Pool pool);
}
