package com.example.ecole.Repository;

import com.example.ecole.Entities.Absent;
import com.example.ecole.Entities.Eleve;
import com.example.ecole.Entities.Seance;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AbsentRepository extends JpaRepository<Absent,Integer> {
    List<Absent> findAbsentBySeance_Id(int id );
    @Query("SELECT a.id FROM Absent a  WHERE a.date = :date AND a.eleve = :eleve AND a.seance = :seance")
    Optional<Integer> findIdByDateAndEleveAndSeance(@Param("date") Date date, @Param("eleve") Eleve eleve, @Param("seance") Seance seance);

    @Query("SELECT a.eleve from Absent a  WHERE a.date=:date AND a.seance= :seance")
    List<Eleve> findEleve(@Param("date") Date date, @Param("seance") Seance seance );

    List<Absent> findAbsentsByEleve(Eleve eleve);

    @Modifying
    @Transactional
    @Query("UPDATE Absent a SET a.vu = true WHERE a.vu = false AND a.eleve = :eleve")
    void updateAbsent(Eleve eleve);

}
