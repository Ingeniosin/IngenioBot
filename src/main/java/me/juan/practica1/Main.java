package me.juan.practica1;

import lombok.Getter;
import me.juan.core.database.MongoDB;
import me.juan.practica1.commands.CommandManager;
import me.juan.practica1.heritage.Buyer;
import me.juan.practica1.heritage.Licence;
import me.juan.practica1.heritage.Product;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {

    @Getter
    private static JDA jda;

    private static String UrlDB = "mongodb://127.0.0.1:27017/";


    public static void main(String[] strings) {
        long time = System.currentTimeMillis();
        if (!initDB()) return;
        if (!initDiscord()) return;
        System.out.println("Started in " + (System.currentTimeMillis() - time) + "ms.");
        System.out.println(" ");
    }


    public static boolean initDiscord() {
        try {
            jda = JDABuilder.createDefault("ODA4ODM5ODgxMzcxMDI1NDU4.YCMY1w.nJtQPgZpJHtz6uOBi90-nYQte9s").build();
            jda.awaitReady();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        jda.addEventListener(new CommandManager());
        return true;
    }

    public static boolean initDB() {
        MongoDB mongoDB = new MongoDB(UrlDB);
        if (mongoDB.isConnected()) {
            Licence.mongoDatabase = mongoDB.getDatabase("LicenceDB").setDefaultCollection("Licences");
            Product.mongoDatabase = mongoDB.getDatabase("LicenceDB").setDefaultCollection("Products");
            Buyer.mongoDatabase = mongoDB.getDatabase("LicenceDB").setDefaultCollection("Buyers");
        } else {
            System.out.println("No-Connected");
            return false;
        }
        return true;
    }
}
