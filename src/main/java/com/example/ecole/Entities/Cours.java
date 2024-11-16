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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cours {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom ;
    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Chapiter> chapiters;

    @OneToMany(mappedBy = "cours" ,  cascade = CascadeType.ALL, orphanRemoval = true )
    @JsonIgnore
    @ToString.Exclude
    private List<Seance> seances;

    @OneToOne
    @JsonIgnore
    @ToString.Exclude
    private Professeur professeur;



    public  Cours(int id ){}


}
