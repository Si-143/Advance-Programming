/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import uk.ac.gre.comp1549.dashboard.DashboardDemoMain;
import uk.ac.gre.comp1549.dashboard.controls.BarPanel;
import uk.ac.gre.comp1549.dashboard.controls.DialPanel;
import uk.ac.gre.comp1549.dashboard.controls.DialPanelHalf;
import uk.ac.gre.comp1549.dashboard.controls.DialSpeed;
import uk.ac.gre.comp1549.dashboard.controls.Digital;
import uk.ac.gre.comp1549.dashboard.events.DashBoardEvent;
import uk.ac.gre.comp1549.dashboard.events.DashBoardEventListener;
import uk.ac.gre.comp1549.dashboard.scriptreader.DashboardEventGeneratorFromXML;

/**
 *
 * @author ts348
 */
public class Model {

    DialSpeed speedDial;
    JTextField txtSpeedValueInput;
    DialPanel Dial;
    JTextField txtDialValueInput;
    JTextField txtPetrolValueInput;
    DialPanelHalf petrolDial;
    BarPanel petrolBar;
    Digital digital;
    JTextField txtDigitalInput;
    int counter;
// holds all of the methods that will be called by the Controller class. 
    public static void runXMLScript(DialPanel speedDial, DialPanel Dial, Digital digital, DialPanelHalf petrolDial, BarPanel petrolBar) {
        try {
            DashboardEventGeneratorFromXML dbegXML = new DashboardEventGeneratorFromXML();

            // Register for speed events from the XML script file
            DashBoardEventListener dbelSpeed = new DashBoardEventListener() {
                @Override
                public void processDashBoardEvent(Object originator, DashBoardEvent dbe) {
                    speedDial.setValue(Integer.parseInt(dbe.getValue()));
                    Dial.setValue(Integer.parseInt(dbe.getValue()));
                    digital.setValue(Integer.parseInt(dbe.getValue()));
                }
            };
            dbegXML.registerDashBoardEventListener("speed", dbelSpeed);

            // Register for petrol events from the XML script file
            DashBoardEventListener dbelPetril = new DashBoardEventListener() {
                @Override
                public void processDashBoardEvent(Object originator, DashBoardEvent dbe) {
                    petrolDial.setValue(Integer.parseInt(dbe.getValue()));
                    petrolBar.setValue(Integer.parseInt(dbe.getValue()));
                }
            };
            dbegXML.registerDashBoardEventListener("petrol", dbelPetril);

            // Process the script file - it willgenerate events as it runs
            dbegXML.processScriptFile(DashboardDemoMain.getXML_SCRIPT());

        } catch (Exception ex) {
            Logger.getLogger(DashboardDemoMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Set the speed value to the value entered in the textfield.
     *
     * @param speedDial
     * @param txtSpeedValueInput
     */
    public static void setSpeed(DialSpeed speedDial, JTextField txtSpeedValueInput) {
        try {
            int value = Integer.parseInt(txtSpeedValueInput.getText().trim());
            speedDial.setValue(value);
        } catch (NumberFormatException e) {
        }
        // don't set the speed if the input can't be parsed
    }

    /**
     * Set the petrol value to the value entered in the textfield.
     */
    public static void setPetrol(int newvalue, DialPanelHalf petrolDial, BarPanel petrolBar) {
        try {
            //int value = Integer.parseInt(txtPetrolValueInput.getText().trim());
            int value = newvalue;
            petrolDial.setValue(value);
            petrolBar.setValue(value);
        } catch (NumberFormatException e) {
        }
        // don't set the speed if the input can't be parsed
    }

    // Set new dial value 
    public static void setDial(DialPanel Dial, JTextField txtDialValueInput) {
        try {
            int value = Integer.parseInt(txtDialValueInput.getText().trim());
            Dial.setValue(value);
        } catch (NumberFormatException e) {
        }
        // don't set the speed if the input can't be parsed
    }

    public static DialPanel getDialPanel(DialPanel Dial) {
        return Dial;
    }

    public static void setDisplay(Digital digital, JTextField txtDigitalInput) {
        try {
            int value = Integer.parseInt(txtDigitalInput.getText().trim());
            digital.setValue(value);
        } catch (NumberFormatException e) {
        }
        // don't set the speed if the input can't be parsed
    }

    /**
     * Respond to user input in the Speed textfield
     */
    private class SpeedValueListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent event) {
            setSpeed(speedDial, txtSpeedValueInput);
        }

        @Override
        public void removeUpdate(DocumentEvent event) {
            setSpeed(speedDial, txtSpeedValueInput);
        }

        @Override
        public void changedUpdate(DocumentEvent event) {
        }
    }

    /**
     * Respond to user input in the Petrol textfield
     */
    private class PetrolValueListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent event) {
            setPetrol(counter, petrolDial, petrolBar);
        }

        @Override
        public void removeUpdate(DocumentEvent event) {
            setPetrol(counter, petrolDial, petrolBar);
        }

        @Override
        public void changedUpdate(DocumentEvent event) {
        }
    }

    //new Dial Listener
    private class DialValueListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent event) {
            setDial(Dial, txtDialValueInput);
        }

        @Override
        public void removeUpdate(DocumentEvent event) {
            setDial(Dial, txtDialValueInput);
        }

        @Override
        public void changedUpdate(DocumentEvent event) {
        }
    }

    private class DigitalValueListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent event) {
            setDisplay(digital, txtDigitalInput);
        }

        @Override
        public void removeUpdate(DocumentEvent event) {
            setDisplay(digital, txtDigitalInput);
        }

        @Override
        public void changedUpdate(DocumentEvent event) {
        }
    }

    /**
     *
     * @param args - unused
     */
}
