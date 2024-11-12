package com.example.ecole.Repository;

import com.example.ecole.Entities.Eleve;
import com.example.ecole.Entities.Professeur;
import com.example.ecole.Entities.Seance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfesseurRepository extends JpaRepository<Professeur ,Integer> {
    Professeur findByEmailAndMotdepasse(String email , String motdepasse);



}
