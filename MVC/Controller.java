
package MVC;

import java.awt.event.*;
import javax.swing.JTextField;
import uk.ac.gre.comp1549.dashboard.DashboardDemoMain;
import uk.ac.gre.comp1549.dashboard.controls.BarPanel;
import uk.ac.gre.comp1549.dashboard.controls.DialPanel;
import uk.ac.gre.comp1549.dashboard.controls.DialPanelHalf;
import uk.ac.gre.comp1549.dashboard.controls.DialSpeed;
import uk.ac.gre.comp1549.dashboard.controls.Digital;
import uk.ac.gre.comp1549.dashboard.scriptreader.DashboardEventGeneratorFromXML;

// Controller knows about both Model and View
// It receives the event originated in the
// View and gets the number from the View and
// passes it to the Model
public class Controller implements ActionListener {
private static Controller instance = null;

private Controller(){}
    
    
public static synchronized Controller getInstance(){// Singleton method  
    if (instance == null){ // checks if their is a instance that already exist 
        instance = new Controller();// if none exist then it will create one
    }
    return instance;// returns the instance
}

    DashboardEventGeneratorFromXML myModel;// the model
    DashboardDemoMain myView; //  the view 

    public Controller(DashboardDemoMain v, DashboardEventGeneratorFromXML m) {
        myModel = m;
        myView = v;
    }

    public static void runXMLScript(DialPanel speedDial, DialPanel Dial, Digital digital, DialPanelHalf petrolDial, BarPanel petrolBar) {
        Model.runXMLScript(speedDial, Dial, digital, petrolDial, petrolBar);
    }// call the method from the model class, which hold the method to run the XML script

    public void actionPerformed(ActionEvent ae) {
//      myModel.addToCount(myView.getNumb());
    }

    public static void setSpeed(DialSpeed speedDial, JTextField txtSpeedValueInput) {

        Model.setSpeed(speedDial, txtSpeedValueInput);
        // call the set speed method from the model class. 
    }

    public static DialPanel getDialPanel(DialPanel Dial) {
        Model.getDialPanel(Dial);
        return Dial;
    }

    public static void setDisplay(Digital digital, JTextField txtDialValueInput) {
        Model.setDisplay(digital, txtDialValueInput);

    }

    public static void setPetrol(int newvalue, DialPanelHalf petrolDial, BarPanel petrolBar) {
        Model.setPetrol(newvalue, petrolDial, petrolBar);
    }

    public static void setDial(DialPanel Dial, JTextField txtDialValueInput) {
        Model.setDial(Dial, txtDialValueInput);
    }

}
