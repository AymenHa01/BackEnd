package com.example.ecole.Service;

import com.example.ecole.Entities.*;
import com.example.ecole.Entities.ClassSpecifique.GroupeAndCours;
import com.example.ecole.Repository.ChapiterRepository;
import com.example.ecole.Repository.CoursRepository;
import com.example.ecole.Repository.GroupeRepository;
import com.example.ecole.Repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashBoardService {
    @Autowired
    CoursRepository Cr;
    @Autowired
    GroupeRepository GR;

    @Autowired
    SessionRepository SR;

    @Autowired
    ChapiterRepository CR;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public GroupeAndCours GetAllGroupesAndAllcours(int id ){
    Cours Cours = Cr.findCoursByProfesseurId(id);
        System.out.println(Cours);
    List<Groupe> Groupes = GR.findAll();
    return new GroupeAndCours(Groupes, Cours);
    }



    public  void AddSeance(Seance seance){
        SR.save(seance);

        messagingTemplate.convertAndSend("/Seance/seance" , "{}" );
        
    }
    
    public void DeleteSeance(int id ){
        SR.deleteById(id);
        messagingTemplate.convertAndSend("/Seance/seance" , "{}" );
    }

    public int addcours(Cours cours){
        Cours cour =   Cr.save(cours);
        return cour.getId();
    }

    public void  DeleteCours(int id ){
        Cr.deleteById(id);
    }

    public void addchapiter(Chapiter chapiter){
        CR.save(chapiter);
    }
    public void Deletechapiter(int id ){
        CR.deleteById(id);
    }










}
