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
//    public HandlerRegistry handlerRegistry(CommandsHandler commands, EventsHandler events, QueriesHandler queries) {
    public HandlerRegistry handlerRegistry(CommandsHandler commands, EventsHandler events) {
        return HandlerRegistry.register()
                .listenNotificationEvent("some.broadcast.event.name", events::handleEventA, Object.class)
                .listenEvent("some.event.name", events::handleEventA, Object.class)
                .handleCommand("some.command.name", commands::handleCommandA, Object.class);
//                .handleCommand("push", "some.command.names", commands::handleCommandA, Object.class)
//                .serveQuery("some.query", queries::handleQueryB, MessagePush.class);
    }
}
