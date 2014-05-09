import java.util.Stack;
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player
{
    private Room currentRoom;
    private Stack<Room> previousRoom;

    /**
     * Constructor for objects of class Player
     */
    public Player()
    {   
        previousRoom = new Stack<>();
        currentRoom = new Room("central room");
    }

    public void setPlayerRoom(Room roomStatus){
        currentRoom = roomStatus;
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        //         String a = command.getSecondWord();
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

    public void goBack(){
        if(previousRoom.empty()){
            System.out.println("you can not go back because there is no previous room");
        }else{
            currentRoom = previousRoom.pop();
        }
    }

    public void printLocationInfo(){
        System.out.println("" + currentRoom.getLongDescription());
        System.out.println();
    }
}