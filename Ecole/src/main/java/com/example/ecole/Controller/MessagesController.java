package com.example.ecole.Controller;

import com.example.ecole.EmailSender.EmailService;
import com.example.ecole.Entities.Messages.Discussion;
import com.example.ecole.Entities.Messages.Messages;
import com.example.ecole.Entities.Messages.Participer;
import com.example.ecole.Entities.Messages.Sondage;
import com.example.ecole.Service.ServicesMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.http.MediaType;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/Messages")

public class MessagesController {

    @Autowired
    ServicesMessages SM;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @Autowired
    private EmailService emailService;
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();


   //connect to Profstream 
   @GetMapping(path = "/MessagesStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
   public SseEmitter streamSeances() {
       SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
       emitters.add(emitter);    
       emitter.onCompletion(() -> emitters.remove(emitter));
       emitter.onTimeout(() -> emitters.remove(emitter));
       return emitter;
   }




    @PostMapping("/createDiscussion")
    public ResponseEntity<Integer> createDiscussion(@RequestBody Discussion D) {
        try{
            Discussion discussion = AlreadyDisscussion(D.getCodeDiscussion());
            System.out.println(discussion);
            if ( AlreadyDisscussion(D.getCodeDiscussion())==null){
            Discussion discussion1 = SM.createDiscussion(D);
                messagingTemplate.convertAndSend("/topic/ms" , "{}");
            //notifyClients("Discussion");
            return  new ResponseEntity<>(discussion1.getId() , HttpStatus.CREATED);
            }else{

                return new ResponseEntity<>(discussion.getId(),HttpStatus.OK);
            }
        }catch (Exception e ){
            e.printStackTrace();
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createMessages")
    public ResponseEntity<HttpStatus> createMessage(@RequestBody Messages m) {
        try{
            SM.createMessage(m);
            SM.UpdatevuDiscussion(m.getDiscussion().getId(),false);
            messagingTemplate.convertAndSend("/topic/ms" , m);
           // notifyClients("messagesSend");
            return  new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e ){
            e.printStackTrace();
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/GetDisscussion")
    public ResponseEntity<List<Discussion>> getDisscussion(@RequestParam String code , @RequestParam String type) {
        try{

            List<Discussion> discussion = SM.getDiscussions(code , type) ;
            return new ResponseEntity<>(discussion , HttpStatus.OK);
        }catch (Exception e ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } @GetMapping("/GetDisscussionBygroupe")
    public ResponseEntity<List<Discussion>> getDisscussion(@RequestParam int  code , @RequestParam String type) {
        try{

            List<Discussion> discussion = SM.getDiscussionsByGroupe(code , type) ;
            return new ResponseEntity<>(discussion , HttpStatus.OK);
        }catch (Exception e ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public Discussion AlreadyDisscussion(@RequestParam String code ) {
        try{
            Optional<Discussion> discussion = SM.DiscussionAlreadyCreated(code ) ;
            if (discussion.isPresent()){
            return discussion.get();
            }else{
            return null;
            }
        }catch (Exception e ){
            return null;
        }
    }
    @GetMapping("/Getmessage/{id}")
    public ResponseEntity<List<Messages>> getMessage(@PathVariable int id   ) {
        try {
            List<Messages> messages = SM.getMessages(id);
            return new ResponseEntity<>(messages , HttpStatus.OK);
        }catch (Exception e ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/createSondage")
    ResponseEntity<Sondage> createSondage(@RequestBody Sondage s  ) {
        try{
            Sondage sondage = SM.createSondage(s );
            messagingTemplate.convertAndSend("/topic/ms" , "{}");
           // notifyClients("messagesSend");
            return  ResponseEntity.ok(sondage);
        }catch (Exception e ){
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/Participer/{id}")
    ResponseEntity<String> createParticiper(@RequestBody Participer p , @PathVariable int id ) {
        try {
            SM.createParticiper(p , id);
            //notifyClients("participer");
            messagingTemplate.convertAndSend("/topic/ms" , "{}");
            return  ResponseEntity.ok("Participer");
        }catch (Exception e ){
            return  ResponseEntity.ok(e.getMessage());
        }
    }

    @PutMapping("/UpdateDiscusionvu/{id}")
    ResponseEntity<HttpStatus> Updatevu (@PathVariable int id  , @RequestParam boolean bool ){
        try{
    SM.UpdatevuDiscussion(id,bool);
    return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e ){
return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("SendEmail")
    ResponseEntity<HttpStatus> SendEmail( @RequestParam String email , @RequestParam String  subject, @RequestParam String message){
        try{
            emailService.sendSimpleEmail(email , subject , message);
        return  new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e ){
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    private void notifyClients( String endpoint ) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name(endpoint).data("")
                       );
            } catch (IOException e) {
                emitter.completeWithError(e);
                emitters.remove(emitter);  // Remove if there's an error
            }
        }
    }






}
