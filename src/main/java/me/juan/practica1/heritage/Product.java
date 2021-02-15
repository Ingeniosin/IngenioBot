package me.juan.practica1.heritage;

import com.mongodb.client.model.Filters;
import lombok.Getter;
import me.juan.core.database.MongoDB;
import me.juan.core.utils.StringUtil;
import me.juan.core.utils.TimeUtil;
import me.juan.practica1.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.Date;
import java.util.UUID;

@Getter
public class Product {


    public static MongoDB.MongoDatabase mongoDatabase;
    private final String name;
    private final UUID uuid;
    private final int limit;

    public Product(String name, int limit) throws Exception {
        if (exist(name, false))
            throw new Exception("Este producto ya existe.");
        this.name = name;
        this.limit = limit;
        uuid = UUID.randomUUID();
        mongoDatabase.add(null, this);
    }

    public static Product get(Object id, boolean isUuid) {
        return mongoDatabase.get(mongoDatabase.getDefCollection().find(Filters.eq(isUuid ? "uuid" : "name", id.toString())), Product.class);
    }

    public static boolean exist(Object id, boolean isUuid) {
        return mongoDatabase.exist(mongoDatabase.getDefCollection().find(Filters.eq(isUuid ? "uuid" : "name", id.toString())));
    }

    public MessageEmbed productoGenerado(String title) {
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("**Generador de licencias** \nㅤ\n*" + title + "*\nㅤ\n")
                .addField("Nombre:", "*" + StringUtil.comentarDiscord2(getName()) + "*", true)
                .addField("Cantidad restante:", "*" + StringUtil.comentarDiscord2(limit == -1 ? "Infinito" : "" + limit) + "*", true)
                .addField("ID:", "*" + StringUtil.comentarDiscord2("" + getUuid()) + "*", false)
                .addBlankField(false)
                .setThumbnail("https://i.ibb.co/PmTKp4C/icons8-facebook-like-1080px.png")
                .setColor(Color.gray)
                .setTimestamp(TimeUtil.getCalendar(new Date()).toInstant())
                .setFooter("Announcement by JuanC's Licences", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
        return embedBuilder.build();
    }

}
