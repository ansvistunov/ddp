package grpc.client;

import car.CarServer;
import grpc.carservice.*;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;

/**
 * @author : Alex
 * @created : 13.04.2021, вторник
 **/
public class GrpcClient {
    final CarServiceGrpc.CarServiceBlockingStub client;
    public GrpcClient(String host, int port){
        client = createClient(host, port);
    }
    public int createCar(String name, String color){
        AddNewCarRequest request = AddNewCarRequest.newBuilder()
                .setName(name)
                .setColor(color)
                .build();
        AddNewCarResponse response = client.createCar(request);
        return response.getCarIndex();
    }

    public void setCarName(int carIndex, String name){
        SetCarNameRequest request = SetCarNameRequest.newBuilder()
                .setCarIndex(carIndex)
                .setName(name)
                .build();
        client.setCarName(request);
    }

    public void setCarColor(int carIndex, String color){
        SetCarColorRequest request = SetCarColorRequest.newBuilder()
                .setCarIndex(carIndex)
                .setColor(color)
                .build();
        client.setCarColor(request);
    }

    public boolean moveCar(int carIndex, CarServer.Direction direction, int count){
        MoveCarRequest request = MoveCarRequest.newBuilder()
                .setCarIndex(carIndex)
                .setDirectionValue(direction.ordinal())
                .setCount(count)
                .build();
        MoveCarResponse response = client.moveCar(request);
        return response.getResult();
    }

    private CarServiceGrpc.CarServiceBlockingStub createClient(String host, int port){
        Channel channel = ManagedChannelBuilder.forAddress(host,port)
                .usePlaintext()
                .build();
        return CarServiceGrpc.newBlockingStub(channel);
    }




}
