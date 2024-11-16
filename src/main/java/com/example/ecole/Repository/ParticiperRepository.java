package com.example.ecole.Repository;

import com.example.ecole.Entities.Messages.Participer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticiperRepository extends JpaRepository<Participer, Integer> {
}
