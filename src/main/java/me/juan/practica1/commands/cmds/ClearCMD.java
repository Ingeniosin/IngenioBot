package me.juan.practica1.commands.cmds;

import me.juan.practica1.commands.Command;
import net.dv8tion.jda.api.entities.Message;

import java.util.List;


public class ClearCMD extends Command {

    public ClearCMD() {
        super("clear", true);
    }

    @Override
    public void run(String[] args) {

        List<Message> messages = getChannel().getHistory().retrievePast(100).complete();
        new Thread(() -> {
            for (Message message : messages) {
                message.delete().complete();
            }
        }).start();

    }
}
