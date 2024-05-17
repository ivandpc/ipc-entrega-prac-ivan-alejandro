package javafxmlapplication.controller;

import model.Charge;

/**
 *
 * @author ivand
 */
public class ChargeHolder {

    private final static ChargeHolder INSTANCE = new ChargeHolder();
    private Charge charge;

    // Private constructor to prevent instantiation from other classes
    private ChargeHolder() {
    }

    // Method to get the single instance of ChargeHolder
    public static ChargeHolder getInstance() {
        return INSTANCE;
    }

    // Method to set the Charge object
    public void setCharge(Charge charge) {
        this.charge = charge;
    }

    // Method to get the Charge object
    public Charge getCharge() {
        return this.charge;
    }
}
