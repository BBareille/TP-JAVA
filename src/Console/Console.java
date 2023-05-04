package Console;

import Commands.BackToMainMenuCommand;
import Commands.QuitCommand;
import Controller.CommandController;
import database.SqlConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class Console {

    private static Console instance;
    Scanner scanner;
    Integer commandAsked;
    List<Command> commandList;
    boolean isRunning = false;
    SqlConnection connection;
    CommandController commandController;
    int nombre;
    public Console(SqlConnection connection){
        this.scanner = new Scanner(System.in);
        this.connection = connection;
        instance = this;
        this.commandList = new ArrayList<>();
        this.commandController = new CommandController(connection);
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
                command.apply(null);
    }
    public void setCommandList(List<Command> commandList , boolean isMainMenu) {
        if (!isMainMenu) {
            commandList.add(new BackToMainMenuCommand(8, "8. Retourner au menu principal", null, null));
        }
        commandList.add(new QuitCommand(9, "9. Quitter", null, null));
        this.commandList = commandList;
    }

    public void run() throws SQLException {
        commandController.menu();
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
