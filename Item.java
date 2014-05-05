
/**
 * Write a description of class Item here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String item;
    private double peso;

    /**
     * Constructor for objects of class Item
     */
    public Item(String item, double peso)
    {
        this.item = item;
        this.peso = peso;
    }

    //Método que nos devolvera el item.
    public String getItem(){
        return item;
    }
    
    //Método que nos devolvera el peso.
    public double getPeso(){
        return peso;
    }
}
