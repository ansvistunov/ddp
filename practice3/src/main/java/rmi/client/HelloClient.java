package rmi.client;

import rmi.common.HelloChat;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 * @author : Alex
 * @created : 31.03.2021, среда
 **/
public class HelloClient {
    public static final int port = 8080;
    public static final String name = "HelloChat";
    public static final String host = "132.145.228.39";

    public static void main(String[] args) throws Exception{
        Registry registry = LocateRegistry.getRegistry(host, port);
        System.out.println(String.format("Client connected to registry on host %s and port %d",host, port));
        HelloChat server = (HelloChat) registry.lookup(name);
        System.out.println("server="+server);
        server.message("Alex", "Hello");
    }
}
