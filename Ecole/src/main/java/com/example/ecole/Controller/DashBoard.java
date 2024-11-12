package com.example.ecole.Controller;

import com.example.ecole.Entities.*;
import com.example.ecole.Entities.ClassSpecifique.GroupeAndCours;
import com.example.ecole.Repository.ChapiterRepository;
import com.example.ecole.Repository.CoursRepository;
import com.example.ecole.Repository.GroupeRepository;
import com.example.ecole.Repository.ProfesseurRepository;
import com.example.ecole.Service.DashBoardService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/DashBoard")
@CrossOrigin(origins = "*")
public class DashBoard {
    @Autowired
    DashBoardService DS;
    @Autowired
    GroupeRepository groupeRepository;
    @Autowired
    ProfesseurRepository professeurRepository;
    @Autowired
    CoursRepository coursRepository;

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();


    @GetMapping("/GroupesAndCours/{id}")
    public ResponseEntity<GroupeAndCours> getGroupesAndCours(@PathVariable int id ) {
        try {
            GroupeAndCours groupeAndCours= DS.GetAllGroupesAndAllcours(id);

            return new ResponseEntity<>(groupeAndCours , HttpStatus.OK );
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/AddSeance")
    public ResponseEntity<HttpStatus> addSeance(@RequestBody Seance seance , @RequestParam int idprof , @RequestParam int idgroupe ) {
        try {
            Groupe groupe = groupeRepository.findById(idgroupe)
                    .orElseThrow(() -> new EntityNotFoundException("Groupe not found"));
            Professeur professeur = professeurRepository.findById(idprof)
                    .orElseThrow(() -> new EntityNotFoundException("Professeur not found"));
            seance.setGroupe(groupe);
            seance.setProfesseur(professeur);

            DS.AddSeance(seance);
//            notifyClients( seance, "seance-add");
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }}

    @GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamSeances() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.add(emitter);

        // Clean up when the client disconnects
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }



    @PutMapping("/updateSeance")
    public ResponseEntity<HttpStatus> update(@RequestBody Seance seance , @RequestParam int idprof , @RequestParam int idgroupe ) {
        try {
            Groupe groupe = groupeRepository.findById(idgroupe)
                    .orElseThrow(() -> new EntityNotFoundException("Groupe not found"));
            Professeur professeur = professeurRepository.findById(idprof)
                    .orElseThrow(() -> new EntityNotFoundException("Professeur not found"));
            seance.setGroupe(groupe);
            seance.setProfesseur(professeur);

            DS.AddSeance(seance);
            notifyClients( seance, "seance-add");
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }}

        @DeleteMapping("/DeleteSeance/{id}")
        public ResponseEntity<HttpStatus> deleteSeance(@PathVariable int id) {
            try {
                DS.DeleteSeance(id);
                notifyClients( new Seance() , "seance-add");
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    @PostMapping("/AddCours")
    public ResponseEntity<Integer> addCours(@RequestBody Cours cours ) {
        try {
            int coursId = DS.addcours(cours);
            return new ResponseEntity<>(coursId, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/DeleteCours/{id}")
    public ResponseEntity<HttpStatus> deleteCours(@PathVariable int id) {
        try {
            DS.DeleteCours(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/AddChapiter")
    public ResponseEntity<HttpStatus> addChapiter(@RequestBody Chapiter chapiter , @RequestParam int id) {
        try {
            Cours cours = coursRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Groupe not found"));
            chapiter.setCours(cours);

            DS.addchapiter(chapiter);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @DeleteMapping("/DeleteChapiter/{id}")
    public ResponseEntity<HttpStatus> deletechapiter(@PathVariable int id) {
        try {
            DS.Deletechapiter(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private void notifyClients(Seance seance , String endpoint ) {

        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name(endpoint)
                        .data(seance));
            } catch (IOException e) {
                emitter.completeWithError(e);
                emitters.remove(emitter);  // Remove if there's an error
            }
        }
    }


}
