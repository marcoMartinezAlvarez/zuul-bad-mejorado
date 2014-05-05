import java.util.Stack;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> previousRoom;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        previousRoom = new Stack<>();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room north, south, east, west, central, southeast;

        // create the rooms
        north = new Room("north of the central room");
        south = new Room("south of the central room");
        east = new Room("east of the central room");
        west = new Room("west of the central room");
        central = new Room("in central room");

        // initialise room exits

        north.setExit("bajandoLasEscaleras", central);
        east.setExit("saltasElMuro", central);
        east.setExit("bajasPorElCamino", south);

        south.setExit("vasAlCentro", central);
        south.setExit("subesElCamino", east);

        west.setExit("pasaPorElPuente", central);

        central.setExit("subeLasEscaleras", north);
        central.setExit("bajasALaZonaSur", south);
        central.setExit("esquivasElMuro", east);
        central.setExit("saltaElRio", west);

        //objetos en las salas
        central.addItem("roca", 12.4);
        central.addItem("ordenador", 6.2);
        central.addItem("ventana", 8.9);

        south.addItem("mochila", 3.2);

        west.addItem("tronco", 14);
        west.addItem("extintor", 9);

        east.addItem("botella", 2);
        east.addItem("armario", 36.2);

        currentRoom = central;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            printLocationInfo();
        }
        else if (commandWord.equals("eat")) {
            System.out.println("You have eaten now and you are not hungry any more");
        }
        else if (commandWord.equals("back")) {
            if(previousRoom.empty()){
                System.out.println("you can not go back because there is no previous room");
            }else{
                currentRoom = previousRoom.pop();
                printLocationInfo();
            }
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.printCommand();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String a = command.getSecondWord();
        Room nextRoom = currentRoom.getExit(command.getSecondWord()); 

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            previousRoom.push(currentRoom);
            currentRoom = nextRoom;
            printLocationInfo();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    private void printLocationInfo(){
        System.out.println("" + currentRoom.getLongDescription());
        System.out.println();
        //System.out.println("En esta sala se encuentra el objeto: " + currentRoom.getItem() + "\n el cual pesa: " + currentRoom.getPeso() + "Kg");

    }
}
