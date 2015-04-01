package com.saprykin.ats.service;

import com.saprykin.ats.model.Role;

import java.util.List;

public interface RoleService {

    void saveRole(Role role);

    List<Role> findAllRoles();

    void deleteRoleById(int id);

    Role findRoleById(int id);

    void updateRole(Role role);
}
