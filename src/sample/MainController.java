/**
Arron Wilcox
Project Software 1 -C482
Inventory Management System
4/29/2021
*/

package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class is the main controller that loads every FXML form used in the Inventory Management System.
 * This class implements initializable for the loading property for each form. Also, all other classes functions
 * are used within this file in order to manipulate all forms functionality.
 */
public class MainController implements Initializable {

    /**
    partId and proId variables set the part and product id's
     */
    private int partId = Inventory.getAllParts().size()+1;
    private int proId = Inventory.getAllProducts().size()+1;
    /**
    Variables for the Main form are below
     */
    private Stage currentWindow = new Stage();
    @FXML
    private TextField nameTextfield = new TextField();
    @FXML
    private TextField inventoryTextfield = new TextField();
    @FXML
    private TextField minTextfield = new TextField();
    @FXML
    private TextField maxTextfield = new TextField();
    @FXML
    private TextField priceTextfield = new TextField();
    @FXML
    private Label machineIdText = new Label();
    @FXML
    private TextField machineIdTextfield = new TextField();
    @FXML
    private TableView<Part> partListView = new TableView<Part>(FXCollections.observableArrayList()) ;
    @FXML
    private TableView<Product> productListView = new TableView<Product>(FXCollections.observableArrayList());
    @FXML
    private RadioButton inHouseRadio = new RadioButton();
    @FXML
    private RadioButton outsourcedRadio = new RadioButton();
    @FXML
    private TextField searchPart = new TextField();;
    @FXML
    private TextField searchProduct = new TextField();;
    @FXML
    private TableColumn<Part,Integer> partIdSet = new TableColumn<>();
    @FXML
    private TableColumn<Part,String> partNameSet = new TableColumn<>();
    @FXML
    private TableColumn<Part,Integer> partInvSet = new TableColumn<>();
    @FXML
    private TableColumn<Part,Integer> partPriceSet = new TableColumn<>();
    @FXML
    private TableColumn<Product,Integer> productIdSet = new TableColumn<>();
    @FXML
    private TableColumn<Product,String> productNameSet = new TableColumn<>();
    @FXML
    private TableColumn<Product,Integer> productInvSet = new TableColumn<>();
    @FXML
    private TableColumn<Product,Integer> productPriceSet = new TableColumn<>();

    /**
     * This function helps determine whether a string is a number or not.
     * Specifically this function will be used throughout the program to determine
     * if certain data entered in the field are integers like they are supposed to be.
     * @param supposeNumber
     * @return boolean
     */
    private static boolean isNumber(String supposeNumber){
        if (supposeNumber == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(supposeNumber);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @FXML
    private void exit(ActionEvent event){
        System.exit(0);
    }

    /**
    This function is supposed to select the searched part.
    At first i couldn't get the lookup name part to work for
    the search function until i realized I needed to catch the NumberFormatException
    that was being thrown when i typed in a string rather then an int in the search
    textfield.
     @param event
     */
    @FXML
    private void searchPartAction(ActionEvent event){
        if(searchPart.getText().trim().isEmpty()){
            partListView.setItems(Inventory.getAllParts());
            partsTableProduct.setItems(Inventory.getAllParts());
        }
        else if(searchPart.getText().trim() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("That search is incorrect");
            alert.showAndWait();
        }
        else{
            try{
                Part returnedPart = Inventory.lookUpPart(Integer.parseInt(searchPart.getText().trim()));
                if(returnedPart == null){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Part not found");
                    alert.showAndWait();
                }
                else{
                    ObservableList<Part> viewableParts = FXCollections.observableArrayList();
                    viewableParts.add(returnedPart);
                    partListView.setItems(viewableParts);
                    partsTableProduct.setItems(viewableParts);
                }
            } catch (Exception e) {
                partListView.setItems(Inventory.lookUpPart(searchPart.getText().trim()));
                partsTableProduct.setItems(Inventory.lookUpPart(searchPart.getText().trim()));
            }
        }
    }
    /**
    This function makes the product table searchable by id or name.
    I ran into the same NumberFormatException issue as before but i placed the lookUp by name
    function in the catch clause to implement the correct functionality.
     @param event
     */
    @FXML
    private void searchProductAction(ActionEvent event){
        if(searchProduct.getText().trim().isEmpty()){
            productListView.setItems(Inventory.getAllProducts());
        }
        else if(searchProduct.getText().trim() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("That search is incorrect");
            alert.showAndWait();
        }
        else{
            try{
                Product returnedProduct = Inventory.lookUpProduct(Integer.parseInt(searchProduct.getText().trim()));
                if(returnedProduct == null){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Product not found");
                    alert.showAndWait();
                }
                else{
                    ObservableList<Product> viewableProduct = FXCollections.observableArrayList();
                    viewableProduct.add(returnedProduct);
                    productListView.setItems(viewableProduct);
                }
            } catch (NumberFormatException e) {
                productListView.setItems(Inventory.lookUpProduct(searchProduct.getText().trim()));
            }
        }
    }
    /**
    This Function loads the add part form
     */
    @FXML
    private void addPartAction(ActionEvent event){
        try{
            FXMLLoader addPartLoader = new FXMLLoader(getClass().getResource("AddPartForm.fxml"));
            Parent root = addPartLoader.load();
            Scene scene = new Scene(root);
            currentWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
            currentWindow.setTitle("Add Part Form");
            currentWindow.setScene(scene);
            currentWindow.show();
        }catch (Exception e){
            System.out.println();
            System.out.println("Not loading form");
            e.printStackTrace();
        }
    }
    /**
    This function loads the modify part form. selectedPart is set to the selected item before the modify
    Part form is loaded.
     */
    @FXML
    private void modifyPartAction(ActionEvent event)throws IOException {
        selectedPart = partListView.getSelectionModel().getSelectedItem();

        if(selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No part selected");
            alert.setContentText("Please select a part");
            alert.showAndWait();
        }
        else{
            try {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModifyPartForm.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Scene scene = new Scene(root);
                currentWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentWindow.setTitle("Modify Part Form");
                currentWindow.setScene(scene);
                currentWindow.show();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Cant Load Add Form");
            }
        }
    }
    /**
    This function deletes a selected part from the table
     */
    @FXML
    private void deletePart(){
        Part removePart = partListView.getSelectionModel().getSelectedItem();
        if(removePart == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nothing is selected to delete");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("If you are sure you want to delete the selected Part please press OK!/n" +
                    "Otherwise press cancel");
            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK){
                Inventory.deletePart(removePart);
                ObservableList<Part> newPartList = Inventory.getAllParts();
                partListView.setItems(newPartList);
            }
            else{
                alert.close();
            }
        }
    }
    /**
  This function deletes a selected product from the table
  */
    @FXML
    private void deleteProduct(){
        Product removeProduct = productListView.getSelectionModel().getSelectedItem();
        if(removeProduct == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nothing is selected to delete");
            alert.showAndWait();
        }
        else if (!removeProduct.getAllAssociatedParts().isEmpty()){
            Alert alertD = new Alert(Alert.AlertType.CONFIRMATION);
            alertD.setHeaderText("The associated parts with this product must be removed before deletion");
            alertD.setContentText("Would you like to remove all Associated parts?");
            alertD.showAndWait();
            if(alertD.getResult() == ButtonType.OK){
                removeProduct.getAllAssociatedParts().clear();
                Inventory.deleteProduct(removeProduct);
                ObservableList<Product> newProductList = Inventory.getAllProducts();
                productListView.setItems(newProductList);
            }
            else{
                alertD.close();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("If you are sure you want to delete the selected Product please press OK!\n" +
                    "Otherwise press cancel");
            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK){
                Inventory.deleteProduct(removeProduct);
                ObservableList<Product> newProductList = Inventory.getAllProducts();
                productListView.setItems(newProductList);
            }
            else{
                alert.close();
            }
        }
    }
    /**
    This function loads the add product form.
     @param event
     */
    @FXML
    private void addProductAction(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddProductForm.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            Scene scene = new Scene(root);
            currentWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
            currentWindow.setTitle("Add Product Form");
            currentWindow.setScene(scene);
            currentWindow.show();
        }catch (Exception e){
            System.out.println();
            System.out.println("Not loading form");
            System.out.println(e);
        }
    }
    /**
       This function loads the modify product form.
       @param event
       @throws IOException
     */
    @FXML
    private void modifyProductAction(ActionEvent event) throws IOException {
        selectedProduct = productListView.getSelectionModel().getSelectedItem();
//        System.out.println(selectedProduct.getId() + " "+ selectedProduct.getName());
        if(selectedProduct == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No product selected");
            alert.setContentText("Please select a product");
            alert.showAndWait();
        }
        else{
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModifyProductForm.fxml"));
                Parent root = (Parent)fxmlLoader.load();
                Scene scene = new Scene(root);
                currentWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
                currentWindow.setTitle("Modify Product Form");
                currentWindow.setScene(scene);
                currentWindow.show();
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("Cant Load Add Form");
            }
        }

    }
    /**
     Sets the changed text field for the Radio buttons
     Had Null Pointer exception Error because outsource radio & and inhouse radio buutons
     were not initialized.
     @param event
     */
    @FXML
    private void selectRadio(ActionEvent event){
        ToggleGroup toggle = new ToggleGroup();
        outsourcedRadio.setToggleGroup(toggle);
        inHouseRadio.setToggleGroup(toggle);
        if(inHouseRadio.isSelected()){
            machineIdText.setText("Machine ID");
        }
        else if (outsourcedRadio.isSelected()) {
            machineIdText.setText("Company Name");
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Radio selection not working");
            System.out.println("Which button");
        }
    }
    /**
   Sets the changed text field for the Radio buttons
   Had Null Pointer exception Error because outsourcemod & and inhousemod radio buutons
    were not initialized.
   */
    @FXML
    private void selectRadioMod(ActionEvent event){
        ToggleGroup toggleMod = new ToggleGroup();
        inHouseMod.setToggleGroup(toggleMod);
        outSourcedMod.setToggleGroup(toggleMod);
        if(inHouseMod.isSelected()){
            machineIdTextModPart.setText("Machine ID");
            System.out.println("inMod");

        }
        else if (outSourcedMod.isSelected()) {
            machineIdTextModPart.setText("Company Name");
            System.out.println("outMod");

        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Radio selection not working");
            System.out.println("Which button");
        }
    }

 /**
  Function that saves newly added parts, either in-house or outsourced, to a Parts observable list.
  Originally a runtime Exception: InvocationTargetException was thrown when a user would enter
  alphanumeric data in a field that required a number. I fixed the error by
  implementing an if statement with a function that checks to see if the
  data entered in the field is a number and displays an error if not.
  Created a new array list to hold the company name in order to rePopulate
  the company name. Before the Company Name was null everytime it was set from
  the selectedPart value.
  @param event
  @throws IOException
 */
    public static List<String> companyNameList = new ArrayList<>();

    @FXML
    private void savePart(ActionEvent event) throws IOException {
        if(nameTextfield.getText().isEmpty() ||
        inventoryTextfield.getText().isEmpty() ||
        priceTextfield.getText().isEmpty() ||
        minTextfield.getText().isEmpty() ||
        maxTextfield.getText().isEmpty()) {
            System.out.println("No Parts");
            Alert partAlert = new Alert(Alert.AlertType.WARNING);
            partAlert.setTitle("No Parts Available");
            partAlert.showAndWait();
        }
        else if(!isNumber(inventoryTextfield.getText()) || !isNumber(maxTextfield.getText()) ||
                !isNumber(minTextfield.getText()) || !isNumber(priceTextfield.getText())){
            Alert partAlert = new Alert(Alert.AlertType.WARNING);
            partAlert.setContentText("Price, Inventory , Min , and Max Fields must be numbers");
            partAlert.showAndWait();
        }
        else if(Integer.parseInt(maxTextfield.getText()) <= Integer.parseInt(minTextfield.getText())){
            Alert partAlert = new Alert(Alert.AlertType.WARNING);
            partAlert.setTitle("Max has to be greater than min");
            partAlert.showAndWait();
        }
        else if(Integer.parseInt(inventoryTextfield.getText()) <= Integer.parseInt(minTextfield.getText()) ||
                Integer.parseInt(inventoryTextfield.getText()) >= Integer.parseInt(maxTextfield.getText())){
            Alert partAlert = new Alert(Alert.AlertType.WARNING);
            partAlert.setTitle("Inventory must be between min and max");
            partAlert.showAndWait();
        }

        else{
            try{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("AddPart");
                alert.setHeaderText("Would you like to save?");
                alert.showAndWait();
                if(alert.getResult() == ButtonType.OK) {
                    String partName = nameTextfield.getText();
                    int stock = Integer.parseInt(inventoryTextfield.getText());
                    Double cost = Double.parseDouble(priceTextfield.getText());
                    int mini = Integer.parseInt(minTextfield.getText());
                    int maxi = Integer.parseInt(maxTextfield.getText());
                    String machineId = machineIdTextfield.getText();
                    if(outsourcedRadio.isSelected()){
                        OutSourced newPart = new OutSourced(partId,partName,cost,stock,mini,maxi,machineId);
                        newPart.setCompanyName(machineId);
                        companyNameList.add(machineId);
                        Inventory.addPart(newPart);

                    }
                    else if(inHouseRadio.isSelected()){
                        if(!isNumber(machineIdTextfield.getText())){
                            Alert partAlert = new Alert(Alert.AlertType.WARNING);
                            partAlert.setContentText("Machine ID must be a number");
                            partAlert.showAndWait();
                        }
                        else{
//                            int machineId = Integer.parseInt(machineIdTextfield.getText());
                            InHouse newPart = new InHouse(partId,partName,cost,stock,mini,maxi,Integer.parseInt(machineId));
                            newPart.setMachineId(Integer.parseInt(machineId));
                            companyNameList.add(machineId);
                            Inventory.addPart(newPart);
                        }

                    }
                    currentWindow.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("MainFormH.fxml"));
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root,1100,1100);
                    currentWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
                    currentWindow.setTitle("Inventory Management System");
                    currentWindow.setScene(scene);
                    currentWindow.show();
                }
                else{
                    alert.close();
                }
            }catch (Exception e){

            }
        }
    }
    /**
    This Function is for the cancel button which closes the current form and
    loads tbe main form.
     @param event
     */
    @FXML
    private void cancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Trying to exit");
        alert.setHeaderText("If you wish to exit click ok. Otherwise cancel");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK){
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("MainFormH.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root,1100,1100);
            currentWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
            currentWindow.setTitle("Inventory Management System");
            currentWindow.setScene(scene);
            currentWindow.show();
        }
        else{
            alert.close();
            FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("MainFormH.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root,1100,1100);
            currentWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
            currentWindow.setTitle("Inventory Management System");
            currentWindow.setScene(scene);
            currentWindow.show();
        }
    }
    /*
    These variables are used in the Product form.
     */
    public static Product product;

    @FXML
    TableView<Part> partsTableProduct = new TableView<>(FXCollections.observableArrayList());
    @FXML
    TableView<Part> associatedPartsTable = new TableView<>(FXCollections.observableArrayList());
    @FXML
    TableColumn<Part, Integer> partsProductIDCol = new TableColumn<>();
    @FXML
    TableColumn<Part, String> partsProductNameCol = new TableColumn<>();
    @FXML
    TableColumn<Part, Integer> partsProductInvCol = new TableColumn<>();
    @FXML
    TableColumn<Part, Integer> partsProductPriceCol = new TableColumn<>();
    @FXML
    TableColumn<Part, Integer> assocProductPartIDCol = new TableColumn<>();
    @FXML
    TableColumn<Part, String> assocProductNameCol = new TableColumn<>();
    @FXML
    TableColumn<Part, Integer> assocProductInvCol = new TableColumn<>();
    @FXML
    TableColumn<Part, Integer> assocProductPriceCol = new TableColumn<>();
    @FXML
    TextField productName = new TextField();
    @FXML
    TextField productInv = new TextField();
    @FXML
    TextField productId = new TextField();
    @FXML
    TextField productPrice = new TextField();
    @FXML
    TextField productMax = new TextField();
    @FXML
    TextField productMin = new TextField();
    /**
    This Function will add any selected part from the parts table to the bottom associated parts
    table in the add product form.
     @param event
    */
    @FXML
    private void addAssociatedPart(ActionEvent event){
        if(partsTableProduct.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nothing is selected !");
            alert.setHeaderText("Please select a Part to add to the associated part list");
            alert.showAndWait();
        }
        else{
            try{
                if(selectedProduct == null){
                    System.out.println("is empty");
                    selectedProduct = new Product(proId,productName.getText(),Double.parseDouble(productPrice.getText()),
                            Integer.parseInt(productInv.getText()),Integer.parseInt(productMax.getText()),Integer.parseInt(productMin.getText()));
                    System.out.println(selectedProduct.getName());

                }
                else if(productName.getText().isEmpty() || productMax.getText().isEmpty()|| productMin.getText().isEmpty()
                        ||productPrice.getText().isEmpty()||productInv.getText().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Nothing is inputted !");
                    alert.setHeaderText("Please input all the information needed");
                    alert.showAndWait();
                }
                Part currentPart = partsTableProduct.getSelectionModel().getSelectedItem();
                if(associatedPartsTable.getItems().contains(currentPart)){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("That part is already associated with this product");
                    alert.showAndWait();
                }
                else{
                    associatedPartsTable.getItems().add(currentPart);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    /**
    This function is for the Modify product form to implement added associated parts to the bottom table
     of the modify product form.
     @param event
     */
    @FXML
    private void addAssociatedPartMod(ActionEvent event){
        if(partsTableProduct.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nothing is selected !");
            alert.setHeaderText("Please select a Part to add to the associated part list");
            alert.showAndWait();
        }
        else{
            if(selectedProduct == null){
                selectedProduct = new Product(proId,nameTextfieldModPro.getText(),Double.parseDouble(priceTextfieldModPro.getText()),
                        Integer.parseInt(inventoryTextfieldModPro.getText()),Integer.parseInt(maxTextfieldModPro.getText())
                        ,Integer.parseInt(minTextfieldModPro.getText()));
            }
            else if(nameTextfieldModPro.getText().isEmpty() || maxTextfieldModPro.getText().isEmpty()|| minTextfieldModPro.getText().isEmpty()
                    ||priceTextfieldModPro.getText().isEmpty()||inventoryTextfieldModPro.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Nothing is inputted !");
                alert.setHeaderText("Please input all the information needed");
                alert.showAndWait();
            }
            Part currentPart = partsTableProduct.getSelectionModel().getSelectedItem();
            if(associatedPartsTable.getItems().contains(currentPart)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("That part is already associated with this product");
                alert.showAndWait();
            }
            else{
                associatedPartsTable.getItems().add(currentPart);
            }
        }
    }
    /**
    This function removes the selected associated part from the associated parts table, and from the
     product itself.
     */
    @FXML
    private void removeAssociatedPart(){
        Part removePart = associatedPartsTable.getSelectionModel().getSelectedItem();
        if(removePart == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("There are no products selected");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("If you are sure you would like to delete the Associated Part press OK!"+
            "Otherwise press cancel");
            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK){
                selectedProduct.deleteAssociatedPart(removePart);
                associatedPartsTable.setItems(selectedProduct.getAllAssociatedParts());
            }
           else {
               alert.close();
            }
        }
    }
    /**
     This function saves a product to the product list.
     Originally a runtime Exception: InvocationTargetException was thrown when a user would enter
     alphanumeric data in a field that required a number. I fixed the error by
     implementing an if statement with a function that checks to see if the
     data entered in the field is a number and displays an error if not.
     @param event
     */
    @FXML
    private void saveProduct(ActionEvent event) throws IOException, NumberFormatException{
        if(productName.getText().isEmpty() ||
                productInv.getText().isEmpty() ||
                productPrice.getText().isEmpty() ||
                productMin.getText().isEmpty() ||
                productMax.getText().isEmpty() )
        {
            Alert productAlert = new Alert(Alert.AlertType.WARNING);
            productAlert.setTitle("No Parts Available");
            productAlert.showAndWait();
        }
        else if(!isNumber(productInv.getText()) || !isNumber(productMax.getText()) ||
                !isNumber(productMin.getText()) || !isNumber(productPrice.getText())){
            Alert partAlert = new Alert(Alert.AlertType.WARNING);
            partAlert.setContentText("Price, Inventory , Min , and Max Fields must be numbers");
            partAlert.showAndWait();
        }
        else if(Integer.parseInt(productMax.getText()) < Integer.parseInt(productMin.getText())){
            System.out.println("Max exceeds minimum");
            Alert productAlert = new Alert(Alert.AlertType.WARNING);
            productAlert.setTitle("Please be sure the Maximum is a higher Value than the Minimum requirement");
            productAlert.showAndWait();
        }
        else if(Integer.parseInt(productInv.getText()) <= Integer.parseInt(productMin.getText()) ||
                Integer.parseInt(productInv.getText()) >= Integer.parseInt(productMax.getText())){
            Alert productAlert = new Alert(Alert.AlertType.WARNING);
            productAlert.setTitle("Inventory must be between the Minimum and Maximum requirement");
            productAlert.showAndWait();
        }
        else{
            try{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Add Product");
                alert.setHeaderText("Would you like to save?");
                alert.showAndWait();
                if(alert.getResult() == ButtonType.OK) {
                    String currentProductName = productName.getText();
                    int currentStock = Integer.parseInt(productInv.getText());
                    Double currentCost = Double.parseDouble(productPrice.getText());
                    int currentMin = Integer.parseInt(productMin.getText());
                    int currentMax = Integer.parseInt(productMax.getText());
                    Product newProduct = new Product(proId,currentProductName,currentCost,currentStock,currentMin,currentMax);
                    ObservableList<Part> assoPart = associatedPartsTable.getItems();
                    for (int i=0;i<assoPart.size();i++){
                        newProduct.addAssociatedPart(assoPart.get(i));
                        System.out.println(assoPart.get(i).getName());
                    }
                    Inventory.addProduct(newProduct);


                    FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("MainFormH.fxml"));
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root,1100,1100);
                    currentWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
                    currentWindow.setTitle("Inventory Management System");
                    currentWindow.setScene(scene);
                    currentWindow.show();
                }
                else{
                    alert.close();
                }
            }catch (Exception e){
                System.out.println("Cant Load O Form");
            }
        }
    }
    /*
    These are the variables used in the modify part form.
     */
    public static Product selectedProduct;

    public static Part selectedPart  ;

    public static OutSourced outPart;

    public static InHouse inPart;
    @FXML
    private RadioButton inHouseMod = new RadioButton();
    @FXML
    private RadioButton outSourcedMod = new RadioButton();
    @FXML
    private TextField idTextfieldModPart = new TextField();
    @FXML
    private TextField nameTextfieldModPart = new TextField();
    @FXML
    private TextField inventoryTextfieldModPart = new TextField();
    @FXML
    private TextField minTextfieldModPart = new TextField();
    @FXML
    private TextField maxTextfieldModPart = new TextField();
    @FXML
    private TextField priceTextfieldModPart = new TextField();
    @FXML
    private Label machineIdTextModPart = new Label();
    @FXML
    private TextField machineIdTextfieldModPart = new TextField();
    /**
    This function populates a selected parts information in all the labels
    At first i had alot of trouble getting the information to load into the textfields because of
    a null pointer exception with selectedPart. I soon figured outi needed to provide a static variable
    for selectedPart and use set selectedPart to the part that is selected before the modify Part form
    is opened.
     */
    @FXML
    private void modifyPartView(){
        if(Inventory.getAllParts().isEmpty() || selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("There are no Products selected");
            selectedPart = partListView.getSelectionModel().getSelectedItem();
        }
        else{
            ToggleGroup toggleMod = new ToggleGroup();
            inHouseMod.setToggleGroup(toggleMod);
            outSourcedMod.setToggleGroup(toggleMod);
            idTextfieldModPart.setText(String.valueOf(selectedPart.getId()));
            nameTextfieldModPart.setText(selectedPart.getName());
            inventoryTextfieldModPart.setText(String.valueOf(selectedPart.getStock()));
            priceTextfieldModPart.setText(String.valueOf(selectedPart.getPrice()));
            maxTextfieldModPart.setText(String.valueOf(selectedPart.getMax()));
            minTextfieldModPart.setText(String.valueOf(selectedPart.getMin()));
            if(selectedPart instanceof InHouse){
                inHouseMod.setSelected(true);
                inPart = (InHouse) selectedPart;
                machineIdTextfieldModPart.setText(String.valueOf(inPart.getMachineId()));
                System.out.println(((InHouse) selectedPart).getMachineId());
            }
            else {
                outSourcedMod.setSelected(true);
                machineIdTextModPart.setText("Company Name");
                machineIdTextfieldModPart.setText(((OutSourced) selectedPart).getCompanyName());
                outPart = (OutSourced) selectedPart;
                for(int i = 0; i < Inventory.getAllParts().size();i++){
                    if(selectedPart == Inventory.getAllParts().get(i)){
                        machineIdTextfieldModPart.setText(companyNameList.get(i));
                    }
                    else{
                        System.out.println("111");
                    }
                }

            }
        }
    }

    /*
    These are variables used in the modify product form.
     */
    @FXML
    private TextField idTextfieldModPro = new TextField();
    @FXML
    private TextField nameTextfieldModPro = new TextField();
    @FXML
    private TextField inventoryTextfieldModPro = new TextField();
    @FXML
    private TextField minTextfieldModPro = new TextField();
    @FXML
    private TextField maxTextfieldModPro = new TextField();
    @FXML
    private TextField priceTextfieldModPro = new TextField();
    /**
   This Function sets the view for modify product form.
   */
    @FXML
    private void modifyProductView(){
        if(Inventory.getAllProducts().isEmpty() || selectedProduct == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("There are no Products selected");
            selectedProduct = productListView.getSelectionModel().getSelectedItem();
        }
        else{
            idTextfieldModPro.setText(String.valueOf(selectedProduct.getId()));
            System.out.println(selectedProduct.getId() + "PROVIEW");
            nameTextfieldModPro.setText(selectedProduct.getName());
            inventoryTextfieldModPro.setText(String.valueOf(selectedProduct.getStock()));
            priceTextfieldModPro.setText(String.valueOf(selectedProduct.getPrice()));
            maxTextfieldModPro.setText(String.valueOf(selectedProduct.getMax()));
            minTextfieldModPro.setText(String.valueOf(selectedProduct.getMin()));

            associatedPartsTable.setItems(selectedProduct.getAllAssociatedParts());
        }
    }

    /**
    This function saves the modified part.
     Originally a runtime Exception: InvocationTargetException was thrown when a user would enter
     alphanumeric data in a field that required a number. I fixed the error by
     implementing an if statement with a function that checks to see if the
     data entered in the field is a number and displays an error if not.
     */
    @FXML
    private void saveModifiedPart(ActionEvent event){
        if(nameTextfieldModPart.getText().isEmpty() ||
                inventoryTextfieldModPart.getText().isEmpty() ||
                priceTextfieldModPart.getText().isEmpty() ||
                maxTextfieldModPart.getText().isEmpty() ||
                minTextfieldModPart.getText().isEmpty()) {
            Alert partAlert = new Alert(Alert.AlertType.WARNING);
            partAlert.setTitle("No Parts Available");
            partAlert.showAndWait();
        }
        else if(!isNumber(maxTextfieldModPart.getText()) || !isNumber(minTextfieldModPart.getText()) ||
                !isNumber(inventoryTextfieldModPart.getText()) || !isNumber(priceTextfieldModPart.getText())){
            Alert partAlert = new Alert(Alert.AlertType.WARNING);
            partAlert.setContentText("Price, Inventory , Min , and Max Fields must be numbers");
            partAlert.showAndWait();
        }
        else if(Integer.parseInt(maxTextfieldModPart.getText()) < Integer.parseInt(minTextfieldModPart.getText())){
            Alert partAlert = new Alert(Alert.AlertType.WARNING);
            partAlert.setTitle("Please be sure the Maximum is a higher Value than the Minimum requirement");
            partAlert.showAndWait();
        }

        else{
            try{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Modify Product");
                alert.setHeaderText("Would you like to save?");
                alert.showAndWait();
                if(alert.getResult() == ButtonType.OK) {
                    int id = Integer.parseInt(idTextfieldModPart.getText());
                    String currentPartName = nameTextfieldModPart.getText();
                    int currentStock = Integer.parseInt(inventoryTextfieldModPart.getText());
                    Double currentCost = Double.parseDouble(priceTextfieldModPart.getText());
                    int currentMin = Integer.parseInt(minTextfieldModPart.getText());
                    int currentMax = Integer.parseInt(maxTextfieldModPart.getText());
                    String machineId = machineIdTextfieldModPart.getText();
                    if(outSourcedMod.isSelected()){
                        OutSourced newPart = new OutSourced(id,currentPartName,currentCost,currentStock,currentMin,currentMax,machineId);
                        newPart.setCompanyName(machineId);
                        companyNameList.add(id-1,machineId);
                        Inventory.updatePart(id,newPart);

                    }
                    else if(inHouseMod.isSelected()){
                        if(!isNumber(machineIdTextfieldModPart.getText())){
                            Alert partAlert = new Alert(Alert.AlertType.WARNING);
                            partAlert.setContentText("Machine ID must be a number");
                            partAlert.showAndWait();
                        }
                        else{
                            InHouse newPart = new InHouse(id,currentPartName,currentCost,currentStock,currentMin,currentMax,Integer.parseInt(machineId));
                            newPart.setMachineId(Integer.parseInt(machineId));
                            companyNameList.add(id-1,machineId);
                            Inventory.updatePart(id,newPart);

                        }
                    }
                    currentWindow.close();
                    FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("MainFormH.fxml"));
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root,1100,1100);
                    currentWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
                    currentWindow.setTitle("Inventory Management System");
                    currentWindow.setScene(scene);
                    currentWindow.show();
                }
                else{
                    alert.close();

                }
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("Cant Load O Form");
            }
        }
    }
    /**
     This function saves the modified product.
     Originally a runtime Exception: InvocationTargetException was thrown when a user would enter
     alphanumeric data in a field that required a number. I fixed the error by
     implementing an if statement with a function that checks to see if the
     data entered in the field is a number and displays an error if not.
     */
    @FXML
    private void saveModifiedProduct(ActionEvent event){
        if(nameTextfieldModPro.getText().isEmpty() ||
                inventoryTextfieldModPro.getText().isEmpty() ||
                priceTextfieldModPro.getText().isEmpty() ||
                maxTextfieldModPro.getText().isEmpty() ||
                minTextfieldModPro.getText().isEmpty()) {
            System.out.println("No Parts");
            Alert partAlert = new Alert(Alert.AlertType.WARNING);
            partAlert.setTitle("No Parts Available");
            partAlert.showAndWait();
        }
        else if(!isNumber(inventoryTextfieldModPro.getText()) || !isNumber(maxTextfieldModPro.getText()) ||
                !isNumber(minTextfieldModPro.getText()) || !isNumber(priceTextfieldModPro.getText())){
            Alert partAlert = new Alert(Alert.AlertType.WARNING);
            partAlert.setContentText("Price, Inventory , Min , and Max Fields must be numbers");
            partAlert.showAndWait();
        }
        else if(Integer.parseInt(maxTextfieldModPro.getText()) <= Integer.parseInt(minTextfieldModPro.getText())){
            System.out.println("Max exceeds minimum");
            Alert partAlert = new Alert(Alert.AlertType.WARNING);
            partAlert.setTitle("No Parts Available Max vs Min");
            partAlert.showAndWait();
        }

        else{
            try{
                int id = Integer.parseInt(idTextfieldModPro.getText());
                String proName = nameTextfieldModPro.getText();
                int stock = Integer.parseInt(inventoryTextfieldModPro.getText());
                Double cost = Double.parseDouble(priceTextfieldModPro.getText());
                int min = Integer.parseInt(minTextfieldModPro.getText());
                int max = Integer.parseInt(maxTextfieldModPro.getText());
                Product currentProduct = new Product(id,proName,cost,stock,min,max);

                ObservableList<Part> assoPart = associatedPartsTable.getItems();

                for (int i=0;i<assoPart.size();i++){
                    System.out.println(currentProduct.getName()+"Add to asso part");
                    currentProduct.addAssociatedPart(assoPart.get(i));
                }

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Add Product");
                alert.setHeaderText("Would you like to save?");
                alert.showAndWait();
                if(alert.getResult() == ButtonType.OK) {

//                    if(!(currentProduct.getAllAssociatedParts().isEmpty())){
//                        currentProduct.addAssociatedPart(associatedPartsTable.getSelectionModel().getSelectedItem());
//                        System.out.println("Ass parts");
//                        //associatedPartsTable.setItems();
//                    }
                    Inventory.updateProduct(id,currentProduct);



                    FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("MainFormH.fxml"));
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root,1100,1100);
                    currentWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
                    currentWindow.setTitle("Inventory Management System");
                    currentWindow.setScene(scene);
                    currentWindow.show();
                }
                else{
                    alert.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * This function loads the desired fixed settings for each form and table
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        /*
        This Automatically sets the inHouse radio button when adding a new Part.
                   ****PLEASE READ*****
        In order to change to outSourced Part the outSourced Radio button must be hit twice.
         */
        inHouseRadio.setSelected(true);
        /*
       setting the columns to specific Part values
        */
        partIdSet.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameSet.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvSet.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceSet.setCellValueFactory(new PropertyValueFactory<>("price"));
        /*
        setting all the Parts added so far to the table view
         */
        partListView.setItems(Inventory.getAllParts());
        /*
       setting the columns to specific Product values
        */
        productIdSet.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameSet.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvSet.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceSet.setCellValueFactory(new PropertyValueFactory<>("price"));
        productListView.setItems(Inventory.getAllProducts());
         /*
        setting all the Products added so far to the table view
         */
        partsProductIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsProductInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTableProduct.setItems(Inventory.getAllParts());
          /*
        setting all the associated Parts added so far to the table view
         */
        assocProductPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocProductInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        /*
        These functions are called to populate the modified Part and Product Forms with
        The selected Items.
         */
        modifyPartView();
        modifyProductView();

//        if (selectedProduct != null){
//            associatedPartsTable.setItems(selectedProduct.getAllAssociatedParts());
//        }
    }
}
