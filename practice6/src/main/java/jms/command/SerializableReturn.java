package jms.command;

import java.io.Serializable;

/**
 * @author : Alex
 * @created : 01.05.2021, суббота
 **/
public class SerializableReturn implements Serializable {
    public final int carIndex;
    public final Object ret;

    public SerializableReturn(int carIndex, Object ret){
        this.carIndex = carIndex;
        this.ret = ret;
    }
    public String toString(){
        return carIndex + ","+ret;
    }
}
