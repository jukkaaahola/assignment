package com.aholafamily.assignment;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.net.httpserver.HttpServer;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Main class that works as the stand-alone HTTP-Server for the RESTful web services.
 * The server will be hosted by default at port 8080 on localhost.
 * This can be changed by providing the domain name and port number as command line arguments.
 *
 * Created by jukkaahola on 1/15/16.
 */
public class Main {
    private int portNumber = 8080;
    private String domainName = "localhost";

    public static void main(String[] args){
        Main m = null;
        if(args!=null && args.length>=2){
            try {
                m = new Main(args[0], Integer.parseInt(args[1]));
            } catch (Exception exc){
                exc.printStackTrace();
                return;
            }
        } else {
            m = new Main();
        }
        m.run();
    }

    public Main(){
        super();
    }

    public Main(String domainName, int portNumber){
        super();
        this.portNumber = portNumber;
        this.domainName = domainName;
    }

    public void run(){
        try {
            ResourceConfig resourceConfig = new PackagesResourceConfig("com.aholafamily.assignment.rest");
            URI uri = UriBuilder.fromUri("http://"+domainName+"/").port(portNumber).build();
            HttpServer server = HttpServerFactory.create(uri, resourceConfig);
            server.start();
        } catch (Exception exc){
            exc.printStackTrace();
        }

    }
}
