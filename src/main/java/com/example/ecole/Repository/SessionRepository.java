package com.example.ecole.Repository;

import com.example.ecole.Entities.Absent;
import com.example.ecole.Entities.Groupe;
import com.example.ecole.Entities.Professeur;
import com.example.ecole.Entities.Seance;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface SessionRepository extends JpaRepository<Seance, Integer> {
    @Query("SELECT s.groupe FROM Seance s WHERE s.professeur= :professeur")
    List<Groupe> ListeDesGroupe(@Param("professeur") Professeur professeur);

    @Query("SELECT s FROM Seance s WHERE s.groupe.id = :groupeId")
    List<Seance> findSeancesByGroupeId(@Param("groupeId") int groupeId);

    List<Seance> findSeanceByProfesseur_Id(int professeurId);

    @Modifying
    @Transactional
    @Query("UPDATE Seance s SET s.cmnts = :cmnts WHERE s.id = :id")
    void  updateCmntsById(int id, String cmnts);

}
