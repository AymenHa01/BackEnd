package com.example.ecole.Service;

import com.example.ecole.Entities.*;
import com.example.ecole.Repository.*;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceProf {
@Autowired
RendmentRepository RE;
@Autowired
AbsentRepository AB;
@Autowired
ProfesseurRepository pf;
@Autowired
GroupeRepository GR;
@Autowired
SessionRepository SE;
@Autowired
EleveRepository e;
@Autowired
private SimpMessagingTemplate messagingTemplate;




public Professeur login(String email , String password){
    return pf.findByEmailAndMotdepasse( email , password );
}


public List<Seance> GetSeance(int id ){
    return  SE.findSeanceByProfesseur_Id(id);
}
public Professeur getProfesseur(int id ) {
    return pf.findById(id).get();
}
public List<Professeur> getAllprof(){
    return  pf.findAll();
}

public void AbsentEleve(Absent absent){
    System.out.println("1    "+absent);
    try {

     Optional<Integer> id= AB.findIdByDateAndEleveAndSeance(absent.getDate(),absent.getEleve(),absent.getSeance());

    if(!id.isPresent()){
    AB.save(absent);
    }else {
        absent.setId(id.get());
 AB.delete(absent);
    }
    
    messagingTemplate.convertAndSend("/topic/absent" , absent);
    }catch (Exception e ){
        e.getCause();
    }

}
public void DeleteAbsent(Absent absent){
    AB.delete(absent);
   messagingTemplate.convertAndSend("/topic/absent" , absent);
}

public Page<Rendement> EleveRendement(  int id, int page, int size ){
    Pageable pageable = PageRequest.of(page, size);
    return RE.findRendementByEleve_Id(id, pageable);
}
public List<Rendement> EleveRendemente(  int id, int page, int size ){

    return RE.findRendementByEleve_Id(id);
}
public void Updatecommentaire(int id , String cmnts  ){
   SE.updateCmntsById(id,cmnts);
   Seance s =new Seance();
   messagingTemplate.convertAndSend("/Seance/seance", "{}" );

}

public List<Groupe> Groupeparseance(Seance seance){
    return  GR.findBySeances(seance);
}


public List<Eleve> listeDesAbsent(Seance seance , Date date ){
    return  AB.findEleve( date, seance );
}

public  List<Groupe> ListeDesGroupe(Professeur   professeur){
    return SE.ListeDesGroupe(professeur);
}

public Rendement changeRendement(Rendement rendement){
    messagingTemplate.convertAndSend("/Level/rendement", rendement);
    return RE.save(rendement);
}





}
