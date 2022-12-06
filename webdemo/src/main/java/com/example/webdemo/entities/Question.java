package com.example.webdemo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Question extends BaseEntity {
    private int type;
    private String content;
    private String analysis;
    private int ordinal;
    @OneToMany(mappedBy = "question", cascade = CascadeType.PERSIST)
    private List<Option> options;
    @ManyToOne
    private Chapter chapter;
    @OneToMany(mappedBy = "question")
    private List<Result> results;
    @Transient
    private int multipleChoice = 0;
    @Transient
    private int judgment = 1;
    @Transient
    private int multiSelectMultipleChoice = 2;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    @JsonManagedReference
    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @JsonManagedReference
    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

}
