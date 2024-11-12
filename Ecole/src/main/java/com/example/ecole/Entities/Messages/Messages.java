package com.example.ecole.Entities.Messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private  String userMessage;
    private String PathImage;

    private String message;

    @OneToOne
    private Sondage Sondage;




    private Date date ;

        @ManyToOne
        private Discussion discussion;

    public  Messages(String userMessage , Discussion discussion){}



    @Override
    public String toString() {
        return "Messages{" +
                "id=" + id +
                ", userMessage='" + userMessage + '\'' +
                ", PathImage='" + PathImage + '\'' +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }


}
