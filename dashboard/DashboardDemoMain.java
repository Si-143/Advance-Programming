package uk.ac.gre.comp1549.dashboard;

import MVC.Controller;
import MVC.Model;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import uk.ac.gre.comp1549.dashboard.controls.*;
import uk.ac.gre.comp1549.dashboard.events.*;
import uk.ac.gre.comp1549.dashboard.scriptreader.DashboardEventGeneratorFromXML;

/**
 * DashboardDemoMain.java Contains the main method for the Dashboard demo
 * application. It: a) provides the controller screen which allows user input
 * which is passed to the display indicators, b) allows the user to run the XML
 * script which changes indicator values, c) creates the dashboard JFrame and
 * adds display indicators to it.
 *
 * @author Gill Windall
 * @version 2.0
 */
public class DashboardDemoMain extends JFrame {

    /**
     * @return the XML_SCRIPT
     */
    public static String getXML_SCRIPT() {
        return XML_SCRIPT;
    }

    /**
     * @param aXML_SCRIPT the XML_SCRIPT to set
     */
    public static void setXML_SCRIPT(String aXML_SCRIPT) {
        XML_SCRIPT = aXML_SCRIPT;
    }

   
    private int counter;

    /**
     * Name of the XML script file - change here if you want to use a different
     * ;
     */
    private static String XML_SCRIPT = "dashboard_script.xml";
    // fields that appear on the control panel
    private JTextField txtSpeedValueInput;
    private JTextField txtDialValueInput;
    private JButton btnScript;
    private JTextField AddPetrolInput;
    private JButton Add;
    private JLabel lblWarning;

    // fields that appear on the dashboard itself
    private DialSpeed speedDial;
    private DialPanel Dial;
    private DialPanelHalf petrolDial;
    private BarPanel petrolBar;
    private Digital digital;

    /**
     * Constructor. Does maybe more work than is good for a constructor.
     */
    public DashboardDemoMain() {
        // Set up the frame for the controller
        setTitle("Dashboard demonstration controller");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Speed GUI 
        JPanel panel = new JPanel();
        panel.add(new JLabel("Speed Value:"));
        txtSpeedValueInput = new JTextField("0", 3);
        panel.add(txtSpeedValueInput);
        DocumentListener speedListener = new SpeedValueListener();
        txtSpeedValueInput.getDocument().addDocumentListener(speedListener);


        // New Pressure GUI 
        panel.add(new JLabel("Pressure:"));
        txtDialValueInput = new JTextField("0", 3);
        panel.add(txtDialValueInput);
        DocumentListener DialListener = new DialValueListener();
        txtDialValueInput.getDocument().addDocumentListener(DialListener);

        //add Button
        panel.add(new JLabel("Add Petrol:"));
        AddPetrolInput = new JTextField("0", 3);
        panel.add(AddPetrolInput);
        Add = new JButton("Add");
        panel.add(Add);
        add(panel);

        Add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Context AddPetrol = new Context(new addOperation());
//                setCounter(getCounter() + 1);
                try {
                    setCounter(getCounter() + Integer.parseInt(AddPetrolInput.getText()));
                } catch (NumberFormatException nfe) {// catches any number formating issues e.g if the user inputs a charater that can not be converted into a integer
                    System.out.println(nfe.toString());
                }
                int newvalue = AddPetrol.executeStrategy(getCounter(), Integer.parseInt(AddPetrolInput.getText()) );
                Controller.setPetrol(newvalue, petrolDial, petrolBar);
            }
        });

        btnScript = new JButton("Run XML Script");

        // When the button is read the XML script will be run
        btnScript.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread() {
                    public void run() {
                        Controller.runXMLScript(speedDial, Dial, digital, petrolDial, petrolBar);// calls the "runXMLScript" method from the Controller class 
                    }
                }.start();
            }
        });
        panel.add(btnScript);
        add(panel);
        pack();

        setLocationRelativeTo(null); // display in centre of screen
        this.setVisible(true);

        // Set up the dashboard screen        
        JFrame dashboard = new JFrame("Train dashboard");
        dashboard.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        dashboard.setLayout(new FlowLayout());

        // add the speed Dial
        speedDial = new DialSpeed();
        speedDial.setLabel("SPEED");
        dashboard.add(speedDial);

        // add the petrol Dial
        petrolDial = new DialPanelHalf();
        petrolDial.setLabel("Petrol Dial");
        petrolDial.setValue(0);
        dashboard.add(petrolDial);

        // add new Dial
        Dial = new DialPanel();
        Dial.setLabel("Pressure");
        dashboard.add(Dial);

        // add the petrol Bar
        petrolBar = new BarPanel();
        petrolBar.setLabel("Petrol Bar");
        petrolBar.setValue(100);
        dashboard.add(petrolBar);

        // add the Digital clock
        digital = new Digital();
        digital.setValue(0);
        digital.setLabel("Digital Display Speed");
        dashboard.add(digital);
        dashboard.pack();
        // centre the dashboard frame above the control frame
        Point topLeft = this.getLocationOnScreen(); // top left of control frame (this)
        int hControl = this.getHeight(); // height of control frame (this)
        int wControl = this.getWidth(); // width of control frame (this)
        int hDash = dashboard.getHeight(); // height of dashboard frame 
        int wDash = dashboard.getWidth(); // width of dashboard frame 
        // calculate where top left of the dashboard goes to centre it over the control frame
        Point p2 = new Point((int) topLeft.getX() - (wDash - wControl) / 2, (int) topLeft.getY() - (hDash + hControl));
        dashboard.setLocation(p2);
        dashboard.setVisible(true);

    }

    /**
     * Run the XML script file which generates events for the dashboard
     * indicators
     */
    /**
     * Set the speed value to the value entered in the textfield.
     */
    public void setSpeed(DialSpeed speedDial, JTextField txtSpeedValueInput) {
        Controller.setSpeed(speedDial, txtSpeedValueInput);
        // don't set the speed if the input can't be parsed
    }

    /**
     * Set the petrol value to the value entered in the textfield.
     */
    public void setPetrol(int newvalue, DialPanelHalf petrolDial, BarPanel petrolBar) {
        Controller.setPetrol(newvalue, petrolDial, petrolBar);
    }

    // Set new dial value 
    public void setDial(DialPanel Dial, JTextField txtDialValueInput) {
        Controller.setDial(Dial, txtDialValueInput);
        // don't set the speed if the input can't be parsed
    }

    public DialPanel getDialPanel() {
        return Dial;
    }

    public void setDisplay(Digital digital, JTextField txtDialValueInput) {
        Controller.setDisplay(digital, txtDialValueInput);
        // don't set the speed if the input can't be parsed
    }

    /**
     * Respond to user input in the Speed textfield
     */
    private class SpeedValueListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent event) {
            Controller.setSpeed(speedDial, txtSpeedValueInput);
        }

        @Override
        public void removeUpdate(DocumentEvent event) {
            Controller.setSpeed(speedDial, txtSpeedValueInput);
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
//            Controller.setPetrol(counter, petrolDial, petrolBar,txtPetrolValueInput);

            Context AddPetrol = new Context(new addOperation());
            try {
                setCounter(getCounter());
            } catch (NumberFormatException nfe) {
                System.out.println(nfe.toString());
            }
            int newvalue = AddPetrol.executeStrategy(getCounter(), 0);
            Controller.setPetrol(newvalue, petrolDial, petrolBar);
        }

        @Override
        public void removeUpdate(DocumentEvent event) {
//            Controller.setPetrol(counter, petrolDial, petrolBar,txtPetrolValueInput);

            Context AddPetrol = new Context(new addOperation());
            try {
                setCounter(getCounter());
            } catch (NumberFormatException nfe) {
                System.out.println(nfe.toString());
            }
            int newvalue = AddPetrol.executeStrategy(getCounter(), 0);
            Controller.setPetrol(newvalue, petrolDial, petrolBar);
        }

        @Override
        public void changedUpdate(DocumentEvent event) {
        }
    }

    //new Dial Listener
    private class DialValueListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent event) {
            Controller.setDial(Dial, txtDialValueInput);
        }

        @Override
        public void removeUpdate(DocumentEvent event) {
            Controller.setDial(Dial, txtDialValueInput);
        }

        @Override
        public void changedUpdate(DocumentEvent event) {
        }
    }

    private class DigitalValueListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent event) {
            Controller.setDisplay(digital, txtDialValueInput);
        }

        @Override
        public void removeUpdate(DocumentEvent event) {
            Controller.setDisplay(digital, txtDialValueInput);
        }

        @Override
        public void changedUpdate(DocumentEvent event) {
        }
    }

//    /**
//     *
//     * @param args - unused
//     */
    public static void main(String[] args) {
        final DashboardDemoMain me = new DashboardDemoMain();
    }

    /**
     * @return the counter
     */
    public int getCounter() {
        return counter;
    }

    /**
     * @param counter the counter to set
     */
    public void setCounter(int counter) {
        this.counter = counter;
    }

    /**
     * @return the txtSpeedValueInput
     */
    public JTextField getTxtSpeedValueInput() {
        return txtSpeedValueInput;
    }

    /**
     * @param txtSpeedValueInput the txtSpeedValueInput to set
     */
    public void setTxtSpeedValueInput(JTextField txtSpeedValueInput) {
        this.txtSpeedValueInput = txtSpeedValueInput;
    }

    /**
     * @return the txtDialValueInput
     */
    public JTextField getTxtDialValueInput() {
        return txtDialValueInput;
    }

    /**
     * @param txtDialValueInput the txtDialValueInput to set
     */
    public void setTxtDialValueInput(JTextField txtDialValueInput) {
        this.txtDialValueInput = txtDialValueInput;
    }


    public JTextField getTxtDigitalInput() {
        return txtDialValueInput;
    }

    /**
     * @param txtDialValueInput3 the txtDialValueInput to set
     */
    public void setTxtDigitalInput(JTextField txtDialValueInput) {
        this.txtDialValueInput = txtDialValueInput;
    }

    /**
     * @return the btnScript
     */
    public JButton getBtnScript() {
        return btnScript;
    }

    /**
     * @param btnScript the btnScript to set
     */
    public void setBtnScript(JButton btnScript) {
        this.btnScript = btnScript;
    }

    /**
     * @return the AddPetrolInput
     */
    public JTextField getAddPetrolInput() {
        return AddPetrolInput;
    }

    /**
     * @param AddPetrolInput the AddPetrolInput to set
     */
    public void setAddPetrolInput(JTextField AddPetrolInput) {
        this.AddPetrolInput = AddPetrolInput;
    }

    /**
     * @return the Add
     */
    public JButton getAdd() {
        return Add;
    }

    /**
     * @param Add the Add to set
     */
    public void setAdd(JButton Add) {
        this.Add = Add;
    }

    /**
     * @return the lblWarning
     */
    public JLabel getLblWarning() {
        return lblWarning;
    }

    /**
     * @param lblWarning the lblWarning to set
     */
    public void setLblWarning(JLabel lblWarning) {
        this.lblWarning = lblWarning;
    }

    /**
     * @return the speedDial
     */
    public DialPanel getSpeedDial() {
        return speedDial;
    }

    /**
     * @param speedDial the speedDial to set
     */
    public void setSpeedDial(DialSpeed speedDial) {
        this.speedDial = speedDial;
    }

    /**
     * @return the Dial
     */
    public DialPanel getDial() {
        return Dial;
    }

    /**
     * @param Dial the Dial to set
     */
    public void setDial(DialPanel Dial) {
        this.Dial = Dial;
    }

    /**
     * @return the petrolDial
     */
    public DialPanelHalf getPetrolDial() {
        return petrolDial;
    }

    /**
     * @param petrolDial the petrolDial to set
     */
    public void setPetrolDial(DialPanelHalf petrolDial) {
        this.petrolDial = petrolDial;
    }

    /**
     * @return the petrolBar
     */
    public BarPanel getPetrolBar() {
        return petrolBar;
    }

    /**
     * @param petrolBar the petrolBar to set
     */
    public void setPetrolBar(BarPanel petrolBar) {
        this.petrolBar = petrolBar;
    }

    /**
     * @return the digital
     */
    public Digital getDigital() {
        return digital;
    }

    /**
     * @param digital the digital to set
     */
    public void setDigital(Digital digital) {
        this.digital = digital;
    }
}
