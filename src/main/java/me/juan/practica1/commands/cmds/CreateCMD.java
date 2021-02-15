package me.juan.practica1.commands.cmds;

import me.juan.core.utils.JavaUtils;
import me.juan.core.utils.StringUtil;
import me.juan.core.utils.TimeUtil;
import me.juan.practica1.commands.Command;
import me.juan.practica1.heritage.Buyer;
import me.juan.practica1.heritage.Licence;
import me.juan.practica1.heritage.Product;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CreateCMD extends Command {

    public CreateCMD() {
        super("create", true);
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
                if (args.length == 2) {
                    err("Commando invalido, !CREATE USER <NOMBRE>");
                    return;
                }
                String subcommand_2 = args[2];
                try {
                    getChannel().sendMessage(new Buyer(subcommand_2).usuarioGenerado("Usuario creado!")).complete();
                } catch (Exception exception) {
                    err(exception.getMessage());
                }
                return;
            case "p":
            case "products":
            case "product":
                if (args.length == 2) {
                    err("Commando invalido, !CREATE PRODUCT <NOMBRE> <LIMITE(-1 = ∞)>");
                    return;
                }
                subcommand_2 = args[2];
                String subcommand_3 = (args.length == 4 ? args[3] : "" + -1);
                Integer i = JavaUtils.tryParseInteger(subcommand_3);
                if (i == null) {
                    err("El limite debe ser un numero");
                    return;
                }
                try {
                    getChannel().sendMessage(new Product(subcommand_2, i).productoGenerado("Producto creado!")).complete();
                } catch (Exception exception) {
                    err(exception.getMessage());
                }
                return;
            case "l":
            case "licences":
            case "licence":
                if (args.length == 3) {
                    err("Commando invalido, !CREATE LICENCE <USER> <PRODUCT>");
                    return;
                }
                subcommand_2 = args[2];
                subcommand_3 = args[3];
                try {
                    MessageEmbed licencia = new Licence(subcommand_2, subcommand_3).getInfo("Licencia generada!");
                    getChannel().sendMessage("Generando licencia...").queue(response /* => Message */ -> {
                        response.editMessage(licencia).queueAfter(2, TimeUnit.SECONDS);
                    });
                } catch (Exception exception) {
                    err(exception.getMessage());
                }
                return;
        }
        help();
    }


    private void help() {
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("**Generador de licencias** \nㅤ\n*Comando 'Create':*\nㅤ\n")
                .addField(StringUtil.comentarDiscord2("  *  !CREATE USER <NOMBRE>"), "      ⇝ Crea un usuario para añadirle una licencia.", false)
                .addBlankField(false)
                .addField(StringUtil.comentarDiscord2("  *  !CREATE PRODUCT <NOMBRE> <LIMITE(-1 = ∞)>"), "      ⇝ Crear un producto.", false)
                .addBlankField(false)
                .addField(StringUtil.comentarDiscord2("  *  !CREATE LICENCE <USER> <PRODUCT>"), "      ⇝ Generar una licencia.", false)
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
