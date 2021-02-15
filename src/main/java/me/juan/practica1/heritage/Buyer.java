package me.juan.practica1.heritage;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import lombok.Getter;
import me.juan.core.database.MongoDB;
import me.juan.core.utils.StringUtil;
import me.juan.core.utils.TimeUtil;
import me.juan.practica1.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Getter
public class Buyer {

    public static MongoDB.MongoDatabase mongoDatabase;

    private final String name;
    private final UUID uuid;
    private final ArrayList<UUID> licences = new ArrayList<>();

    public Buyer(String name) throws Exception {
        if (exist(name, false))
            throw new Exception("Este comprador ya esta registrado.");
        this.name = name;
        this.uuid = UUID.randomUUID();
        mongoDatabase.add(null, this);

    }

    public static boolean exist(Object id, boolean isUuid) {
        return mongoDatabase.exist(mongoDatabase.getDefCollection().find(Filters.eq(isUuid ? "uuid" : "name", id.toString())));
    }

    public static Buyer get(Object id, boolean isUuid) {
        return mongoDatabase.get(mongoDatabase.getDefCollection().find(Filters.eq(isUuid ? "uuid" : "name", id.toString())), Buyer.class);
    }

    public void addLicence(UUID uuid) {
        mongoDatabase.getDefCollection().updateOne(Filters.eq("uuid", this.uuid.toString()), Updates.push("licences", uuid.toString()));
    }

    public MessageEmbed usuarioGenerado(String title) {
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("**Generador de licencias** \nㅤ\n*" + title + "*\nㅤ\n")
                .addField("Nombre:", "*" + StringUtil.comentarDiscord2(getName()) + "*", true)
                .addField("Compras:", "*" + StringUtil.comentarDiscord2("" + getLicences().size()) + "*", true)
                .addField("ID:", "*" + StringUtil.comentarDiscord2("" + getUuid()) + "*", false)
                .addBlankField(false)
                .setThumbnail("https://i.ibb.co/PmTKp4C/icons8-facebook-like-1080px.png")
                .setColor(Color.gray)
                .setTimestamp(TimeUtil.getCalendar(new Date()).toInstant())
                .setFooter("Announcement by JuanC's Licences", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
        return embedBuilder.build();
    }

}
