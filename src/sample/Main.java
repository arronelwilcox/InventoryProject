/**Arron Wilcox
 Project Software 1 -C482
Inventory Management System
4/27/2021.
 */

package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is the main class which loads the main GUI.<BR>
 * Future Modifications: This GUI could be updated by implementing saving data to a file,and implementing
 *  a checkout system.In this check out system rewards points are rewarded to a
 * specific user for every part and/or product checked out. These rewards points could be applied to future checkouts,
 * in order to make parts and products cheaper.<BR>
 * RunTime Error: I found Null Pointer Exceptions through out all of my code due to not
 * Intilaizing all of the FXML variables after making them private, and also from not checking to see if selectedParts and
 * selectedProducts were null. I fixed this with if statements throughout all the functions. To ensure the
 * variables were set to specific values that were selected in the table views.
 *  Also, I received Number Format Exceptions and Invocation Format Errors due
 *  to the Machine ID not being a number, along with inventory,max,price,and min
 *  variables being entered as alphanumeric texts. I fixed all of these issues by
 *  creating a function to make sure those parameters were indeed numbers, and if not an
 *  alert box pops up prompting the user to enter thr correct field.
 * Current File Functions: This file loads the main GUI for the inventory.
 */
public class Main extends Application {
    /**
     * This creates the actual stage for the GUI/
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("MainFormH.fxml"));
            Parent root = fxmlLoader.load();
            primaryStage.setTitle("Inventory Management System");
            primaryStage.setScene(new Scene(root, 1100, 1100));
            primaryStage.show();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Cant load MAin");
        }

    }

    /**
     * This function loads the created stage for the main gui fxml file
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
