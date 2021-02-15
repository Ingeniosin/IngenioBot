package me.juan.practica1;

import lombok.Getter;
import me.juan.core.database.MongoDB;
import me.juan.practica1.commands.CommandManager;
import me.juan.practica1.objects.Licence;
import me.juan.practica1.objects.ObjectMethod;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.security.auth.login.LoginException;
import java.util.UUID;

public class Main {

    @Getter
    private static JDA jda;

    @Getter
    private static MongoDB.MongoDatabase mongoDatabase;
    private static MessageChannel messageChannel;
    private static String UrlDB = "mongodb://127.0.0.1:27017/";

    public static void log(String msg) {
        String PREFIX = "[Repesi] ";
        if (messageChannel == null) {
            System.out.println(PREFIX + msg);
            return;
        }
        messageChannel.sendMessage(PREFIX + msg).complete();
    }

    public static void lines() {
        System.out.println("----------------------------------");
    }

    public static void space() {
        System.out.println(" ");
    }


    public static void main(String[] strings) {
        long time = System.currentTimeMillis();


        if (!initDB()) return;
        if (!initDiscord()) return;

        System.out.println("Started in " + (System.currentTimeMillis() - time) + "ms.");
        space();


    }


    public static boolean initDiscord()  {
        try {
            jda = JDABuilder.createDefault("ODA4ODM5ODgxMzcxMDI1NDU4.YCMY1w.nJtQPgZpJHtz6uOBi90-nYQte9s").build();
            jda.awaitReady();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        messageChannel = jda.getTextChannelById("810540860261269584");
        jda.addEventListener(new CommandManager());


        try {
          //  new Buyer("Juanelpro19");

      //      new Product("jUHC", -1);

            Licence licence = ObjectMethod.getLicence(UUID.fromString("b4869251-d02c-45e5-87ee-76da3e7c05b7"));

            messageChannel.sendMessage(licence.getInfo("Licencia generada!").build()).complete();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        // new Licence();

       /* Licence licence;
        try {
            Date date = new Date();
            date.setMinutes(date.getMinutes()+15);
            licence = new Licence(new Buyer("Juanelpro19"), Product.loadByName("jUHC")).setExpires(date);
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }


        messageChannel.sendMessage(licence.getInfo("Licencia generada!").build()).complete();*/
        return true;
    }




    public static boolean initDB() {
        MongoDB mongoDB = new MongoDB(UrlDB);
        if (mongoDB.isConnected())
            mongoDatabase = mongoDB.getDatabase("LicenceDB").setDefaultCollection("Licences");
        else {
            System.out.println("No-Connected");
            return false;
        }
        return true;
        /* if (mongoDatabase.exist(mongoDatabase.findSpecificDefault(new String[]{"name", "departamento"}, new Object[]{"Juan","Nariño"}))) {
            System.out.println("EXISTE");
        } else {
            System.out.println("NO EXISTE");
        }
        if (!mongoDatabase.exist(mongoDatabase.findDefault("name", "Juan")))
            mongoDatabase.add(null, new Empleado("Juan", "Nariño").setPersonalData("3147778511", "Barranquilla", new Date(2004, Calendar.MAY, 23)));

        if (mongoDatabase.exist(mongoDatabase.findSpecificDefault(new String[]{"name", "departamento"}, new Object[]{"Juan","Nariño"}))) {
            System.out.println("EXISTE");
        } else {
            System.out.println("NO EXISTE");
        }*/

    }
}
