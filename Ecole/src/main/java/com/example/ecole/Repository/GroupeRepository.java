package com.example.ecole.Repository;

import com.example.ecole.Entities.Groupe;
import com.example.ecole.Entities.Professeur;
import com.example.ecole.Entities.Seance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupeRepository extends JpaRepository<Groupe, Integer> {


    List<Groupe> findBySeances(Seance seance);


    
}
