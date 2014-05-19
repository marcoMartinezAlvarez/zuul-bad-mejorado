import java.util.HashMap;
/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class CommandWords
{
    // a constant array that holds all valid command words
    private HashMap<String,Option> commands;

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        commands = new HashMap<>();
        commands.put("go",Option.GO);
        commands.put("quit",Option.QUIT);
        commands.put("help",Option.HELP);
        commands.put("look",Option.LOOK);
        commands.put("eat",Option.EAT);
        commands.put("back",Option.BACK);
        commands.put("items",Option.ITEMS);
        commands.put("take",Option.TAKE);
        commands.put("drop",Option.DROP);
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        return commands.containsKey(aString);
    }

    /**
     * Print all valid commands to System.out.
     */    
    public void showAll()
    {
        for(String key : commands.keySet()){
            System.out.print(key + ", ");
        }

    }

    /**
     * Return the Option associated with a word.
     * @param commandWord The word to look up (as a string).
     * @return The Option correspondng to commandWord, or UNKNOWN
     *         if it is not a valid command word.
     */
    public Option getCommandWord(String commandWord){
        Option command = Option.UNKNOWN;
        
        if(isCommand(commandWord)){
            command = commands.get(commandWord);
        }
        
        return command;
    }
}


