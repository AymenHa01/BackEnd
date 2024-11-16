package com.example.ecole.Repository;

import com.example.ecole.Entities.Messages.Sondage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SondageRepository extends JpaRepository<Sondage, Integer> {
}
