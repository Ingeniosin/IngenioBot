package me.juan.practica1.commands.cmds;


import me.juan.practica1.commands.Command;

public class EndCMD extends Command {

    public EndCMD(boolean op) {
        super("end", op);
    }

    @Override
    public void run(String[] args) {
        log("Good bye.");
        System.exit(0);
    }
}
