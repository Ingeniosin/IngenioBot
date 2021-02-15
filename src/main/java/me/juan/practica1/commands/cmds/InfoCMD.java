package me.juan.practica1.commands.cmds;

import me.juan.core.utils.StringUtil;
import me.juan.core.utils.TimeUtil;
import me.juan.practica1.commands.Command;
import me.juan.practica1.heritage.Buyer;
import me.juan.practica1.heritage.Licence;
import me.juan.practica1.heritage.Product;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.Date;

public class InfoCMD extends Command {

    public InfoCMD() {
        super("info", true);
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
                    err("Commando invalido, !INFO USER <NOMBRE>");
                    return;
                }
                String subcommand_2 = args[2];

                Buyer buyer = Buyer.get(subcommand_2, false);
                if (buyer == null) {
                    err("Este usuario no existe.");
                    return;
                }
                getChannel().sendMessage(buyer.usuarioGenerado("Información de '" + buyer.getName() + "':")).complete();

                return;
            case "p":
            case "products":
            case "product":
                if (args.length == 2) {
                    err("Commando invalido, !INFO PRODUCT <NOMBRE>");
                    return;
                }
                subcommand_2 = args[2];

                Product product = Product.get(subcommand_2, false);
                if (product == null) {
                    err("Este producto no existe.");
                    return;
                }
                getChannel().sendMessage(product.productoGenerado("Información de producto:")).complete();

                return;
            case "l":
            case "licences":
            case "licence":
                if (args.length == 2) {
                    err("Commando invalido, !INFO LICENCE <KEY>");
                    return;
                }
                subcommand_2 = args[2];
                Licence licence = Licence.get(subcommand_2, true);
                if (licence == null) {
                    err("Esta licencia no existe.");
                    return;
                }
                getChannel().sendMessage(licence.getInfo("Información de licencia:")).complete();

                return;
        }
        help();
    }


    private void help() {
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("**Generador de licencias** \nㅤ\n*Comando 'Info':*\nㅤ\n")
                .addField(StringUtil.comentarDiscord2("  *  !INFO USER <NOMBRE>"), "      ⇝ Información de un usuario.", false)
                .addBlankField(false)
                .addField(StringUtil.comentarDiscord2("  *  !INFO PRODUCT <NOMBRE>"), "      ⇝ Información de un producto.", false)
                .addBlankField(false)
                .addField(StringUtil.comentarDiscord2("  *  !INFO LICENCE <KEY>"), "      ⇝ Información de una licencia.", false)
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
