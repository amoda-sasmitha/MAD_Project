package Models;

import java.io.Serializable;



public class SavingModel implements Serializable {

    int ID;
    String savingName;
    String savingDescription;
    double targetAmount;
    double startAmount;
    double currentAmount;

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public SavingModel(int ID, String savingName, String savingDescription, double targetAmount, double startAmount) {
        this.ID = ID;
        this.savingName = savingName;
        this.savingDescription = savingDescription;
        this.targetAmount = targetAmount;
        this.startAmount = startAmount;
    }

    public SavingModel() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSavingName() {
        return savingName;
    }

    public void setSavingName(String savingName) {
        this.savingName = savingName;
    }

    public String getSavingDescription() {
        return savingDescription;
    }

    public void setSavingDescription(String savingDescription) {
        this.savingDescription = savingDescription;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public double getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(double startAmount) {
        this.startAmount = startAmount;
    }
}
