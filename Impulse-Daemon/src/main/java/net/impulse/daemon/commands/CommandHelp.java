package net.impulse.daemon.commands;

import net.impulse.daemon.ImpulseDaemon;
import net.impulse.lib.command.CommandExecutor;

/**
 * @author Mike
 * Command to print the descriptions
 */
public class CommandHelp implements CommandExecutor{
    @Override
    public void execute( String[] cmd ){
        if ( cmd[ 0 ].equalsIgnoreCase( "help" ) ){
            ImpulseDaemon.instance.getCommandProvider().getCommandExecutors().forEach( commandExecutor -> ImpulseDaemon.instance.getLoggingProvider().log( commandExecutor.getUsage() ) );
        }
    }

    /**
     * Get a short description of the command
     * @return
     */
    @Override
    public String getUsage(){
        return "Help - Show all current commands";
    }
}
