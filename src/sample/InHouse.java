/**
Arron Wilcox
Project Software 1 -C482
Inventory Management System
4/27/2021
Current File: This class extends the Abstract Class Part and Holds the functionality for All InHouse parts.
*/
package sample;

/**
 * Class extends the Abstract Part Class and stores the data for InHouse parts
 */
public class InHouse extends Part {

    // Private variable to hold machineId.
    private int machineId;

    /**
     * Constructor for InHouse Part
      * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
    }

    /**
     * Sets machineID
      * @param machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * returns machineId
      * @return Int
     */
    public int getMachineId() {
        return machineId;
    }
}
