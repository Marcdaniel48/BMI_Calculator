package com.marcdanieldialogo;

import com.marcdanieldialogo.controller.BMICalculatorController;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class allows the JavaFX application to begin.
 * @author Marc-Daniel
 */
public class MainApp extends Application
{
    // Logger
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    
    private Stage primaryStage;
    private Parent rootPane;
    
    /**
     * The main method
     * @param args 
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    /**
     * Sets the primary stage then calls another method to create the scene.
     * @param primaryStage
     * @throws Exception 
     */
    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        this.primaryStage = primaryStage;
        
        initRootLayout();
        
        // Set title of stage
        primaryStage.setTitle(ResourceBundle.getBundle("BMICalculatorProps").getString("CalculatorTitle"));
        
        // Show
        primaryStage.show();
    }
    
    /**
     * Loads the layout and controller.
     */
    public void initRootLayout() 
    {
        try 
        {
            // Instantiate a FXMLLoader object
            FXMLLoader loader = new FXMLLoader();

            // Configure the FXMLLoader with the i18n locale resource bundles
            loader.setResources(ResourceBundle.getBundle("BMICalculatorProps"));

            // Connect the FXMLLoader to the fxml file that is stored in the jar
            loader.setLocation(MainApp.class.getResource("/fxml/BMICalculator.fxml"));

            // The load command returns a reference to the root pane of the fxml file
            rootPane = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootPane);

            // Put the Scene on the Stage
            primaryStage.setScene(scene);

            // Retrive a refernce to the controller from the FXMLLoader
            BMICalculatorController controller = loader.getController();

            // Before displaying the Scene, send a message to the the controller
            // to retrieve the table from the DAO connect it to the TableView
            // that represents a table view of data.
            controller.displayTheTable();
        } 
        catch (IOException ex) 
        {
            log.error("Error in MainApp", ex);
        }
        
    }
}
