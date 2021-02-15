package me.juan.practica1.commands.cmds;

import com.google.gson.Gson;
import me.juan.core.utils.StringUtil;
import me.juan.core.utils.TimeUtil;
import me.juan.practica1.commands.Command;
import me.juan.practica1.heritage.Buyer;
import me.juan.practica1.heritage.Licence;
import me.juan.practica1.heritage.Product;
import net.dv8tion.jda.api.EmbedBuilder;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class ListCMD extends Command {

    public ListCMD() {
        super("list", true);
    }

    @Override
    public void run(String[] args) {
        if (args.length == 1) {
            help();
            return;
        }
        String subcommand_1 = args[1];
        switch (subcommand_1.toLowerCase()) {
            case "help":
                help();
                return;
            case "u":
            case "users":
            case "user":
                listUsers();
                return;
            case "p":
            case "products":
            case "product":
                listProducts();
                return;
            case "l":
            case "licences":
            case "licence":
                listLicences();
                return;
        }
        help();
    }


    private void listUsers() {
        ArrayList<Buyer> buyers = new ArrayList<>();
        for (Document document : Buyer.mongoDatabase.getDefCollection().find())
            buyers.add(new Gson().fromJson(document.toJson(), Buyer.class));
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("**Lista de usuarios**")
                .addField("Usuarios registrados:", "**"
                        + (StringUtil.comentarDiscord2(buyers.isEmpty() ? "Ningún usuario registrado." : "" +
                        "➭ " + StringUtils.join(buyers.stream().map(buyer -> buyer.getName() + " (" + buyer.getUuid() + ")")
                        .collect(Collectors.toList()), "\n➭ ")) + "**"), false)
                .addBlankField(false)
                .setThumbnail("https://i.ibb.co/CH3Px5q/icons8-general-ledger-1080px-1.png")
                .setColor(Color.gray)
                .setTimestamp(TimeUtil.getCalendar(new Date()).toInstant())
                .setFooter("Announcement by JuanC's Licences", "https://cdn.discordapp.com/avatars/300717914791739393/71a1b3984688f7ad11487e46eaed8b5f.png");
        getChannel().sendMessage(embedBuilder.build()).complete();
    }

    private void listProducts() {
        ArrayList<Product> products = new ArrayList<>();
        for (Document document : Product.mongoDatabase.getDefCollection().find())
            products.add(new Gson().fromJson(document.toJson(), Product.class));
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("**Lista de productos**")
                .addField("Productos registrados:", "**"
                        + (StringUtil.comentarDiscord2(products.isEmpty() ? "Ningún producto registrado." : "" +
                        "➭ " + StringUtils.join(products.stream().map(buyer -> buyer.getName() + " (" + buyer.getUuid() + ")")
                        .collect(Collectors.toList()), "\n➭ ")) + "**"), false)
                .addBlankField(false)
                .setThumbnail("https://i.ibb.co/CH3Px5q/icons8-general-ledger-1080px-1.png")
                .setColor(Color.gray)
                .setTimestamp(TimeUtil.getCalendar(new Date()).toInstant())
                .setFooter("Announcement by JuanC's Licences", "https://cdn.discordapp.com/avatars/300717914791739393/71a1b3984688f7ad11487e46eaed8b5f.png");
        getChannel().sendMessage(embedBuilder.build()).complete();
    }

    private void listLicences() {
        ArrayList<Licence> licences = new ArrayList<>();
        for (Document document : Licence.mongoDatabase.getDefCollection().find()) {
            licences.add(new Gson().fromJson(document.toJson(), Licence.class));
        }

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("**Lista de licencias**")
                .addField("Licencias generadas:", "**"
                        + (StringUtil.comentarDiscord2(licences.isEmpty() ? "Ninguna licencia generada." : "" +
                        "➭ " + StringUtils.join(licences.stream().map(Licence::getProductAndOwner)
                        .collect(Collectors.toList()), "\n➭ ")) + "**"), false)
                .addBlankField(false)
                .setThumbnail("https://i.ibb.co/CH3Px5q/icons8-general-ledger-1080px-1.png")
                .setColor(Color.gray)
                .setTimestamp(TimeUtil.getCalendar(new Date()).toInstant())
                .setFooter("Announcement by JuanC's Licences", "https://cdn.discordapp.com/avatars/300717914791739393/71a1b3984688f7ad11487e46eaed8b5f.png");
        getChannel().sendMessage(embedBuilder.build()).complete();
    }

    private void help() {
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("**Generador de licencias** \nㅤ\n*Comando 'Info':*\nㅤ\n")
                .addField(StringUtil.comentarDiscord2("  *  !LIST USERS"), "      ⇝ Lista de usuarios registrados.", false)
                .addBlankField(false)
                .addField(StringUtil.comentarDiscord2("  *  !LIST PRODUCTS"), "      ⇝ Lista de productos.", false)
                .addBlankField(false)
                .addField(StringUtil.comentarDiscord2("  *  !LIST LICENCES"), "      ⇝ Lista de licencias generadas.", false)
                .addBlankField(false)
                .setThumbnail("https://i.ibb.co/CH3Px5q/icons8-general-ledger-1080px-1.png")
                .setColor(Color.gray)
                .setTimestamp(TimeUtil.getCalendar(new Date()).toInstant())
                .setFooter("Announcement by JuanC's Licences", "https://cdn.discordapp.com/avatars/300717914791739393/71a1b3984688f7ad11487e46eaed8b5f.png");
        getChannel().sendMessage(embedBuilder.build()).complete();
    }


    private void err(String string) {
        log(StringUtil.comentarDiscord2("Ocurrió un error: " + string));
    }

}
