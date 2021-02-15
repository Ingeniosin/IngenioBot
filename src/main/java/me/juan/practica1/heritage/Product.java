package me.juan.practica1.heritage;

import com.google.gson.Gson;
import lombok.Getter;
import me.juan.core.database.MongoDB;
import me.juan.practica1.Main;
import org.bson.Document;

import java.util.HashMap;
import java.util.UUID;

@Getter
public class Product {

    @Getter
    private static final HashMap<UUID, Product> productHashMapUuid = new HashMap<>();
    @Getter
    private static final HashMap<String, Product> productHashMapString = new HashMap<>();

    private final String name;
    private final UUID uuid;
    private final int limit;

    public Product(String name, int limit) throws Exception {
        if (exists(name, false))
            throw new Exception("This product already exists");
        this.name = name;
        this.limit = limit;
        uuid = UUID.randomUUID();
        index();
        register();
    }

    public static Product loadByUuid(UUID uuid) {
        MongoDB.MongoDatabase mongoDB = Main.getMongoDatabase();
        for (Document document : mongoDB.findSpecificDefault(new String[]{"uuid"}, new Object[]{uuid})) {
            Gson gson = new Gson();
            return gson.fromJson(document.toJson(), Product.class).index();
        }
        return null;
    }

    public static Product loadByName(String name) {
        MongoDB.MongoDatabase mongoDB = Main.getMongoDatabase();
        for (Document document : mongoDB.findDefault("name", name)) {
            Gson gson = new Gson();
            return gson.fromJson(document.toJson(), Product.class).index();
        }
        return null;
    }



    public boolean register() {
        MongoDB.MongoDatabase mongoDB = Main.getMongoDatabase();
        mongoDB.add(null, this);
        return true;
    }

    public boolean exists(String id, boolean isUuid){
        MongoDB.MongoDatabase mongoDB = Main.getMongoDatabase();
        return  (mongoDB.exist(mongoDB.findCustom("Licences",(isUuid ? "uuid" : "name"), id)));
    }

    public Product index() {
        productHashMapUuid.put(getUuid(), this);
        productHashMapString.put(getName(), this);
        return this;
    }


}
