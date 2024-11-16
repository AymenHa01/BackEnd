package com.example.ecole.Entities.ClassSpecifique;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EleveSpecifique {
    private int id;
    private String nom;
    private String gendre ;
    private String prenom;
    private String adresse;
    private int Groupeid;

}
