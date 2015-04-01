package com.saprykin.surveyapp.service;

import com.saprykin.surveyapp.dao.RoleDao;
import com.saprykin.surveyapp.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleDao dao;

    @Override
    public void saveRole(Role role) {
        dao.saveRole(role);
    }

    @Override
    public List<Role> findAllRoles() {
        return dao.findAllRoles();
    }

    @Override
    public void deleteRoleById(int id) {
        dao.deleteRoleById(id);
    }

    @Override
    public Role findRoleById(int id) {
        return dao.findRoleById(id);
    }

    @Override
    public void updateRole(Role role) {
        dao.updateRole(role);
    }
}
