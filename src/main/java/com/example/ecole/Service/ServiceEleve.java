package com.example.ecole.Service;

import com.example.ecole.EmailSender.EmailService;
import com.example.ecole.Entities.*;
import com.example.ecole.Entities.ClassSpecifique.EleveSpecifique;
import com.example.ecole.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceEleve {
    @Autowired
    RendmentRepository RE;
    @Autowired
    EleveRepository ER;
    @Autowired
    GroupeRepository GR;
    @Autowired
    AbsentRepository AB;
    @Autowired
    SessionRepository SR;
    @Autowired
    private EmailService emailService;
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private CoursRepository CR;


    public Eleve GetEleve(int id) {
        return ER.findById(id).get();
    }

    public List<Rendement> RendementByEleve(int id ){
        return RE.findRendementByEleve_Id(id);
    }

    public List<Absent> listAbsentByEleve(Eleve eleve ){
        return AB.findAbsentsByEleve(eleve);
    }

    public void Updatevu(Eleve eleve){
         AB.updateAbsent(eleve);
//        messagingTemplate.convertAndSend("/topic/absent" );

    }

    public List<Seance> Getseances(int  id ){
        return SR.findSeancesByGroupeId(id);
    }

    public void Updateupgrade(int id ){
        List<Rendement> rendement = RE.findRendementByEleve_IdAndVu(id,false);
        rendement.forEach(rendement1 -> {
            rendement1.setVu(true);
        RE.save(rendement1);
                }
                );
    }

    public List<Object[]> Test(){
        return ER.findAllStudentsSortedByStars();
    }

    public void registre(Eleve eleve , int  idgroupe ){
        String Password = com.example.ecole.Service.Password.generatePassword();
        eleve.setPassword(Password);
        Groupe groupe1 = GR.findById(idgroupe).orElseThrow(() -> new RuntimeException("Groupe not found"));
        eleve.setGroupe(groupe1);
        Eleve eleve1 = ER.save(eleve);
if (eleve1 != null) {
        emailService.sendSimpleEmail(
                eleve.getEmailParent(),
                "Vérification de votre compte",
                "Bonjour,\n\n" + eleve.getNom() +
                        "Votre compte a été créé avec succès.\n\n" +
                        "Détails de votre compte :\n" +
                        "votre email :\n " + eleve.getEmail() + "\n" +
                        "Votre mot de passe : " + Password + "\n\n" +

                        "Si vous rencontrez un problème, veuillez contacter l'administrateur.\n\n" +
                        "Cordialement,\nL'équipe d'administration"
        );
}

//       addRendement(eleve1 , groupe1.getId());
    }

    public void addRendement(Eleve eleve, int groupeId) {
        List<Cours> coursList = CR.findCoursByGroupeId(groupeId);
        for (Cours cours : coursList) {
            for (Chapiter chapiter : cours.getChapiters()) {
                Rendement rendement = new Rendement();
                rendement.setStars(0);
                rendement.setVu(false);
                rendement.setEleve(eleve);
                rendement.setChapiter(chapiter);
                 if (!RE.existsByChapiterAndEleve(chapiter , eleve) ) {
                     RE.save(rendement);
                 }
            }
        }}

    public EleveSpecifique login(String username , String password){
       Eleve eleve =  ER.findByEmailAndPassword(username, password);
       return  new EleveSpecifique(eleve.getId() , eleve.getNom() , eleve.getGendre(),  eleve.getPrenom() , eleve.getEmail() , eleve.getGroupe().getId());
    }

}
