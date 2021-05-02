package kafka.commands;

import kafka.Client;

import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author : Alex
 * @created : 01.05.2021, суббота
 **/
public abstract class ConsoleCommand {
    protected final CommandType type;
    protected final String destination;
    protected final Client client;
    protected final String message;

    enum CommandType{
        SEND{
            @Override
            public ConsoleCommand createCommand(Client client, ParseResult result, Consumer<String> consumer){
                return new CreateProducerCommand(client,result.destination, result.others);
            }
        },
        LISTEN{
            @Override
            public ConsoleCommand createCommand(Client client, ParseResult result, Consumer<String> consumer){
                return new CreateConsumerCommand(client, result.destination, result.others, consumer);
            }
        },
        LIST{
            @Override
            public ConsoleCommand createCommand(Client client, ParseResult result, Consumer<String> consumer){
                return new ListConsumersCommand(client, result.destination, result.others);
            }
        };
        public ConsoleCommand createCommand(Client client, ParseResult result, Consumer<String> consumer){
            throw new RuntimeException("default method");
        }

    }

    public ConsoleCommand(Client client, CommandType type, String destination, String message){
        this.client = client;
        this.type = type;
        this.destination = destination;
        this.message = message;
    }

    public abstract void execute();

    public static class ParseResult{
        public final CommandType type;
        public final String destination;
        public final String others;

        public ParseResult(CommandType type, String destination, String others){
            this.type = type;
            this.destination = destination;
            this.others = others;
        }
        public String toString(){
            return type+" "+destination+" "+others;
        }
    }


    public static ParseResult parse(String line){
        String[] tokens = line.split(" ");
        return new ParseResult(CommandType.valueOf(tokens[0].toUpperCase()),
                tokens.length>1?tokens[1]:"",
                String.join(" ",
                        IntStream.range(0,tokens.length)
                        .filter((index)->index>1)
                        .mapToObj((index)->tokens[index])
                        .collect(Collectors.toList())
                )
        );
    }

    public static ConsoleCommand createCommand(Client client, String line, Consumer<String> consumer){
        ParseResult result = parse(line);
        return result.type.createCommand(client,result,consumer);
    }

}
