package com.example.ecole.Repository;

import com.example.ecole.Entities.Absent;
import com.example.ecole.Entities.Eleve;
import com.example.ecole.Entities.Rendement;
import com.example.ecole.Entities.Seance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EleveRepository  extends JpaRepository<Eleve, Integer> {



    @Query("SELECT e.nom from Eleve e , Absent a WHERE a.date=:date AND a.seance= :seance  ")
    List<Eleve> findListdesabsent(@Param("date") Date date, @Param("seance") Seance seance );

    @Query("SELECT  a.eleve from Absent a WHERE a.eleve= :eleve")
    List<Absent> findAbsent(@Param("eleve") Eleve eleve );

    @Query("SELECT e.eleve AS eleve, SUM(e.stars)  AS totalStars FROM Rendement e  GROUP BY e.eleve ORDER BY SUM(e.stars) DESC")
    List<Object[]> findAllStudentsSortedByStars();


    Optional<Eleve> findByEmail(String email   );

    Eleve findByEmailAndPassword(String email , String password);
}
