package com.adsh.personalProjects.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Document(collection = "personnalProjects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalProject {
    @Id
    private ObjectId id;
    private String imdbId;
    private String title;
    private String releaseDate;
    private String trailerLink;
    private String poster;
    private List<String> genres;
    private List<String> backdrops;
    @DocumentReference
    private List<Review> reviewIds;
    @DocumentReference
    private Person personId;

    public PersonalProject(String title, String trailerLink, String poster,
                           List<String> genres, List<String> backdrops) {
        this.title = title;
        this.trailerLink = trailerLink;
        this.poster = poster;
        this.genres = genres;
        this.backdrops = backdrops;

        UUID uuid = UUID. randomUUID();
        this.imdbId = uuid.toString();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        this.releaseDate = dateFormat.format(date);
    }
}
