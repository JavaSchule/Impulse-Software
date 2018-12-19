package net.impulse.daemon.commands;

import net.impulse.daemon.ImpulseDaemon;
import net.impulse.lib.command.CommandExecutor;

/**
 * @author Mike
 * Command to clear the currrent screen
 */
public class CommandClear implements CommandExecutor{
    @Override
    public void execute( String[] cmd ){
        if ( cmd[0].equalsIgnoreCase( "clear" ) ){
            ImpulseDaemon.instance.getScreenHandler().clearScreen();
        }
    }

    /**
     * Get a short description of the command
     * @return
     */
    @Override
    public String getUsage(){
        return "Clear - Clear the current screen";
    }
}
