package grpc.server;


import car.*;
import car.command.Command;
import car.command.MoveCommand;
import car.util.ColorFactory;
import com.google.protobuf.Empty;
import grpc.carservice.AddNewCarResponse;
import grpc.carservice.CarServiceGrpc;
import grpc.carservice.MoveCarResponse;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author : Alex
 * @created : 13.04.2021, вторник
 **/
public class GrpcServer extends CarServiceGrpc.CarServiceImplBase {
    public static final int port = 8080;
    final BasicCarServer carServer;

    protected GrpcServer(FieldMatrix fieldMatrix, CarEventsListener carEventsListener) {
        carServer = BasicCarServer.createCarServer(fieldMatrix, carEventsListener);
    }


    @Override
    public void createCar(grpc.carservice.AddNewCarRequest request,
                          io.grpc.stub.StreamObserver<grpc.carservice.AddNewCarResponse> responseObserver) {
        Car car = carServer.createCar();
        car.setName(request.getName());
        car.setColor(ColorFactory.getColor(request.getColor()));
        AddNewCarResponse response = AddNewCarResponse.newBuilder().setCarIndex(car.getIndex()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void setCarColor(grpc.carservice.SetCarColorRequest request,
                            io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {

        Car car = carServer.getCar(request.getCarIndex());
        car.setColor(ColorFactory.getColor(request.getColor()));
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void moveCar(grpc.carservice.MoveCarRequest request,
                        io.grpc.stub.StreamObserver<grpc.carservice.MoveCarResponse> responseObserver) {
        Car car = carServer.getCar(request.getCarIndex());
        MoveCommand command = (MoveCommand) Command.createCommand(car,request.getDirection().name() + " "+request.getCount());
        Boolean result = command.execute();
        MoveCarResponse response = MoveCarResponse.newBuilder().setResult(result).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void setCarName(grpc.carservice.SetCarNameRequest request,
                           io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
        Car car = carServer.getCar(request.getCarIndex());
        car.setName(request.getName());
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    public static void main(String[] args) throws Exception{
        InputStream is = CarPainter.class.getClassLoader().getResourceAsStream("Field10x10.txt");
        FieldMatrix fm = FieldMatrix.load(new InputStreamReader(is));
        CarPainter p = new CarPainter(fm);
        GrpcServer grpcServer = new GrpcServer(fm,p);
        Server server = ServerBuilder
                .forPort(port)
                .addService(grpcServer).build();
        server.start();
        System.out.println("GrpcServer started");
        server.awaitTermination();
    }

}
