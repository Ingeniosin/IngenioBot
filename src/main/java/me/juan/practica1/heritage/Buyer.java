package me.juan.practica1.heritage;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import lombok.Getter;
import me.juan.core.database.MongoDB;
import me.juan.core.utils.StringUtil;
import me.juan.core.utils.TimeUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class Buyer {

    public static MongoDB.MongoDatabase mongoDatabase;

    private final String name;
    private final UUID uuid;
    private final Calendar creationTime;
    private final ArrayList<UUID> licences = new ArrayList<>();

    public Buyer(String name) throws Exception {
        if (exist(name, false))
            throw new Exception("Este comprador ya esta registrado.");
        this.name = name;
        this.uuid = UUID.randomUUID();
        creationTime = TimeUtil.getCalendar(new Date());
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
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE d 'de' MMMM 'a las' hh:mm a");
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("**" + title + "**\nㅤ\n")
                .addField("Nombre:", "**" + StringUtil.comentarDiscord2(getName()) + "**", true)
                .addField("Creado el:", "" + StringUtil.comentarDiscord2(StringUtil.upperCaseFirst(dateFormatter.format(creationTime.getTime()))), true)
                .addField("Compras:", "**"
                        + (StringUtil.comentarDiscord2(getLicences().isEmpty() ? "Ninguna." : "" +
                        "➭ " + StringUtils.join(getLicences().stream().map(uuid1 -> Licence.get(uuid1, true).getKeyAndName().replace(":", " [") + "]")
                        .collect(Collectors.toList()), "\n➭ ")) + "**"), false)
                .addField("ID:", "**" + StringUtil.comentarDiscord2("" + getUuid()) + "**", false)
                .addBlankField(false)
                .setThumbnail("https://i.ibb.co/PmTKp4C/icons8-facebook-like-1080px.png")
                .setColor(Color.ORANGE)
                .setTimestamp(TimeUtil.getCalendar(new Date()).toInstant())
                .setFooter("Announcement by JuanC's Licences", "https://cdn.discordapp.com/avatars/300717914791739393/71a1b3984688f7ad11487e46eaed8b5f.png");
        return embedBuilder.build();
    }

}
