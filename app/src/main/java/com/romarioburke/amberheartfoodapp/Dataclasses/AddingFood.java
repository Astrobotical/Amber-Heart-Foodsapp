package com.romarioburke.amberheartfoodapp.Dataclasses;

public class AddingFood {
    private String FoodCategory;
    private String TargetedStudent;
    private boolean Choice = true;
    private String FoodName;
    private String Description;

    public String getFoodCategory() {
        return FoodCategory;
    }

    public String getTargetedStudent() {
        return TargetedStudent;
    }

    public void setFoodCategory(String foodCategory) {
        FoodCategory = foodCategory;
    }

    public void setTargetedStudent(String targetedStudent) {
        TargetedStudent = targetedStudent;
    }

    public boolean isChoice() {
        return Choice;
    }

    public void setChoice(boolean choice) {
        Choice = choice;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }
}
