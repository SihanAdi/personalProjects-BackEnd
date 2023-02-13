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
import java.util.Optional;
import java.util.UUID;

@Document(collection = "persons")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    private ObjectId id;
    private String userId;
    private String userName;
    private String userPassword;
    private String gender;
    private String birthday;
    private String phone;
    private String address;
    private String creationDate;
    private String age;
    @DocumentReference
    private List<Review> reviewIds;
    @DocumentReference
    private List<PersonalProject> personalProjectIds;

    public Person(String userName, String userPassword,
                  String gender, String birthday, String phone,
                  String address, String age) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.address = address;
        this.age = age;

        UUID uuid = UUID. randomUUID();
        this.userId = uuid.toString();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        this.creationDate = dateFormat.format(date);
    }
}
