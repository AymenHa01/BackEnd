package com.example.ecole.Entities.ClassSpecifique;

import com.example.ecole.Entities.Cours;
import com.example.ecole.Entities.Groupe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupeAndCours {
    List<Groupe> groupeList;
    Cours coursList;
}
