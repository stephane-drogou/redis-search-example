package org.acme.data;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.inject.Singleton;

import java.util.function.BiConsumer;

import static io.smallrye.config.ConfigLogging.log;

@Singleton
public class RedisNotification {

    private final PubSubCommands<String> commands;

    public RedisNotification(RedisDataSource ds) {
        commands = ds.pubsub(String.class);
    }

    public void OnMessage(String keyspace, String commande) {
        String key = keyspace.substring(15);
        log.info("On a reçu une notification sur la clef :");
        log.info(key);
    }

    @RunOnVirtualThread
    public void subscribe (String key) {
        String pattern = "*__:"+key;
        BiConsumer<String, String> onmessage = this::OnMessage;
        commands.subscribeToPattern(pattern, onmessage);
    }


}
