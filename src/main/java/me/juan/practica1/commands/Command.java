package me.juan.practica1.commands;

import lombok.Getter;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {

    @Getter
    private static final List<Command> commandList = new ArrayList<>();
    @Getter
    private final String cmd;
    @Getter
    private MessageChannel channel;
    @Getter
    private boolean op;

    public Command(String cmd, boolean op) {
        this.cmd = cmd;
        this.op = op;
        commandList.add(this);
    }

    public void log(String msg) {
        channel.sendMessage("[" + cmd + "] " + msg).queue();
    }

    public Command setChannel(MessageChannel channel) {
        this.channel = channel;
        return this;
    }

    public abstract void run(String[] args);

}
