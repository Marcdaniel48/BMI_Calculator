package com.marcdanieldialogo.beans;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Marc-Daniel
 */
public class BmiTableRowBean 
{
    private StringProperty category;
    private StringProperty bmi;
    private StringProperty riskToHealth;
    
    public BmiTableRowBean(String category, String bmi, String riskToHealth)
    {
        this.category = new SimpleStringProperty(category);
        this.bmi = new SimpleStringProperty(bmi);
        this.riskToHealth = new SimpleStringProperty(riskToHealth);
    }
    
    public BmiTableRowBean()
    {
        this("", "", "");
    }
    
    public void setCategory(String category)
    {
        this.category.set(category);
    }
    
    public String getCategory()
    {
        return category.get();
    }
    
    public StringProperty categoryProperty()
    {
        return category;
    }
    
    public void setBMI(String bmi)
    {
        this.bmi.set(bmi);
    }
    
    public String getBMI()
    {
        return bmi.get();
    }
    
    public StringProperty bmiProperty()
    {
        return bmi;
    }
    
    public void setRiskToHealth(String riskToHealth)
    {
        this.riskToHealth.set(riskToHealth);
    }
    
    public String getRiskToHealth()
    {
        return riskToHealth.get();
    }
    
    public StringProperty riskToHealthProperty()
    {
        return riskToHealth;
    }
}
