package com.saprykin.ats.dao;

import com.saprykin.ats.model.Applicant;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("applicantDao")
public class ApplicantDaoImpl extends AbstractDao implements ApplicantDao {

    @Override
    public void saveApplicant(Applicant applicant) {
        persist(applicant);
    }

    @Override
    public List<Applicant> findAllApplicants() {
        Criteria criteria = getSession().createCriteria(Applicant.class);
        return (List<Applicant>) criteria.list();
    }

    @Override
    public void deleteApplicantById(int id) {
        Query query = getSession().createSQLQuery("delete from APPLICANTS where id = :id");
        query.setInteger("id", id);
        query.executeUpdate();
    }

    @Override
    public Applicant findApplicantById(int id) {
        Criteria criteria = getSession().createCriteria(Applicant.class);
        criteria.add(Restrictions.eq("id", id));
        return (Applicant) criteria.uniqueResult();
    }

    @Override
    public void updateApplicant(Applicant applicant) {
        getSession().update(applicant);
    }
}
