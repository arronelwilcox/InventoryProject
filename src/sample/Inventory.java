/**
Arron Wilcox
Project Software 1 -C482
Inventory Management System
4/27/2021
Current File: This class Holds the functionality for All the observable Parts and Products that are used
and manipulated throughout the program.
*/


package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

/**
 * This Class implements all of the functions used in the MainController class
 */
public class Inventory {
    // List created to store all parts
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    // List created to store all products
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static Part part;

    private static Product product;

    /**
     * Adds a part to the allParts list
      * @param newPart
     * @throws IOException
     */
    public static void addPart(Part newPart) throws IOException {
        allParts.add(newPart);
    };

    /**
     * Adds a product to the allProducts list
     * @param newProduct
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    };

    /**
     * Finds a specific part in the allParts list based on its id
      * @param partId
     * @return part
     */
    public static Part lookUpPart(int partId){
        for(Part part : allParts){
            if(partId == part.getId()){
                return part;
            }
        }
        return null;
    };


    /**
     * Finds a specific product in the allProducts list based on the is
     * @param productId
     * @return product
     */
    public static Product lookUpProduct(int productId){
        for(Product product : allProducts){
            if(productId == product.getId()){
                return product;
            }

        }
        return null;
    };
    /**

        */
    // . (.contains() functionality)

    /**
     * Function to look up part by name with .contains() function
     * @param partName
     * @return ObservableList
     *  Had trouble at first with getting the correct name because i used a regular for loop.Therefore i changed to
     *  a for in loop.I did this for the rest of the similar functions in this class.
     */
    public static ObservableList<Part> lookUpPart(String partName){
        ObservableList<Part> filteredPartList = FXCollections.observableArrayList();
        for(Part part : allParts){
            if(part.getName().contains(partName)){
                filteredPartList.add(part);
            }

        }
        return filteredPartList;
    };


    /**
     * Function to look up product by name with .contains() function
     * @param productName
     * @return ObservableList
     */
    public static ObservableList<Product> lookUpProduct(String productName){
        ObservableList<Product> filteredProductList = FXCollections.observableArrayList();
        for(Product product : allProducts){
            if(product.getName().contains(productName)){
                filteredProductList.add(product);
            }
        }
        return filteredProductList;
    };

    /**
     * Updates a Part in the allParts list at a given index with a new part.
      * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index-1,selectedPart);

    };

    /**
     * Updates a Part in the allParts list at a given index with a new part.
     * @param index
     * @param selectedProduct
     */
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index-1,selectedProduct);

    };

    /**
     *
      * @param selectedPart
     * @return boolean
     */
    public static boolean deletePart(Part selectedPart){
        if(allParts.contains(selectedPart))
        {
            allParts.remove(selectedPart);
            return true;
        }
        else{
            return  false;
        }
    };

    /**
     * Deletes a selected product from the allProducts list
      * @param selectedProduct
     * @return
     */
    public static boolean deleteProduct(Product selectedProduct){
        if(allProducts.contains(selectedProduct))
        {
            allProducts.remove(selectedProduct);
            return true;
        }
        else{
            return  false;
        }
    };

    /**
     * returns the allParts list
      * @return observableList
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    };

    /**
     * returns the allProducts list
     * @return observableList
     */    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    };

}
