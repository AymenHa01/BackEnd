package com.example.ecole.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data


public class Seance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private  int  jour;
    private String startTime;
    private String endTime;
    private String classe;
    private String cmnts;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private Groupe  groupe;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private  Professeur professeur;


    @ManyToOne

    private Cours cours;


    @OneToMany(mappedBy = "seance")
    @JsonIgnore
    private List<Absent> absents;



}
