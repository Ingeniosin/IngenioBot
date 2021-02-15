package me.juan.practica1.heritage;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Getter
public class Buyer {

    @Getter
    private static final HashMap<UUID, Buyer> uuidBuyerHashMap = new HashMap<>();
    @Getter
    private static final HashMap<String, Buyer> stringBuyerHashMap = new HashMap<>();

    private final String name;
    private final UUID uuid;
    private final ArrayList<UUID> licences = new ArrayList<>();

    public Buyer(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
        index();
    }

    public void index() {
        uuidBuyerHashMap.put(getUuid(), this);
        stringBuyerHashMap.put(getName(), this);
    }

    public ArrayList<Licence> getLicences() {
        ArrayList<Licence> licences = new ArrayList<>();
        for (UUID uuid : this.licences) {
            Licence licence = Licence.getUuidLicenceHashMap().getOrDefault(uuid, null);
            if (licence != null) {
                licences.add(licence);
            }
        }
        return licences;
    }

}
