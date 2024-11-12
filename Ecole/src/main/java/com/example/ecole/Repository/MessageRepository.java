package com.example.ecole.Repository;

import com.example.ecole.Entities.Messages.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository  extends JpaRepository<Messages, Integer> {
    List<Messages> findAllByDiscussion_Id(int id ) ;
}
