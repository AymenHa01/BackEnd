package com.example.ecole.Entities.Messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.message.Message;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Sondage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String sujet;
    private String choices;




    @OneToMany(mappedBy = "sondage")
    private List<Participer> participers;

    @Override
    public String toString() {
        return "Sondage{" +
                "id=" + id +
                ", sujet='" + sujet + '\'' +
                '}';
    }



}
