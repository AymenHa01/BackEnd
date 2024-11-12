package com.example.ecole.Entities.Messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Participer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String Image;
    private int choice;


    @ManyToOne
    @JsonIgnore
    private Sondage sondage ;
    @Override
    public String toString() {
        return "Participer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", image='" + Image + '\'' +
                '}';
    }

}
