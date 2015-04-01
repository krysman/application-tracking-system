package com.saprykin.ats.service;

import com.saprykin.ats.model.Pool;

import java.util.List;

public interface PoolService {

    void savePool(Pool pool);

    List<Pool> findAllPools();

    void deletePoolById(int id);

    Pool findPoolById(int id);

    void updatePool(Pool pool);
}
