package com.marcdanieldialogo.controller;

import com.marcdanieldialogo.beans.BmiTableRowBean;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;

/**
 * Controller class for BMICalculator.fxml
 * 
 * @author Marc-Daniel
 */
public class BMICalculatorController 
{

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="bmiTableView"
    private TableView<BmiTableRowBean> bmiTableView; // Value injected by FXMLLoader

    @FXML // fx:id="categoryColumn"
    private TableColumn<BmiTableRowBean, String> categoryColumn; // Value injected by FXMLLoader

    @FXML // fx:id="bmiColumn"
    private TableColumn<BmiTableRowBean, String> bmiColumn; // Value injected by FXMLLoader

    @FXML // fx:id="riskColumn"
    private TableColumn<BmiTableRowBean, String> riskColumn; // Value injected by FXMLLoader

    @FXML // fx:id="heightStackPane"
    private StackPane heightStackPane; // Value injected by FXMLLoader
        
    @FXML // fx:id="heightTextField"
    private TextField heightTextField; // Value injected by FXMLLoader

    @FXML // fx:id="weightTextField"
    private TextField weightTextField; // Value injected by FXMLLoader

    @FXML // fx:id="metricRadioButton"
    private RadioButton metricRadioButton; // Value injected by FXMLLoader

    @FXML // fx:id="englishRadioButton"
    private RadioButton englishRadioButton; // Value injected by FXMLLoader

    @FXML // fx:id="pregnantCheckBox"
    private CheckBox pregnantCheckBox; // Value injected by FXMLLoader
    
    @FXML // fx:id="heightLabel"
    private Label heightLabel; // Value injected by FXMLLoader
    
    @FXML // fx:id="weightLabel"
    private Label weightLabel; // Value injected by FXMLLoader

    @FXML // fx:id="calculateBMIButton"
    private Button calculateBMIButton; // Value injected by FXMLLoader

    @FXML // fx:id="bmiResultLabel"
    private Label bmiResultLabel; // Value injected by FXMLLoader
    
    @FXML // fx:id="feetComboBox"
    private ComboBox<String> feetComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="inchComboBox"
    private ComboBox<String> inchComboBox; // Value injected by FXMLLoader
    
    @FXML // fx:id="ageCheckBox"
    private CheckBox ageCheckBox; // Value injected by FXMLLoader

    // A ToggleGroup that groups the metric and english radio buttons.
    ToggleGroup group;
    
    /**
     * Opens a dialog box that briefly describes this application, 
     * when the user the About option in the Help menu is selected.
     * @param event 
     */
    @FXML
    void handleAbout(ActionEvent event) 
    {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle(ResourceBundle.getBundle("AlertProps").getString("AboutTitle"));
        dialog.setHeaderText(ResourceBundle.getBundle("AlertProps").getString("AboutHeader"));
        dialog.setContentText(ResourceBundle.getBundle("AlertProps").getString("AboutContent"));
        dialog.show();
    }

    /**
     * Depending on the user's height, weight, this method calculates the user's BMI.
     * This method also takes into account if the user is at least 18 years old, and if the user is pregnant.
     * @param event 
     */
    @FXML
    void handleCalculateBMI(ActionEvent event) 
    {
        double height = -1;
        double weight = -1;
        double bmi = -1;
        
        // Validation: If the application is using the Metric mode, make sure that both the height and weight fields can be parsed into positive doubles.
        // If they both can't be parsed, then pop up an appropriate alert dialog.
        if(metricRadioButton.isSelected() && (!isDouble(heightTextField.getText()) || !isDouble(weightTextField.getText())))
        {
            alertDialogInvalidHeightWeightInput();
        }
        // Validation: If the application is using the English mode, make sure that the user has selected values for both the feet and inch combo boxes, and
        // make sure that the weight field can be parsed into a positive double.
        // If there is no selected values for the combo boxes, or if the weight field can't be parsed, then pop up an appropriate alert dialog.
        else if(englishRadioButton.isSelected() && (feetComboBox.getValue() == null || inchComboBox.getValue() == null || !isDouble(weightTextField.getText())))
        {
            alertDialogInvalidHeightWeightInput();
        }
        // Validation: Checks if the current user is pregnant. If the user is pregnant, then pop up an appropriate alert dialog.
        else if(pregnantCheckBox.isSelected())
        {
            alertDialogPregnant();
        }
        // BMI calculation logic for if the user is using the Metric system.
        else if(metricRadioButton.isSelected())
        {
            // Validation: Checks if the user is at least 18 years old. If the user isn't, then pop up an appropriate alert dialog.
            if(!ageCheckBox.isSelected())
            {
                alertDialogUnderage();
            }
            else
            {
                // Parses the values in the height and weight text fields into doubles.
                height = Double.parseDouble(heightTextField.getText());
                weight = Double.parseDouble(weightTextField.getText());

                // Validation: If the user entered a height lower than 0.914 meters OR higher than 2.108 meters OR if the user entered a weight that's not between 50 and 500,
                // then pop up an appropriate alert dialog.
                if((height < 0.914) || (height > 2.108) || (weight <= 22.679) || (weight >= 226.796))
                {
                    alertDialogInvalidHeightWeight();
                }
                else
                {
                    bmi = (weight / (height * height));

                    bmiResultLabel.setText(bmi+"");
                }
            }
        }
        // BMI calculation logic for if the user is using the English system.
        else if(englishRadioButton.isSelected())
        {
            // Validation: Checks if the user is at least 18 years old. If the user isn't, then pop up an appropriate alert dialog.
            if(!ageCheckBox.isSelected())
            {
                alertDialogUnderage();
            }
            else
            {
                // Parses the values in the height and weight text fields into doubles.
                height = (Double.parseDouble(feetComboBox.getValue()) * 12) + (Double.parseDouble(inchComboBox.getValue()));
                weight = Double.parseDouble(weightTextField.getText());

                // Validation: If the user entered a height lower than 3 feet OR higher than 6 feet 11 inches OR if the user entered a weight that's not between 50 and 500,
                // then pop up an appropriate alert dialog.
                if((height < (3 * 12)) || (height > ((6 * 12) + 11)) || (weight <= 50) || (weight >= 500))
                {
                    alertDialogInvalidHeightWeight();
                }
                else
                {
                    bmi = ((weight * 703) / (height * height));

                    bmiResultLabel.setText(bmi+"");
                }
            }
        }
    }

    /**
     * When the user clicks on the exit button or selects the close option in the File menu, the application will close.
     * @param event 
     */
    @FXML
    void handleClose(ActionEvent event) 
    {
        Platform.exit();
    }

    /**
     * When the user selects the English measurement system, two combo boxes for selecting the user's height in foot and inches will appear. 
     * @param event 
     */
    @FXML
    void handleEnglishSystem(ActionEvent event) 
    {
        heightStackPane.getChildren().get(0).setVisible(false);
        heightStackPane.getChildren().get(1).setVisible(true);
        heightLabel.setText(ResourceBundle.getBundle("BMICalculatorProps").getString("HeightEnglish"));
        weightLabel.setText(ResourceBundle.getBundle("BMICalculatorProps").getString("WeightEnglish"));
    }

    /**
     * When the user selects the Metric measurement system, a text field for inputting the user's height in meters will appear.
     * @param event 
     */
    @FXML
    void handleMetricSystem(ActionEvent event) 
    {
        heightStackPane.getChildren().get(0).setVisible(true);
        heightStackPane.getChildren().get(1).setVisible(false);
        heightLabel.setText(ResourceBundle.getBundle("BMICalculatorProps").getString("HeightMetric"));
        weightLabel.setText(ResourceBundle.getBundle("BMICalculatorProps").getString("WeightMetric"));
    }
    
    /**
     * Initializes this controller class.
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() 
    {
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        bmiColumn.setCellValueFactory(cellData -> cellData.getValue().bmiProperty());
        riskColumn.setCellValueFactory(cellData -> cellData.getValue().riskToHealthProperty());
        
        // Initializing a toggle group and setting it to the to the measurement system radio buttons
        group = new ToggleGroup();
        metricRadioButton.setToggleGroup(group);
        englishRadioButton.setToggleGroup(group);
        
        // The app is assuming by default that the user is at least 18 years old
        ageCheckBox.setSelected(true);
        
        // The default measurement system is metric
        metricRadioButton.setSelected(true);
        heightLabel.setText(ResourceBundle.getBundle("BMICalculatorProps").getString("HeightMetric"));
        weightLabel.setText(ResourceBundle.getBundle("BMICalculatorProps").getString("WeightMetric"));
        
        // Since metric is the default measurement system, the feet & inches comboboxes are hidden,
        // while the text field for the metric system remains visible.
        heightStackPane.getChildren().get(1).setVisible(false);
        
        for(int i = 3; i < 7; i++)
        {
            feetComboBox.getItems().add(String.valueOf(i));
        }
        for(int i = 0; i < 12; i++)
        {
            inchComboBox.getItems().add(String.valueOf(i));
        }
        
        displayTheTable();
    }
    
    /**
     * This method is responsible for inserting BMI category information into the BMI table.
     */
    public void displayTheTable()
    {
        ObservableList<BmiTableRowBean> rowBeans = FXCollections.observableArrayList();
        
        // Using information from the specs to create the table data.
        BmiTableRowBean underweight = new BmiTableRowBean("Underweight", "less than 18.5", "Increased health risk");
        BmiTableRowBean normalweight = new BmiTableRowBean("Normal weight", "18.5 to 24.9", "Least health risk");
        BmiTableRowBean overweight = new BmiTableRowBean("Overweight", "25.0 to 29.9", "Increased health risk");
        BmiTableRowBean obese1 = new BmiTableRowBean("Obese class I", "30.0 to 34.9", "High health risk");
        BmiTableRowBean obese2 = new BmiTableRowBean("Obese class II", "35.0 to 39.99", "Very high health risk");
        BmiTableRowBean obese3 = new BmiTableRowBean("Obese class III", "40.0 or more", "Extremely high health risk");
        
        rowBeans.add(underweight);
        rowBeans.add(normalweight);
        rowBeans.add(overweight);
        rowBeans.add(obese1);
        rowBeans.add(obese2);
        rowBeans.add(obese3);
        
        bmiTableView.setItems(rowBeans);
    }
    
    /**
     * Pops up an alert dialog telling the user that this application isn't suited for pregnant people.
     */
    private void alertDialogPregnant()
    {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle(ResourceBundle.getBundle("AlertProps").getString("AlertPregnantTitle"));
        dialog.setHeaderText(ResourceBundle.getBundle("AlertProps").getString("AlertPregnantHeader"));
        dialog.setContentText(ResourceBundle.getBundle("AlertProps").getString("AlertPregnantContent"));
        dialog.show();
    }
    
    /**
     * Pops up an alert dialog telling the user that this application isn't suited for people under the age of 18 years old.
     */
    private void alertDialogUnderage()
    {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle(ResourceBundle.getBundle("AlertProps").getString("AlertUnderageTitle"));
        dialog.setHeaderText(ResourceBundle.getBundle("AlertProps").getString("AlertUnderageHeader"));
        dialog.setContentText(ResourceBundle.getBundle("AlertProps").getString("AlertUnderageContent"));
        dialog.show();
    }
    
    /**
     * Pops up an alert dialog indicating that the user didn't enter valid numeric values into either the height or weight fields.
     */
    private void alertDialogInvalidHeightWeightInput()
    {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle(ResourceBundle.getBundle("AlertProps").getString("AlertInvalidInputTitle"));
        dialog.setHeaderText(ResourceBundle.getBundle("AlertProps").getString("AlertInvalidInputHeader"));
        dialog.setContentText(ResourceBundle.getBundle("AlertProps").getString("AlertInvalidInputContent"));
        dialog.show();
    }
    
    /**
     * Pops up an alert dialog indicating that the height and/or weight values that the user entered are either too high or too low.
     */
    private void alertDialogInvalidHeightWeight()
    {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle(ResourceBundle.getBundle("AlertProps").getString("AlertInvalidInputTitle"));
        dialog.setHeaderText(ResourceBundle.getBundle("AlertProps").getString("AlertInvalidHeightWeightHeader"));
        dialog.setContentText(ResourceBundle.getBundle("AlertProps").getString("AlertInvalidHeightWeightContent"));
        dialog.show();
    }
    
    /**
     * Method that's responsible for checking if a String value can be parsed into a positive double.
     * @param input
     * @return 
     */
    public boolean isDouble(String input)
    {
        return input.matches("[0-9]{1,13}(\\.[0-9]*)?");
    }
}
