package com.saprykin.ats.service;

import com.saprykin.ats.dao.ApplicantDao;
import com.saprykin.ats.model.Applicant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("applicantService")
@Transactional
public class ApplicantServiceImpl implements ApplicantService{

    @Autowired
    ApplicantDao dao;

    @Override
    public void saveApplicant(Applicant applicant) {
        dao.saveApplicant(applicant);
    }

    @Override
    public List<Applicant> findAllApplicants() {
        return dao.findAllApplicants();
    }

    @Override
    public void deleteApplicantById(int id) {
        dao.deleteApplicantById(id);
    }

    @Override
    public Applicant findApplicantById(int id) {
        return dao.findApplicantById(id);
    }

    @Override
    public void updateApplicant(Applicant applicant) {
        dao.updateApplicant(applicant);
    }
}
