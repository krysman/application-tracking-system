package com.saprykin.ats.service;

import com.saprykin.ats.model.Applicant;

import java.util.List;

public interface ApplicantService {

    void saveApplicant(Applicant applicant);

    List<Applicant> findAllApplicants();

    void deleteApplicantById(int id);

    Applicant findApplicantById(int id);

    void updateApplicant(Applicant applicant);
}
