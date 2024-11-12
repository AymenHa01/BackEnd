package com.example.ecole.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.ecole.Entities.Absent;
import com.example.ecole.Entities.Eleve;
import com.example.ecole.Entities.Groupe;
import com.example.ecole.Entities.Professeur;
import com.example.ecole.Entities.Rendement;
import com.example.ecole.Entities.Seance;
import com.example.ecole.Repository.AbsentRepository;
import com.example.ecole.Repository.SessionRepository;
import com.example.ecole.Service.ServiceEleve;
import com.example.ecole.Service.ServiceProf;

@RequestMapping("/Prof")
@RestController
@CrossOrigin(origins = "*")
public class ControllerProf {
    @Autowired
    AbsentRepository AB;


    @Autowired
    SessionRepository s ;

    @Autowired
    ServiceProf pf;
        @Autowired
        ServiceEleve SE;
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

////
        //connect to Profstreamprocessor
        // @GetMapping(path = "/Profstream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        // public SseEmitter streamSeances() {
        //     SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        //     emitters.add(emitter);    
        //     emitter.onCompletion(() -> emitters.remove(emitter));
        //     emitter.onTimeout(() -> emitters.remove(emitter));
        //     return emitter;
        // }
    




    @PostMapping("/login")
    public ResponseEntity<Professeur> Login(@RequestBody Map<String, Object> requestBody ) {
        try {
            Professeur Prof = pf.login(requestBody.get("email").toString(), requestBody.get("password").toString());
            if (Prof == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(Prof, HttpStatus.OK);
            }
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/GetSeance/{id}")
    public  ResponseEntity<List<Seance>> GetSeance(@PathVariable int id ){
            try {
                List<Seance> seances = pf.GetSeance(id);
                return new ResponseEntity<>(seances, HttpStatus.OK);
            }catch (Exception e ){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
    }

    @GetMapping("/GetSession/{id}")
    public  ResponseEntity<Professeur> getSession(@PathVariable("id") int id) {
        try {
                Professeur prof = pf.getProfesseur(id);
                return new ResponseEntity<>(prof, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(value = "/AbsentEleve")
    public ResponseEntity<HttpStatus>  AbsentEleve(@RequestBody Absent absent ){
        try {
        pf.AbsentEleve(absent);
        // notifyClients("absenteleve");
        return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/DeleteABsent")
    public  ResponseEntity<HttpStatus> DeleteABsent(@RequestBody Absent absent ){
        System.out.println(absent);
        try{
            pf.DeleteAbsent(absent);
            //notifyClients("absenteleve");
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e ){
            e.getCause();
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/Listeabsent/{id}")
    public ResponseEntity<List<Absent>> listeAbsent(@PathVariable int id ){
    List<Absent> absents = AB.findAbsentBySeance_Id(id);
    return new ResponseEntity<>(absents,HttpStatus.OK);
    }

    @PostMapping("/GetGroupe")
    public  ResponseEntity<List<Groupe>> ListGroupe(@RequestBody Seance seance){
        try {
        List<Groupe> groupes = pf.Groupeparseance(seance);
        return  new ResponseEntity<>(groupes,HttpStatus.OK);
        }catch (Exception e){
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping("/EleveRendement/{id}")
    public ResponseEntity<Page<Rendement>> rendementList(@PathVariable  int id ,  @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size ){
        Page<Rendement> rendements = pf.EleveRendement(id , page, size);
        return new ResponseEntity<>(rendements,HttpStatus.OK);
    }

    @PostMapping("/elevesBySeanceAndDate")
    public ResponseEntity<List<Eleve>> getElevesBySeanceAndDate(
            @RequestBody Absent absent ) {
        System.out.println(absent);
        try{
        List<Eleve>  eleves = pf.listeDesAbsent(absent.getSeance() , absent.getDate());
        return new ResponseEntity<>(eleves,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/ListeDesGroupe")
    public  ResponseEntity<List<Groupe>> ListeDesGroupe(@RequestBody Professeur professeur)
    {
        try {
           List<Groupe> groupes =  pf.ListeDesGroupe(professeur);
           return new ResponseEntity<>(groupes,HttpStatus.OK);
        }catch (Exception e){
        return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/changeRendement")
    public ResponseEntity<Rendement> changeRendement(@RequestBody Rendement rendement){
    rendement.setVu(false);
        Rendement rendement1=pf.changeRendement(rendement);
        return new ResponseEntity<>(rendement1,HttpStatus.OK);
    }

    @PostMapping("/Updatecommentaire")
    public ResponseEntity<HttpStatus> updatecmnts(@RequestParam int id , @RequestParam String cmnts){
        try{
            pf.Updatecommentaire(id , cmnts);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e ){
            
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addRendement/{id}")
    public  ResponseEntity<HttpStatus> addRendement(@RequestBody Eleve eleve , @PathVariable  int id  ){
        try{
            SE.addRendement(eleve , id );
            return new ResponseEntity<>(HttpStatus.OK);

        }catch (Exception e ){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
