package com.example.ecole.Entities;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Absent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date date ;
    private  boolean vu ;
    @ManyToOne
    private Eleve eleve;
    @ManyToOne
    private Seance seance;

}
