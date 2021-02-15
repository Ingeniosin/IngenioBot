package me.juan.practica1.objects;

import java.util.ArrayList;
import java.util.UUID;

public class Buyer extends ObjectMethod{

    private final ArrayList<UUID> licences = new ArrayList<>();

    public Buyer(String name) throws Exception {
        super(Type.Buyers, name, true);
        registerDatabase(Buyer.this);
    }
}
