package net.impulse.lib.logging;

import lombok.Getter;
import org.apache.log4j.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class LoggingProvider{

    private final Logger logger;
    @Getter
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd" ){{
        this.setTimeZone( TimeZone.getTimeZone( "Europe/Berlin" ) );
    }};

    public LoggingProvider(){
        logger = Logger.getRootLogger();
        try{
            PatternLayout layout = new PatternLayout( "[%d{yyyy-MM-dd HH:mm:ss}] [%p]: %m%n" );
            ConsoleAppender consoleAppender = new ConsoleAppender( layout );
            logger.addAppender( consoleAppender );
            Date date = new Date( System.currentTimeMillis() );
            DailyRollingFileAppender fileAppender =
                    new DailyRollingFileAppender( layout, "logs/" + getSimpleDateFormat().format( date ) + ".log", "'.'yyyy-MM-dd_HH-mm" );
            this.logger.addAppender( fileAppender );
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void log( String output ){
        this.logger.info( output );
    }

    public void setPrority( Level level ){
        this.logger.setLevel( level );
    }

}
