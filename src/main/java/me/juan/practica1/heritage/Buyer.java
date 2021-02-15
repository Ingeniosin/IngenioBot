package me.juan.practica1.heritage;

import lombok.Getter;
import me.juan.core.database.MongoDB;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.UUID;

@Getter
public class Buyer {

    public static MongoDB.MongoDatabase mongoDatabase;

    private final String name;
    private final UUID uuid;
    private final ArrayList<UUID> licences = new ArrayList<>();

    public Buyer(String name) throws Exception {
        if (get(name, false) != null)
            throw new Exception("Este comprador ya esta registrado.");
        this.name = name;
        this.uuid = UUID.randomUUID();
        mongoDatabase.add(null, this);

    }

    public static boolean exist(UUID uuid) {
        return mongoDatabase.exist(mongoDatabase.findCustom("Buyers", "uuid", uuid));
    }

    public static Buyer get(Object id, boolean isUuid) {
        return (isUuid ? getByUUID(UUID.fromString(id.toString())) : getByName(id.toString()));
    }

    private static Buyer getByName(String name) {
        return mongoDatabase.get(mongoDatabase.findCustom("Buyers", "name", name), Buyer.class);
    }

    private static Buyer getByUUID(UUID uuid) {
        return mongoDatabase.get(mongoDatabase.findCustom("Buyers", "uuid", uuid), Buyer.class);
    }

    public void addLicence(UUID uuid) {
        Bson query = new Document("uuid", uuid);
        Bson update = new Document("$push", new Document("licences", uuid));
        mongoDatabase.getDefCollection().findOneAndUpdate(query, update);
    }

}
