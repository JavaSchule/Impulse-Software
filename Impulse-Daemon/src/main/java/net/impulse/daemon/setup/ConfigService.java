package net.impulse.daemon.setup;

import com.google.gson.JsonObject;
import net.impulse.lib.document.GsonService;

import java.io.File;
import java.io.IOException;

/**
 * @author Mike
 */
public class ConfigService{

    private final File file = new File( "config.json" );

    /**
     * Create a new default config for the daemon service
     */
    public void createDefaultConfig(){

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty( "port", "30000" );
        jsonObject.addProperty( "host", "localhost" );
        jsonObject.addProperty( "mongodb_host", "localhost" );
        jsonObject.addProperty( "mongodb_port", "27017" );
        if ( !file.exists() )
            try{
                GsonService.save( file, jsonObject );
            } catch (IOException e){
                e.printStackTrace();
            }
    }

    /**
     * Create the template config file
     */
    /**
     * public void createTemplateConfig(){
     * List<JsonObject> list = new ArrayList<>();
     * this.templates.forEach( jsonElement -> list.add( jsonElement.getAsJsonObject() ) );
     * final List<String> onlineList = list.stream().map( object -> object.get( "online" ).getAsString() ).collect( Collectors.toList() );
     * list.forEach( object -> {
     * final String online = object.get( "online" ).getAsString();
     * final String template = object.get( "template" ).getAsString();
     * } );
     * }
     **/

    public Integer getPort() throws IOException{
        JsonObject mainObject = GsonService.loadAsJsonObject( this.file );
        return mainObject.getAsJsonPrimitive( "port" ).getAsInt();
    }

    public String getHost() throws IOException{
        JsonObject mainObject = GsonService.loadAsJsonObject( this.file );
        return mainObject.getAsJsonPrimitive( "host" ).getAsString();
    }

    public Integer getMongoDBPort() throws IOException{
        JsonObject mainObject = GsonService.loadAsJsonObject( this.file );
        return mainObject.getAsJsonPrimitive( "mongodb_port" ).getAsInt();
    }

    public String getMongoDBHost() throws IOException{
        JsonObject mainObject = GsonService.loadAsJsonObject( this.file );
        return mainObject.getAsJsonPrimitive( "mongodb_host" ).getAsString();
    }
}
