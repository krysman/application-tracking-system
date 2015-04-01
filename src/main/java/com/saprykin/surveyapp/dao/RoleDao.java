package com.saprykin.surveyapp.dao;


import com.saprykin.surveyapp.model.Role;

import java.util.List;

public interface RoleDao {

    void saveRole(Role role);

    List<Role> findAllRoles();

    void deleteRoleById(int id);

    Role findRoleById(int id);

    void updateRole(Role role);
}
