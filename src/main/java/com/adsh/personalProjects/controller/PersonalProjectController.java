package com.adsh.personalProjects.controller;

import com.adsh.personalProjects.pojo.Person;
import com.adsh.personalProjects.pojo.PersonalProject;
import com.adsh.personalProjects.service.PersonalProjectService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/adsh/personalprojects")
public class PersonalProjectController {
    @Autowired
    private PersonalProjectService personalProjectService;

    @GetMapping
    public ResponseEntity<List<PersonalProject>> getAllPersonalProjects(){
        return new ResponseEntity<List<PersonalProject>>(personalProjectService.allPersonalProjects(), HttpStatus.OK);
    }

    @GetMapping("/{imdbId}")
    public ResponseEntity<Optional<PersonalProject>> getSinglePersonalProject(@PathVariable String imdbId){
        return new ResponseEntity<Optional<PersonalProject>>(personalProjectService.singlePersonalProject(imdbId), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<PersonalProject> createPersonalProject(@RequestBody HashMap<String, Object> payload){
        return new ResponseEntity<PersonalProject>(personalProjectService.createPersonalProject((String) payload.get("title"),
                (String) payload.get("trailerLink"), (String) payload.get("poster"), (List<String>) payload.get("genres"), (List<String>) payload.get("backdrops"), (String) payload.get("userId")), HttpStatus.CREATED);
    }
    @DeleteMapping ("/{imdbId}/{userId}")
    public ResponseEntity<Optional<PersonalProject>> deleteSinglePersonalProject(@PathVariable("imdbId") String imdbId,
                                                                              @PathVariable("userId") String userId){
        return new ResponseEntity<Optional<PersonalProject>>(personalProjectService.deletePersonalProject(imdbId,userId), HttpStatus.OK);
    }
    @PostMapping("/update")
    public ResponseEntity<Optional<PersonalProject>> updatePersonalProject(@RequestBody HashMap<String, Object> payload){
        return new ResponseEntity<Optional<PersonalProject>>(personalProjectService.updatePersonalProject((String) payload.get("title"),
                (String) payload.get("trailerLink"), (String) payload.get("poster"), (List<String>) payload.get("genres"), (List<String>) payload.get("backdrops"), (String) payload.get("imdbId"), (String) payload.get("userId")), HttpStatus.CREATED);
    }
}
