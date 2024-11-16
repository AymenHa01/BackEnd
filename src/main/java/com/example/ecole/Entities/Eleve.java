package com.example.ecole.Entities;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Collection;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Eleve  {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String emailParent;
    private String email ;

    private String password ;
    private String nom ;
    private String prenom;
    private String number ;
    private String gendre  ;
    @ManyToOne
    @JsonIgnore
    private Groupe groupe;
    @OneToMany(mappedBy = "eleve")
    @JsonIgnore
    private List<Rendement> rendements ;
    @OneToMany(mappedBy = "eleve")
    @JsonIgnore
    private List<Absent> absents;





}
