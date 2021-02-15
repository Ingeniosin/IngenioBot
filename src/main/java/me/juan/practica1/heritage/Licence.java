package me.juan.practica1.heritage;

import lombok.Getter;
import me.juan.core.utils.StringUtil;
import me.juan.core.utils.TimeUtil;
import me.juan.practica1.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;


@Getter
public class Licence {


    @Getter
    private static final HashMap<UUID, Licence> uuidLicenceHashMap = new HashMap<>();

    private final UUID key;

    private final UUID buyerUuid;
    private final UUID productUuid;
    private final ArrayList<String> ipsRegistered = new ArrayList<>();
    private final Calendar creationTime;
    private Calendar expireTime;

    public Licence(UUID buyerUuid, UUID productUuid) {
        this.buyerUuid = buyerUuid;
        this.productUuid = productUuid;
        key = UUID.randomUUID();
        creationTime = TimeUtil.getCalendar(new Date());
        uuidLicenceHashMap.put(getKey(), this);
    }

    public Licence(Buyer buyer, Product product) {
        this(buyer.getUuid(), product.getUuid());
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
        return Buyer.getUuidBuyerHashMap().getOrDefault(buyerUuid, null);
    }

    public Product getProduct() {
        return Product.getProductHashMapUuid().getOrDefault(productUuid, null);
    }

    public MessageBuilder getInfo(String title) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE d 'de' MMMM 'a las' hh:mm a");
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle(title)
                .setDescription("\n-\n*Esta licencia esta bajo derechos de autor, cualquier uso indebido sera sancionado con la cancelación automática de la misma, su uso es único y es respaldado por los términos y condiciones.*\n-\n")

                .addField("Dueño:", StringUtil.comentarDiscord2(getBuyer().getName()), true)
                .addField("Producto:", StringUtil.comentarDiscord2(getProduct().getName()), true)
                .addField("Tiempo restante:", StringUtil.comentarDiscord2((isExpires() ? (isExpired() ? "Expirada" : "" + TimeUtil.millisToRoundedTime(getDiff())) : "Permanente")), true)
                .addField("Creada el:", "" + StringUtil.comentarDiscord2(StringUtil.upperCaseFirst(dateFormatter.format(creationTime.getTime()))), false)
                .addField("Key:", StringUtil.comentarDiscord2(getKey().toString()), false)
                .addBlankField(false)
                .setThumbnail(Main.getJda().getSelfUser().getEffectiveAvatarUrl())
                .setColor(Color.gray)
                .setTimestamp(TimeUtil.getCalendar(new Date()).toInstant())
                .setFooter("Announcement by Ingenio's Licences", "https://cdn.discordapp.com/avatars/300717914791739393/71a1b3984688f7ad11487e46eaed8b5f.png");
        return new MessageBuilder().setEmbed(embedBuilder.build());
    }


}
