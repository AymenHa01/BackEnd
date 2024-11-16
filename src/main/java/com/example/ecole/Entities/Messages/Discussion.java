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

public class Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String codeDiscussion ;
    private String type;
    @Column(nullable = false, columnDefinition = "bit default 0")
    private boolean Discussionvu ;
    @OneToMany(mappedBy = "discussion")
    @JsonIgnore
    private List<Messages> messages;


    @Override
    public String toString() {
        return "Discussion{" +
                "id=" + id +
                ", codeDiscussion='" + codeDiscussion + '\'' +
                ", type='" + type + '\'' +
                '}';
    }


}
