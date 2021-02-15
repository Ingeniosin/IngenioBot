package me.juan.practica1.commands.cmds;

import me.juan.core.utils.JavaUtils;
import me.juan.core.utils.StringUtil;
import me.juan.core.utils.TimeUtil;
import me.juan.practica1.Main;
import me.juan.practica1.commands.Command;
import me.juan.practica1.heritage.Buyer;
import me.juan.practica1.heritage.Licence;
import me.juan.practica1.heritage.Product;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.Date;

public class CreateCMD extends Command {

    public CreateCMD() {
        super("create", true);
    }

    @Override
    public void run(String[] args) {
        if (args.length == 1) {
            err("Usa !create help, si necesitas ayuda.");
            return;
        }
        String subcommand_1 = args[1];
        switch (subcommand_1.toLowerCase()) {
            case "help":
                help();
                return;
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
            case "product":
                if (args.length == 3) {
                    err("Commando invalido, !CREATE PRODUCT <NOMBRE> <LIMITE(-1 = ∞)>\"");
                    return;
                }
                subcommand_2 = args[2];
                String subcommand_3 = args[3];
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
            case "licence":
                if (args.length == 3) {
                    err("Commando invalido, !CREATE LICENCE <USER> <PRODUCT>\"");
                    return;
                }
                subcommand_2 = args[2];
                subcommand_3 = args[3];
                try {
                    getChannel().sendMessage(new Licence(subcommand_2, subcommand_3).getInfo("Licencia generada!")).complete();
                } catch (Exception exception) {
                    err(exception.getMessage());
                }
                return;
        }
        err("Usa !create help, si necesitas ayuda.");
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
                .setFooter("Announcement by JuanC's Licences", Main.getJda().getSelfUser().getEffectiveAvatarUrl());
        getChannel().sendMessage(embedBuilder.build()).complete();
    }

    private void err(String string) {
        log(StringUtil.comentarDiscord2("Ocurrió un error: " + string));
    }

}
