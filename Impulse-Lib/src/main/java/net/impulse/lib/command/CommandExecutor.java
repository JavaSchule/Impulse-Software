package net.impulse.lib.command;

public interface CommandExecutor{

    void execute( String[] cmd );

    /**
     * Get a short description of the command
     * @return
     */
    String getUsage();
}
