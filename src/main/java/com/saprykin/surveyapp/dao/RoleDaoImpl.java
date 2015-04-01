package com.saprykin.surveyapp.dao;

import com.saprykin.surveyapp.model.Role;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("roleDao")
public class RoleDaoImpl extends AbstractDao implements RoleDao {

    @Override
    public void saveRole(Role role) {
        persist(role);
    }

    @Override
    public List<Role> findAllRoles() {
        Criteria criteria = getSession().createCriteria(Role.class);
        return (List<Role>) criteria.list();
    }

    @Override
    public void deleteRoleById(int id) {
        Query query = getSession().createSQLQuery("delete from ROLES where id = :id");
        query.setInteger("id", id);
        query.executeUpdate();
    }

    @Override
    public Role findRoleById(int id) {
        Criteria criteria = getSession().createCriteria(Role.class);
        criteria.add(Restrictions.eq("id", id));
        return (Role) criteria.uniqueResult();
    }

    @Override
    public void updateRole(Role role) {
        getSession().update(role);
    }
}
