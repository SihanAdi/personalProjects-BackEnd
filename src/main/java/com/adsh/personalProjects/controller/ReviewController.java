package com.adsh.personalProjects.controller;

import com.adsh.personalProjects.pojo.PersonalProject;
import com.adsh.personalProjects.pojo.Review;
import com.adsh.personalProjects.service.ReviewService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/adsh/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @GetMapping
    public ResponseEntity<List<Review>> getAllReview(){
        return new ResponseEntity<List<Review>>(reviewService.allReview(), HttpStatus.OK);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Optional<Review>> getSingleReview(@PathVariable String reviewId){
        return new ResponseEntity<Optional<Review>>(reviewService.singleReview(reviewId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Map<String, String> payload){
        return new ResponseEntity<Review>(reviewService.createReview(payload.get("body"), payload.get("imdbId"), payload.get("userId")), HttpStatus.CREATED);
    }

    @DeleteMapping("/{reviewId}/{imdbId}/{userId}")
    public ResponseEntity<Optional<Review>> deleteReview(@PathVariable("reviewId") String reviewId,
                                                         @PathVariable("imdbId") String imdbId,
                                                         @PathVariable("userId") String userId){
        return new ResponseEntity<>(reviewService.deleteReview(reviewId, imdbId, userId), HttpStatus.OK);
    }
    @PostMapping("/update")
    public ResponseEntity<Optional<Review>> updateReview(@RequestBody Map<String, String> payload){
        return new ResponseEntity<>(reviewService.updateReview(payload.get("body"), payload.get("reviewId"), payload.get("imdbId"), payload.get("userId")), HttpStatus.ACCEPTED);
    }
}
