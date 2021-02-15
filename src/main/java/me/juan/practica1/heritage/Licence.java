package me.juan.practica1.heritage;

import com.mongodb.client.model.Filters;
import lombok.Getter;
import me.juan.core.database.MongoDB;
import me.juan.core.utils.StringUtil;
import me.juan.core.utils.TimeUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


@Getter
public class Licence {

    public static MongoDB.MongoDatabase mongoDatabase;

    private final UUID key;

    private final UUID buyerUuid;
    private final UUID productUuid;
    private final ArrayList<String> ipsRegistered = new ArrayList<>();
    private final Calendar creationTime;
    private Calendar expireTime;

    public Licence(UUID buyerUuid, UUID productUuid) throws Exception {
        this(Buyer.get(buyerUuid, true), Product.get(productUuid, true));
    }

    public Licence(String buyerName, String productName) throws Exception {
        this(Buyer.get(buyerName, false), Product.get(productName, false));
    }

    public Licence(Buyer buyer, Product product) throws Exception {
        if (product == null) throw new Exception("El producto no existe.");
        if (buyer == null) throw new Exception("El comprador no existe.");
        this.buyerUuid = buyer.getUuid();
        this.productUuid = product.getUuid();
        key = UUID.randomUUID();
        creationTime = TimeUtil.getCalendar(new Date());
        mongoDatabase.add(null, this);
        buyer.addLicence(key);
    }


    public static boolean exist(Object id, boolean isUuid) {
        return mongoDatabase.exist(mongoDatabase.getDefCollection().find(Filters.eq(isUuid ? "uuid" : "name", id.toString())));
    }

    public static Licence get(Object id, boolean isUuid) {
        return mongoDatabase.get(mongoDatabase.getDefCollection().find(Filters.eq(isUuid ? "uuid" : "name", id.toString())), Licence.class);
    }


    private boolean isExpires() {
        return this.expireTime != null;
    }

    public Licence setExpires(Date date) {
        this.expireTime = TimeUtil.getCalendar(date);
        return this;
    }

    private boolean isExpired() {
        return getDiff() <= 0;
    }

    public long getDiff() {
        return expireTime.getTimeInMillis() - creationTime.getTimeInMillis();
    }

    public Buyer getBuyer() {
        return Buyer.get(getBuyerUuid(), true);
    }

    public Product getProduct() {
        return Product.get(getProductUuid(), true);
    }

    public MessageEmbed getInfo(String title) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE d 'de' MMMM 'a las' hh:mm a");
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle(title)
                .setDescription("\n-\n*Esta licencia esta bajo derechos de autor, cualquier uso indebido sera sancionado con la cancelación automática de la misma, su uso es único y esta respaldado por los términos y condiciones.*\n-\n")
                .addField("Dueño:", StringUtil.comentarDiscord2(getBuyer().getName()), true)
                .addField("Producto:", StringUtil.comentarDiscord2(getProduct().getName()), true)
                .addField("Tiempo restante:", StringUtil.comentarDiscord2((isExpires() ? (isExpired() ? "Expirada" : "" + TimeUtil.millisToRoundedTime(getDiff())) : "Permanente")), true)
                .addField("Creada el:", "" + StringUtil.comentarDiscord2(StringUtil.upperCaseFirst(dateFormatter.format(creationTime.getTime()))), false)
                .addField("Key:", StringUtil.comentarDiscord2(getKey().toString()), false)
                .addBlankField(false)
                .setThumbnail("https://i.ibb.co/xMDX27s/icons8-key-1080px-1.png")
                .setColor(Color.gray)
                .setTimestamp(TimeUtil.getCalendar(new Date()).toInstant())
                .setFooter("Announcement by Ingenio's Licences", "https://cdn.discordapp.com/avatars/300717914791739393/71a1b3984688f7ad11487e46eaed8b5f.png");
        return embedBuilder.build();
    }


}
