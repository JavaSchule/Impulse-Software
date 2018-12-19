package net.byteexception.impulse.commands;

import net.byteexception.impulse.ImpulseWrapper;
import net.impulse.lib.command.CommandExecutor;

public class CommandStop implements CommandExecutor{
    public void execute( String[] cmd ){
        final ImpulseWrapper impulseWrapper = new ImpulseWrapper();
        impulseWrapper.stop();
    }

    public String getUsage(){
        return null;
    }
}
