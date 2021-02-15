package me.juan.practica1.heritage;

import lombok.Getter;
import me.juan.core.database.MongoDB;

import java.util.UUID;

@Getter
public class Product {


    public static MongoDB.MongoDatabase mongoDatabase;
    private final String name;
    private final UUID uuid;
    private final int limit;

    public Product(String name, int limit) throws Exception {
        if (get(name, false) != null)
            throw new Exception("Este producto ya existe.");
        this.name = name;
        this.limit = limit;
        uuid = UUID.randomUUID();
        mongoDatabase.add(null, this);
    }


    public static Product get(String id, boolean isUuid) {
        return (isUuid ? getByUUID(UUID.fromString(id)) : getByName(id));
    }

    private static Product getByName(String name) {
        return mongoDatabase.get(mongoDatabase.findCustom("Products", "name", name), Product.class);
    }

    private static Product getByUUID(UUID uuid) {
        return mongoDatabase.get(mongoDatabase.findCustom("Products", "uuid", uuid), Product.class);
    }

}
