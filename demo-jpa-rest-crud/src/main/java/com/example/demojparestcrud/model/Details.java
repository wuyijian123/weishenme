package com.example.demojparestcrud.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tutorial_details")
public class Details  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long dId;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "tutorial_id")
    private  Tutorial tutorialId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTimeOn;
    private  String createdBy;
    public Details() {

    }

    public Details(long dId, Tutorial tutorialId, Date createdTimeOn, String createdBy) {
        this.dId = dId;
        this.tutorialId = tutorialId;
        this.createdTimeOn = createdTimeOn;
        this.createdBy = createdBy;
    }

    public long getdId() {
        return dId;
    }

    public void setdId(long dId) {
        this.dId = dId;
    }

    public Tutorial getTutorialId() {
        return tutorialId;
    }

    public void setTutorialId(Tutorial tutorialId) {
        this.tutorialId = tutorialId;
    }

    public Date getCreatedTimeOn() {
        return createdTimeOn;
    }

    public void setCreatedTimeOn(Date createdTimeOn) {
        this.createdTimeOn = createdTimeOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    //    @Column(columnDefinition = "varchar")
}
