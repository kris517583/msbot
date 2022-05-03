// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.bot.sample.echo;

import com.microsoft.bot.builder.Bot;
import com.microsoft.bot.integration.AdapterWithErrorHandler;
import com.microsoft.bot.integration.BotFrameworkHttpAdapter;
import com.microsoft.bot.integration.Configuration;
import com.microsoft.bot.integration.spring.BotController;
import com.microsoft.bot.integration.spring.BotDependencyConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.Enumeration;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//
// This is the starting point of the Sprint Boot Bot application.
//
@SpringBootApplication

// Use the default BotController to receive incoming Channel messages. A custom
// controller could be used by eliminating this import and creating a new
// org.springframework.web.bind.annotation.RestController.
// The default controller is created by the Spring Boot container using
// dependency injection. The default route is /api/messages.
@Import({BotController.class})

/**
 * This class extends the BotDependencyConfiguration which provides the default
 * implementations for a Bot application.  The Application class should
 * override methods in order to provide custom implementations.
 */
@Slf4j
public class Application extends BotDependencyConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("Application started!!! ");
       /* Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
        	logger.info("name: " +envName);
        	logger.info("value: " +env.get(envName));
        	
        }*/
    }

    /**
     * Returns the Bot for this application.
     *
     * <p>
     *     The @Component annotation could be used on the Bot class instead of this method
     *     with the @Bean annotation.
     * </p>
     *
     * @return The Bot implementation for this application.
     */
    @Bean
    public Bot getBot() {
        return new EchoBot();
    }

    /**
     * Returns a custom Adapter that provides error handling.
     *
     * @param configuration The Configuration object to use.
     * @return An error handling BotFrameworkHttpAdapter.
     */
    @Override
    public BotFrameworkHttpAdapter getBotFrameworkHttpAdaptor(Configuration configuration) {
    	
    	Enumeration<String> enums = (Enumeration<String>) configuration.getProperties().propertyNames();
        while (enums.hasMoreElements()) {
          String key = enums.nextElement();
          configuration.getProperties().setProperty("MicrosoftAppId", (System.getenv().get("MicrosoftAppID") !=null? System.getenv().get("MicrosoftAppID"): ""));
          configuration.getProperties().setProperty("MicrosoftAppPassword", (System.getenv().get("MicrosoftAppPWD") !=null? System.getenv().get("MicrosoftAppPWD"): ""));
        }
        
        enums = (Enumeration<String>) configuration.getProperties().propertyNames();
        while (enums.hasMoreElements()) {
          String key = enums.nextElement();
          String value = configuration.getProperty(key);
          log.info(key + " : " + value);
        }
        
        return new AdapterWithErrorHandler(configuration);
    }
}
