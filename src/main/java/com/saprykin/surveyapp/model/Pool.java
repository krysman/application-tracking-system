package com.saprykin.surveyapp.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "POOLS")
public class Pool {

    private int id;
    private String title;
    private LocalDate creationDate;
    private LocalDate completionDate;

    public Pool() {
    }

    @Id
    @Column(name = "id", unique = true)
    @SequenceGenerator(sequenceName = "POOLS_ID_SEQUENCE", name = "PoolIdSequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PoolIdSequence")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "title", unique = false, nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "creationDate", nullable = false)
    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    @Column(name = "completionDate", nullable = false)
    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public void setDurationInDays(int duration) {
        setCompletionDate(getCreationDate().plusDays(duration));
    }
}