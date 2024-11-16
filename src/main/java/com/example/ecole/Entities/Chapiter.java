package com.example.ecole.Entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Chapiter {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom ;
    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private Cours cours;

    @OneToMany(mappedBy = "chapiter" , cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Rendement> rendements;
}
