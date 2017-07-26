package uk.ac.gre.comp1549.dashboard.events;

/**
 *Holds information about DashBoardEvents that can be
 * passed to DashBoardEventListeners.
 * @author Gill Windall
 */
public class DashBoardEvent {

    private String type; // type of event e.g "speed"
    private String value; // value of the event e.g. "30"

    public String getType() {
        return type;
    }
    // 

    public void setType(String type) {
        this.type = type;
    }
    //Invoking other constructors of the current class in your constructor with this.
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return "type:" + type + " value:" + value;
        // Printhing out the type of event and the given value of that event.
    }
    
}
