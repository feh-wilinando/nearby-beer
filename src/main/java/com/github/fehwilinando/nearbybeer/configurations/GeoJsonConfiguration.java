package com.github.fehwilinando.nearbybeer.configurations;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.databind.Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeoJsonConfiguration {


    @Bean
    public Module jtsModule(){
        return new JtsModule();
    }

}
