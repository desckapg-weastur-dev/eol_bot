package com.desckapg.eolbot.database.pojo;

import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
public class User {

    @BsonProperty("tgID")
    private final Long tgID;

    @BsonProperty("lang")
    private String language;

    @BsonCreator
    public User(@BsonProperty("tgID") Long tgID,
                @BsonProperty("lang") String language) {
        this.tgID = tgID;
        this.language = language;
    }

    public static User defaultData(Long tgID) {
        return new User(tgID, "en");
    }


}
