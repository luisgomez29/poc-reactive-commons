package co.com.bancolombia.events;

import co.com.bancolombia.events.handlers.CommandsHandler;
import co.com.bancolombia.events.handlers.EventsHandler;
import co.com.bancolombia.events.handlers.QueriesHandler;
import co.com.bancolombia.model.push.MessagePush;
import org.reactivecommons.async.api.HandlerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HandlerRegistryConfiguration {

    @Bean
    public HandlerRegistry handlerRegistry(CommandsHandler commands, EventsHandler events, QueriesHandler queries) {
        return HandlerRegistry.register()
                .listenEvent("event.push", events::handleEventA, MessagePush.class)
                .handleCommand("command.push", commands::handleCommandA, MessagePush.class)
                .handleCommand("command.push.error", commands::handleCommandError, MessagePush.class)
                .serveQuery("query.push", queries::handleQueryA, MessagePush.class);
    }
}
