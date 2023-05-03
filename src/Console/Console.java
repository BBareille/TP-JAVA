package Console;

import Controller.CommandController;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

import static Controller.CommandController.menu;

public class Console {

    private static Console instance;
    Scanner scanner;
    Integer commandAsked;
    List<Command> commandList;
    boolean isRunning = false;
    int nombre;
    public Console(){
        this.scanner = new Scanner(System.in);
        instance = this;
        this.commandList = new ArrayList<>();
    }

    public static Console getInstance(){
        return instance;
    }
    public boolean isRunning() {
        return isRunning;
    }
    public void setRunning(boolean running) {
        isRunning = running;
    }
    public void setCommandAsked(Integer n){
        this.commandAsked = n;
        for (Command command: commandList)
            if(command.id == n)
                command.consumer.accept(n);
    }
    public void setCommandList(List<Command> commandList , boolean isMainMenu) {
        if (!isMainMenu) {
            Command command8 = new Command(8, "8. Retourner au menu princpal", c -> {
                try {
                    menu();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            commandList.add(command8);
        }

        Command command9 = new Command(9, "9. Quitter", c -> {
            CommandController.quit();
        });
        commandList.add(command9);
        this.commandList = commandList;
    }

    public void run() throws SQLException {
        menu();
        isRunning = true;
        while (isRunning){
            show(commandList);
            nombre = scanner.nextInt();
            setCommandAsked(nombre);
        }
    }

    public void show(List<Command> commands){
        for (Command command: commands)
            System.out.println(command.displayName);
    }

    public <T> void command(Consumer<T> command, T t, Integer n){
        System.out.println(t);
        if(n == commandAsked){
            command.accept(t);
        }

    }
}
