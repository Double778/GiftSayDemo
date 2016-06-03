package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyClass {
    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.zhao.giftsaydemo.db");
        addNoteStrategy(schema);
        addNoteGift(schema);
        try {
            new DaoGenerator().generateAll(schema, "./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addNoteStrategy(Schema schema) {
        Entity entity = schema.addEntity("Strategy");
        entity.addIdProperty().autoincrement().primaryKey();
        entity.addIntProperty("channels");
        entity.addStringProperty("name");
        entity.addStringProperty("url");
        entity.addStringProperty("imgUrl");
        entity.addBooleanProperty("isLiked");
        entity.addIntProperty("likeCount");

    }
    private static void addNoteGift(Schema schema){
        Entity entity = schema.addEntity("Gift");
        entity.addIdProperty().autoincrement().primaryKey();
        entity.addStringProperty("imgUrl");
        entity.addStringProperty("name");
        entity.addStringProperty("taobaoUrl");


    }
}
