package me.juan.practica1.commands;


import me.juan.practica1.Main;
import me.juan.practica1.commands.cmds.ClearCMD;
import me.juan.practica1.commands.cmds.EndCMD;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandManager extends ListenerAdapter {


    public CommandManager() {
        initEssentials();
    }

    private void initEssentials() {
        new ClearCMD();
        new EndCMD(false);
    }

    public void log(String msg, MessageChannel channel) {
        channel.sendMessage("[commands] " + msg).queue();
    }


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        if (!msg.getContentRaw().startsWith("!")) return;
        if (msg.getAuthor().isBot()) return;
        MessageChannel channel = event.getChannel();
        new Thread(() -> {
            String[] args = msg.getContentRaw().split(" ");
            args[0] = args[0].replace("!", "");
            for (Command cmd : Command.getCommandList()) {
                if (cmd.getCmd().equalsIgnoreCase(args[0])) {
                    Main.space();
                    cmd.setChannel(channel).run(args);
                    Main.space();
                    return;
                }
            }
            if (args[0].equalsIgnoreCase("help")) {
                log("Commands avaliable:", channel);
                log(" ", channel);
                for (Command cmd : Command.getCommandList()) {
                    log("  *  " + cmd.getCmd(), channel);
                }
                return;
            }
            log("Command not found. Use !help", channel);
        }).start();


    }


}
