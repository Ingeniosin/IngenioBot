package me.juan.practica1.commands.cmds;


import me.juan.practica1.Main;
import me.juan.practica1.commands.Command;

public class EndCMD extends Command {

    public EndCMD(boolean op) {
        super("end", op);
    }

    @Override
    public void run(String[] args) {
        log("Good bye.");
        Main.space();
        System.exit(0);
    }
}
