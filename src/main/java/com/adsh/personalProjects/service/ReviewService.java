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
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Review> allReview(){
        return reviewRepository.findAll();
    }

    public Optional<Review> singleReview(String reviewId){
        return reviewRepository.findByReviewId(reviewId);
    }
    public Review createReview(String reviewBody, String imdbId, String userId){
        Review review = reviewRepository.insert(new Review(reviewBody));

        mongoTemplate.update(PersonalProject.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviewIds").value(review))
                .first();
        mongoTemplate.update(Person.class)
                .matching(Criteria.where("userId").is(userId))
                .apply(new Update().push("reviewIds").value(review))
                .first();

        return review;
    }

    public Optional<Review> deleteReview(String reviewId, String imdbId, String userId){
        Optional<Review> review = reviewRepository.findByReviewId(reviewId);
        if (review == null){
            return null;
        }else{
            Query query1 = Query.query(Criteria.where("reviewId").is(reviewId));
            mongoTemplate.update(PersonalProject.class)
                    .matching(Criteria.where("imdbId").is(imdbId))
                    .apply(new Update().pull("reviewIds", query1))
                    .first();
            mongoTemplate.update(Person.class)
                    .matching(Criteria.where("userId").is(userId))
                    .apply(new Update().pull("reviewIds", query1))
                    .first();
            mongoTemplate.findAndRemove(query1, Review.class);
        }
        return review;
    }

    public Optional<Review> updateReview(String reviewBody, String reviewId, String imdbId, String userId){
        Optional<Review> review = reviewRepository.findByReviewId(reviewId);

        if (review == null){
            return null;
        }else{
            Query query1 = Query.query(Criteria.where("reviewId").is(reviewId));
            mongoTemplate.updateFirst(query1, Update.update("body", reviewBody), Review.class);
            mongoTemplate.update(PersonalProject.class)
                    .matching(Criteria.where("imdbId").is(imdbId))
                    .apply(new Update().pull("reviewIds", query1))
                    .first();
            mongoTemplate.update(Person.class)
                    .matching(Criteria.where("userId").is(userId))
                    .apply(new Update().pull("reviewIds", query1))
                    .first();
        }
        return review;
    }

}
