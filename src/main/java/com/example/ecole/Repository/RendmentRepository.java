package com.example.ecole.Repository;

import com.example.ecole.Entities.Chapiter;
import com.example.ecole.Entities.Eleve;
import com.example.ecole.Entities.Rendement;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RendmentRepository extends JpaRepository<Rendement , Integer> {

    Page<Rendement> findRendementByEleve_Id(int id  , Pageable pageable);
    List<Rendement> findRendementByEleve_Id(int id  );
    List<Rendement> findRendementByEleve_IdAndVu(int eleveId, boolean vu   );
    boolean existsByChapiterAndEleve(Chapiter chapiter , Eleve eleve);




}
