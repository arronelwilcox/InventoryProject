/**
Arron Wilcox
Project Software 1 -C482
Inventory Management System
4/27/2021
Current File: This class is used when products are added and modified from the main controller.Also, this
class is used to associate specific parts with Products in the associated table.
*/

package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class that stores data for Products
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor for Products
      * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
      * @return ProductId
     */
    public int getId() {
        return id;
    }

    /**
     * Sets Product Id.
      * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
      * @return Product Name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product name.
      * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
      * @return products price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * sets the products price.
      * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }
    // Returns Products Stock/Inventory
    public int getStock() {
        return stock;
    }

    /**
     * sets the products inventory.
      * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
      * @return products min.
     */
    public int getMin() {
        return min;
    }

    /**
     * sets products min.
      * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
      * @return products max.
     */
    public int getMax() {
        return max;
    }

    /**
     * sets products max.
      * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds associated part to a product.
      * @param part
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * Deletes an associated part from a product
      * @param selectedAssociatedPart
     * @return boolean
     */
    public Boolean deleteAssociatedPart(Part selectedAssociatedPart){
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**
      * @return ObservableList associatedParts.
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
