package me.juan.practica1.objects;

import com.google.gson.Gson;
import lombok.Getter;
import me.juan.core.database.MongoDB;
import me.juan.practica1.Main;
import org.bson.Document;

import java.util.UUID;

public abstract class ObjectMethod {

    @Getter
    private final UUID uuid;
    @Getter
    private final String name;
    private final Type type;

    public ObjectMethod(Type type, String name, boolean errExist) throws Exception{
        if (errExist && exists(name,false, type))
            throw new Exception("El '"+type.toString()+"' de nombre: '"+name+"' ya existe");

        this.name = name;
        this.uuid = UUID.randomUUID();
        this.type = type;
    }




    public boolean registerDatabase(Object obj) {
        MongoDB.MongoDatabase mongoDB = Main.getMongoDatabase();
        mongoDB.add(type.toString(), obj);
        return true;
    }

    public static Buyer getBuyer(UUID uuid) throws Exception {
        return (Buyer) load(""+uuid, true, Type.Buyers);
    }
    public static Product getProduct(UUID uuid) throws Exception {
        return (Product) load(""+uuid, true, Type.Products);
    }
    public static Licence getLicence(UUID uuid) throws Exception {
        return (Licence) load(""+uuid, true, Type.Licences);
    }

    public static Object load(String id, boolean isUuid, Type type) throws Exception{
        MongoDB.MongoDatabase mongoDB = Main.getMongoDatabase();
        for (Document document : mongoDB.findCustom(type.toString(),(isUuid ? "uuid" : "name"), id)) {
            switch (type) {
                case Licences:
                    return new Gson().fromJson(document.toJson(), Licence.class);
                case Products:
                    return new Gson().fromJson(document.toJson(), Product.class);
                case Buyers:
                    return new Gson().fromJson(document.toJson(), Buyer.class);
            }
        }
        throw new Exception("Not found exception");
    }

    public boolean exists(String id, boolean isUuid, Type type){
        MongoDB.MongoDatabase mongoDB = Main.getMongoDatabase();
        return  (mongoDB.exist(mongoDB.findCustom(type.toString(),(isUuid ? "uuid" : "name"), id)));
    }


    public enum Type {
        Buyers, Products, Licences;
    }




}
