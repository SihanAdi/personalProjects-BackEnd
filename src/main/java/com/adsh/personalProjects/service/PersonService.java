package com.adsh.personalProjects.service;

import com.adsh.personalProjects.pojo.Person;
import com.adsh.personalProjects.pojo.PersonalProject;
import com.adsh.personalProjects.pojo.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private PersonalProjectService personalProjectService;

    public List<Person> allPerson(){
        return personRepository.findAll();
    }

    public Optional<Person> singlePerson(String userId){
        return personRepository.findByUserId(userId);
    }
    public Person createPerson(String userName, String userPassword,
                               String gender, String birthday, String phone,
                               String address, String age){
        Person person = personRepository.insert(new Person(userName,userPassword,gender,
                                                            birthday, phone,address, age));
        return person;
    }

    public Optional<Person> deletePerson(String userId){
        Optional<Person> person = personRepository.findByUserId(userId);
        if (person == null){
            return null;
        }else{
            Person p = person.get();

            for (PersonalProject personalProject : p.getPersonalProjectIds()){
                personalProjectService.deletePersonalProject(personalProject.getImdbId(), userId);
            }
            Query query1 = Query.query(Criteria.where("userId").is(userId));
            mongoTemplate.findAndRemove(query1, Person.class);
        }
        return person;
    }
    public Optional<Person> updatePerson(String userName, String userPassword,
                               String gender, String birthday, String phone,
                               String address, String age, String userId){
        Optional<Person> person = personRepository.findByUserId(userId);
        if (person == null){
            return null;
        }else{
            Query query1 = Query.query(Criteria.where("userId").is(userId));
            mongoTemplate.updateFirst(query1, Update.update("userName", userName), Person.class);
            mongoTemplate.updateFirst(query1, Update.update("userPassword", userPassword), Person.class);
            mongoTemplate.updateFirst(query1, Update.update("gender", gender), Person.class);
            mongoTemplate.updateFirst(query1, Update.update("birthday", birthday), Person.class);
            mongoTemplate.updateFirst(query1, Update.update("phone", phone), Person.class);
            mongoTemplate.updateFirst(query1, Update.update("address", address), Person.class);
            mongoTemplate.updateFirst(query1, Update.update("age", age), Person.class);


        }
        return person;
    }
}
