package Models;

import java.io.Serializable;

public class Overview implements Serializable {

    double inflow;
    double outflow;
    String startDate;
    String endDate;

    public Overview() {
        this.inflow = 0;
        this.outflow = 0;
    }

    public Overview(double inflow, double outflow, String startDate, String endDate) {
        this.inflow = inflow;
        this.outflow = outflow;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Overview(double inflow, double outflow) {
        this.inflow = inflow;
        this.outflow = outflow;
    }

    public double getInflow() {
        return inflow;
    }

    public void setInflow(double inflow) {
        this.inflow = inflow;
    }

    public double getOutflow() {
        return outflow;
    }

    public void setOutflow(double outflow) {
        this.outflow = outflow;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
