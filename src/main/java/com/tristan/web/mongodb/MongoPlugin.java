package com.tristan.web.mongodb;

import com.jfinal.log.Log;
import com.jfinal.plugin.IPlugin;
import com.mongodb.MongoClient;

/**
 */

public class MongoPlugin implements IPlugin {

    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAUL_PORT = 27017;

    protected final Log logger = Log.getLog(getClass());

    private MongoClient client;
    private String host;
    private int port;
    private String database;

    public MongoPlugin(String database) {
        this.host = DEFAULT_HOST;
        this.port = DEFAUL_PORT;
        this.database = database;
    }

    public MongoPlugin(String host, int port, String database) {
        this.host = host;
        this.port = port;
        this.database = database;
    }

    @Override
    public boolean start() {

        try {
            client = new MongoClient(host, port);
        } catch (Exception e) {
            throw new RuntimeException("can't connect mongodb, please check the host and port:" + host + "," + port, e);
        }

        MongoKit.init(client, database);
        return true;
    }

    @Override
    public boolean stop() {
        if (client != null) {
            client.close();
        }
        return true;
    }

}
