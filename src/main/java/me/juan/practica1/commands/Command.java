package me.juan.practica1.commands;

import lombok.Getter;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Command {

    @Getter
    private static final List<Command> commandList = new ArrayList<>();
    private final String cmd;
    private final boolean op;
    private MessageChannel channel;
    private Message message;

    public Command(String cmd, boolean op) {
        this.cmd = cmd;
        this.op = op;
        commandList.add(this);
    }

    public Command setMessage(Message message) {
        this.message = message;
        return this;
    }

    public void log(String msg) {
        channel.sendMessage(msg).queue();
    }

    public Command setChannel(MessageChannel channel) {
        this.channel = channel;
        return this;
    }

    public abstract void run(String[] args);

}
