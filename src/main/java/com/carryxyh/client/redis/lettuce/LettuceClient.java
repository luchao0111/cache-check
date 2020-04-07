package com.carryxyh.client.redis.lettuce;

import com.carryxyh.client.ClientConfig;
import com.carryxyh.client.redis.AbstractRedisCacheClient;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;

/**
 * LettuceClient
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-07
 */
public final class LettuceClient extends AbstractRedisCacheClient {

    private RedisClusterClient redisClusterClient;

    private StatefulRedisClusterConnection<String, String> connection;

    private RedisAdvancedClusterCommands<String, String> commands;

    protected LettuceClient(ClientConfig clientConfig) {
        super(clientConfig);
    }

    @Override
    protected String doGet(String key) {
        return commands.get(key);
    }

    @Override
    protected void doInit() throws Exception {
        String host = clientConfig.getHost();
        int port = clientConfig.getPort();
        String password = clientConfig.getPassword();

        this.redisClusterClient = RedisClusterClient.create("redis://" +
                password + "@" + host + ":" + port);
    }

    @Override
    protected void doStart() throws Exception {
        this.connection = redisClusterClient.connect();
        this.commands = connection.sync();
    }

    @Override
    protected void doStop() throws Exception {
        this.connection.close();
        this.connection = null;
        this.commands = null;
    }

    @Override
    protected void doClose() throws Exception {
        this.redisClusterClient.shutdown();
        this.redisClusterClient = null;
    }

}
