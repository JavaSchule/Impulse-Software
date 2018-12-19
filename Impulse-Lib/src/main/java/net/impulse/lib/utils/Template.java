package net.impulse.lib.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Getter
public class Template{
    private final String name;
    private final String template;
    private final int onlineSize;

    public class TemplateHandler{

        private final List<Integer> ports = new ArrayList<>();

        public Integer getFreePort(){
            for ( int i = 50000; i < 65535; i++ ){
                if ( !ports.contains( i ) ){
                    ports.add( i );
                    return i;
                }
            }
            return -1;
        }
    }

}
