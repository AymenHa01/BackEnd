package com.example.ecole.Service;

import com.example.ecole.Entities.Groupe;
import com.example.ecole.Entities.Messages.Discussion;
import com.example.ecole.Entities.Messages.Messages;
import com.example.ecole.Entities.Messages.Participer;
import com.example.ecole.Entities.Messages.Sondage;
import com.example.ecole.Repository.*;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicesMessages {
   @Autowired
   DiscussinRepository DR;
   @Autowired
    MessageRepository MR;
    @Autowired
    SondageRepository SR;
    @Autowired
    ParticiperRepository PR;
    @Autowired
    GroupeRepository GR;




    public Discussion createDiscussion(Discussion discussion){
       return DR.save(discussion);
   }

   public List<Discussion> getDiscussions(String code , String type ){
       return DR.findByDisscussionContaining(code , type);
   }
   public List<Discussion> getDiscussionsByGroupe(int code , String type ){
        Groupe groupe= GR.findById(code).get();
       return DR.findByDisscussionContaining(groupe.getNom() , type);
   }
   public  Optional<Discussion>  DiscussionAlreadyCreated(String code ){
       return DR.findByCodeDiscussion(code );
   }

   public void createMessage(Messages messages){
        MR.save(messages);
   }
   public  List<Messages> getMessages(int id ){
       return  MR.findAllByDiscussion_Id(id);
   }

   public Sondage createSondage(Sondage sondage  ){

        Sondage sondage1 = SR.save(sondage);
        return  sondage1;
   }

   public void createParticiper(Participer participer , int id ){
       Optional<Sondage> sondage = SR.findById(id);

       if (sondage.isPresent()){
        participer.setSondage(sondage.get());
       PR.save(participer);
       }
   }


   public void UpdatevuDiscussion(int id , boolean bool){
        DR.updateDiscussionVu(id , bool );
   }


}
