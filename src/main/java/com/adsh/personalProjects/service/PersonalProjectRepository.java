package com.adsh.personalProjects.service;

import com.adsh.personalProjects.pojo.PersonalProject;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalProjectRepository extends MongoRepository<PersonalProject, ObjectId> {
    Optional<PersonalProject> findByImdbId(String imdbId);
}
