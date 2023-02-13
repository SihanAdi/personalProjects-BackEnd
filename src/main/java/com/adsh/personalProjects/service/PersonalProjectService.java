package com.adsh.personalProjects.service;

import com.adsh.personalProjects.pojo.Person;
import com.adsh.personalProjects.pojo.PersonalProject;
import com.adsh.personalProjects.pojo.Review;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonalProjectService {
    @Autowired
    private PersonalProjectRepository personalProjectRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ReviewService reviewService;

    public List<PersonalProject> allPersonalProjects(){
        return personalProjectRepository.findAll();
    }

    public Optional<PersonalProject> singlePersonalProject(String imdbId){
        return personalProjectRepository.findByImdbId(imdbId);
        //Optional<PersonalProject> can accept that maybe return null
    }
    //update直接删除原有的，再加一个新的
    public PersonalProject createPersonalProject(String title, String trailerLink, String poster,
                                                 List<String> genres, List<String> backdrops, String userId){
        PersonalProject personalProject = personalProjectRepository.insert(new PersonalProject(title,trailerLink,poster,
                genres,backdrops));

        mongoTemplate.update(Person.class)
                .matching(Criteria.where("userId").is(userId))
                .apply(new Update().push("personalProjectIds").value(personalProject))
                .first();

        return personalProject;
    }

    public Optional<PersonalProject> deletePersonalProject(String imdbId, String userId){
        Optional<PersonalProject> personalProject = personalProjectRepository.findByImdbId(imdbId);
        if (personalProject == null){
            return null;
        }else{
            PersonalProject pp = personalProject.get();
            for (Review review : pp.getReviewIds()){
                reviewService.deleteReview(review.getReviewId(), imdbId, userId);
            }
            Query query1 = Query.query(Criteria.where("imdbId").is(imdbId));
            mongoTemplate.update(Person.class)
                    .matching(Criteria.where("userId").is(userId))
                    .apply(new Update().pull("PersonalProject", query1))
                    .first();
            mongoTemplate.findAndRemove(query1, PersonalProject.class);
        }
        return personalProject;
    }
    public Optional<PersonalProject> updatePersonalProject(String title, String trailerLink, String poster,
                                                 List<String> genres, List<String> backdrops, String imdbId, String userId){
        Optional<PersonalProject> personalProject = personalProjectRepository.findByImdbId(imdbId);
        if (personalProject == null){
            return null;
        }else{
            Query query1 = Query.query(Criteria.where("imdbId").is(imdbId));
            mongoTemplate.updateFirst(query1, Update.update("title", title), PersonalProject.class);
            mongoTemplate.updateFirst(query1, Update.update("trailerLink", trailerLink), PersonalProject.class);
            mongoTemplate.updateFirst(query1, Update.update("poster", poster), PersonalProject.class);
            mongoTemplate.updateFirst(query1, Update.update("genres", genres), PersonalProject.class);
            mongoTemplate.updateFirst(query1, Update.update("backdrops", backdrops), PersonalProject.class);

            mongoTemplate.update(Person.class)
                    .matching(Criteria.where("userId").is(userId))
                    .apply(new Update().pull("personalProjectIds", query1))
                    .first();
        }


        return personalProject;
    }
}
