package rmi.server;

import rmi.common.HelloChat;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 * @author : Alex
 * @created : 31.03.2021, среда
 **/
public class HelloServer implements HelloChat {
    public static final int port = 8080;
    public static final String name = "HelloChat";

    @Override
    public void message(String from, String message) {
        System.out.println(from+"-->"+message);
    }

    public static void main(String[] args) throws Exception{
        HelloServer server = new HelloServer();
        Registry registry = LocateRegistry.createRegistry(port);
        HelloChat obj = (HelloChat) UnicastRemoteObject.exportObject(server, port);
        registry.bind(name, obj);
        System.out.println("Server started on port "+port);
    }
}
