package com.adsh.personalProjects.controller;

import com.adsh.personalProjects.pojo.Person;
import com.adsh.personalProjects.pojo.PersonalProject;
import com.adsh.personalProjects.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/adsh/person")
public class PersonController {
    @Autowired
    private PersonService personService;
    @GetMapping
    public ResponseEntity<List<Person>> getAllPerson(){
        return new ResponseEntity<List<Person>>(personService.allPerson(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Map<String, String> payload){
        return new ResponseEntity<Person>(personService.createPerson(payload.get("userName"),payload.get("userPassword"),
                payload.get("gender"),payload.get("birthday"),payload.get("phone"),payload.get("address"),
                payload.get("age")), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Optional<Person>> getSinglePerson(@PathVariable String userId){
        return new ResponseEntity<Optional<Person>>(personService.singlePerson(userId), HttpStatus.OK);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<Optional<Person>> deletePerson(@PathVariable String userId){
        return new ResponseEntity<Optional<Person>>(personService.deletePerson(userId), HttpStatus.OK);
    }
    @PostMapping("/update")
    public ResponseEntity<Optional<Person>> updatePerson(@RequestBody Map<String, String> payload){
        return new ResponseEntity<Optional<Person>>(personService.updatePerson(payload.get("userName"),payload.get("userPassword"),
                payload.get("gender"),payload.get("birthday"),payload.get("phone"),payload.get("address"),
                payload.get("age"), payload.get("userId")), HttpStatus.CREATED);
    }

}
