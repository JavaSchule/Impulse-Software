package net.impulse.daemon.handler;

import com.google.common.collect.Lists;
import com.mongodb.client.model.Filters;
import lombok.Getter;
import net.impulse.daemon.ImpulseDaemon;
import net.impulse.lib.utils.Template;
import org.bson.Document;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Mike
 * @version 0.1
 */
public class TemplateHandler{

    @Getter
    private final List<Template> templateList = Lists.newArrayList();

    /**
     * Get a template document from database
     *
     * @param name
     * @param template
     */
    public void getTemplate( final String name, Consumer<Document> template ){
        ImpulseDaemon.instance.getMongoConnection().getCollection( "templates" ).find( Filters.eq( "name", name ) ).first( ( document, throwable ) -> template.accept( document ) );
    }

    /**
     * Read all templates from database and cache it into a hashmap
     */
    public void loadTemplates(){
        ImpulseDaemon.instance.getMongoConnection().getCollection( "templates" ).find().forEach( document -> {
            Template template = new Template( document.getString( "name" ), document.getString( "template" ), document.getInteger( "onlineSize" ) );
            templateList.add( template );
            ImpulseDaemon.instance.getLoggingProvider().log( String.format( "Service [name='%s', template='%s', onlinesize='%s'] succesfully cached!", template.getName(), template.getTemplate(), template.getOnlineSize() ) );
        }, ( aVoid, throwable ) -> {
        } );
    }
}
