package com.example.ecole.Repository;

import com.example.ecole.Entities.Cours;
import com.example.ecole.Entities.Professeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CoursRepository  extends JpaRepository<Cours, Integer>{
    @Query("SELECT DISTINCT c FROM Cours c JOIN c.seances s WHERE s.groupe.id = :groupeId")
    List<Cours> findCoursByGroupeId(@Param("groupeId") int groupeId);



    @Query("SELECT c FROM Cours c WHERE c.professeur.id = :profId")
    Cours findCoursByProfesseurId(@Param("profId") int profId);

}
