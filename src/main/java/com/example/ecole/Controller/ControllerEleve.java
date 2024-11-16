package com.example.ecole.Controller;

import com.example.ecole.EmailSender.EmailService;
import com.example.ecole.Entities.*;
import com.example.ecole.Entities.ClassSpecifique.EleveSpecifique;
import com.example.ecole.Service.ServiceEleve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Eleve")
@CrossOrigin(origins = "*")
public class ControllerEleve {


    @Autowired
    private EmailService emailService;

    @Autowired
    ServiceEleve se;



    @PostMapping("/Registre")
    public ResponseEntity<HttpStatus> Regisre(@RequestBody Eleve eleve, @RequestParam int id) {
        try {
            se.registre(eleve, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace(); 
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }




    @PostMapping("/login")
    public ResponseEntity<EleveSpecifique> Login(@RequestBody Map<String, Object> requestBody ) {
        try {
            EleveSpecifique eleve = se.login(requestBody.get("email").toString(), requestBody.get("password").toString());
            if (eleve == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else {
            return new ResponseEntity<>(eleve, HttpStatus.OK);
            }
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/getEleve/{id}")
    ResponseEntity<EleveSpecifique> getEleve(@PathVariable int id) {
        try {
            Eleve e =se.GetEleve(id);
            EleveSpecifique eleve=new EleveSpecifique(e.getId() , e.getNom() ,e.getGendre() ,  e.getPrenom() , e.getEmail(),  e.getGroupe().getId());
            return new ResponseEntity<>(eleve, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/GetRendement/{id}")
    ResponseEntity<List<Rendement>> GetRendement(@PathVariable int id) {
            try {

                List<Rendement> rendements = se.RendementByEleve(id);
                return  new ResponseEntity<>(rendements , HttpStatus.OK);
            }catch (Exception e) {
                return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }

    @PostMapping("/GetAbsenceByEleve")
    ResponseEntity<List<Absent>> Absence(@RequestBody Eleve eleve) {
        try {

            List<Absent> absents = se.listAbsentByEleve(eleve);
            return  new ResponseEntity<>( absents, HttpStatus.OK);
        }catch (Exception e ){
                return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/updatevu")
    ResponseEntity<HttpStatus> UpdateV(@RequestBody Eleve eleve) {
    try{
            se.Updatevu(eleve);

            return  new ResponseEntity<>(HttpStatus.OK);
    }catch (Exception e ){
                return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    }


    @GetMapping("/GetSeance/{id}")
    ResponseEntity<List<Seance>> seances(@PathVariable int id ){
            System.out.println(id);
        try {
            List<Seance> seances = se.Getseances(id);
            return  new ResponseEntity<>(seances, HttpStatus.OK);
        }catch (Exception e ){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

@PutMapping("/updateUpgrade")
    public ResponseEntity<String> updateUpgrade(@RequestParam int id) {
        try {
            se.Updateupgrade(id);
            return new ResponseEntity<>("Eleve upgrade successful", HttpStatus.OK);
        }catch (
                Exception e
        ){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
}
    @GetMapping("/GetScore")
public List<Object[]>  test(){
        return se.Test();
}

}
