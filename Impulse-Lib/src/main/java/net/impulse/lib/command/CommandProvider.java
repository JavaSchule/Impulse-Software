package net.impulse.lib.command;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class CommandProvider{

    @Getter
    private final List<CommandExecutor> commandExecutors;


    public CommandProvider(){
        commandExecutors = new ArrayList<>();
    }

    public void registerCommand( CommandExecutor commandExecutor ){
        this.commandExecutors.add( commandExecutor );
    }

    public void runTask(ExecutorService executorService){
        executorService.execute( () -> {
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( System.in ) );
            String line;
            try{
                while ( ( line = bufferedReader.readLine() ) != null ){
                    if ( line.length() != 0 ){
                        for ( CommandExecutor command : commandExecutors ){
                            command.execute( line.split( " " ) );
                        }
                    }
                }
            } catch (Exception ex){
                ex.printStackTrace();
            }
        } );
    }
}