package com.saprykin.ats.model;

import java.time.LocalDate;
import javax.persistence.*;


@Entity
@Table(name = "APPLICANTS")
public class Applicant {

    private int id;
    private String firstName;
    private String lastName;
    private String middleName;

    private String fullAddress;
    private LocalDate dateOfBirth;

    public Applicant() {
    }


    @Id
    @Column(name = "id", unique = true)
    @SequenceGenerator(sequenceName = "APPLICANT_ID_SEQUENCE", name = "ApplicantIdSequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ApplicantIdSequence")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "middleName")
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Column(name = "fullAddress")
    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    @Column(name = "dateOfBirth")
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Applicant{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", fullAddress='" + fullAddress + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
