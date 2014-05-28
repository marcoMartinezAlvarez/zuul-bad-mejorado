import java.util.ArrayList;
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
    private Player player;
    private int siguienteIDAAsignar;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        player = new Player();
        createRooms();
        parser = new Parser();
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

        north.setExit("salaOrdenadores", central);
        
        east.setExit("salaOrdenadores", central);
        east.setExit("salaDispositivos", south);

        south.setExit("salaOrdenadores", central);
        south.setExit("salaDispositivos", east);

        west.setExit("salaVisual", central);

        central.setExit("salaInternet", north);
        central.setExit("salaDispositivos", south);
        central.setExit("salaSonido", east);
        central.setExit("salaVisual", west);

        //objetos en las salas
        central.addItem(new Item("impresora", 6.4,true,true));
        central.addItem(new Item("ordenador", 18.2,true,true));
        central.addItem(new Item("pantalla", 5.9,false,true));
        central.addItem(new Item("portatil", 3.5,false,true));

        south.addItem(new Item("movil", 0.5,true,true));
        south.addItem(new Item("telefono", 2.2,true,true));
        south.addItem(new Item("usb", 0.02,true,false));
        south.addItem(new Item("mando", 0.82,true,false));
 
        west.addItem(new Item("television", 14,false,true));
        west.addItem(new Item("dvd", 3.8,true,true));
        west.addItem(new Item("proyector", 6.2,true,true));
        west.addItem(new Item("cables", 14,false,false));

        east.addItem(new Item("altavoces", 8.8,true,true));
        east.addItem(new Item("amplificador", 9.6,true,true));
        east.addItem(new Item("etapa", 3.6,true,false));
        
        north.addItem(new Item("router", 2,true,true));
        north.addItem(new Item("switch", 3.5,true,true));
        north.addItem(new Item("usbWifi", 0.09,true,false));
              

        player.setCurrentRoom(central);
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
        System.out.println("Type 'ayuda' if you need help.");
        System.out.println();
        player.printLocationInfo();
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

        Option commandWord = command.getCommandWord();
        if (commandWord == Option.HELP) {
            printHelp();
        }
        else if (commandWord == Option.GO) {
            player.goRoom(command);
        }
        else if(commandWord == Option.LOOK) {
            player.look();
        }
        else if(commandWord == Option.EAT) {
            player.eat();
        }
        else if(commandWord == Option.BACK) {
            player.back();
        }
        else if (commandWord == Option.ITEMS){
            player.items();
        }
        else if (commandWord == Option.TAKE){
            player.take(command);
        }
        else if (commandWord == Option.QUIT) {
            wantToQuit = quit(command);
        }
        else if (commandWord == Option.DROP){
            player.drop(command);
        }
        else if (commandWord == Option.ENCENDER){
           player.ligtherOn(command);
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

}


