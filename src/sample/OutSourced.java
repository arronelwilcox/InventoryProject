/**
Arron Wilcox
Project Software 1 -C482
Inventory Management System
4/27/2021
Current File: This class extends the Abstract Class Part and Holds the functionality for All OutSourced parts.
*/
package sample;

/**
 * Extends Part and stores data for OutSourced Parts.
 * This class extends the Abstract Class Part and Holds the functionality for All OutSourced parts.
 */
public class OutSourced extends Part{

    // Private variable to hold the companies name
    private String companyName;

    /**
     * Constructor for OutSourced parts
      * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
    }

    /**
     * Sets the company name
      * @param companyName1
     */
    public void setCompanyName(String companyName1){
        this.companyName = companyName;
    }

    /**
     * returns companyName
      * @return String
     */
    public String getCompanyName(){
        return companyName;
    }
}
