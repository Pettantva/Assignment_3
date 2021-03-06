package control;

//import model.DummyLocalizer;
import model.P1AILocalizer;
//import model.RobotGrid;
//import model.Sensor;
import view.RobotLocalizationViewer;

public class Main {
	
	public static void main( String[] args) {
		EstimatorInterface l = new P1AILocalizer( 8, 8, 4);

		RobotLocalizationViewer viewer = new RobotLocalizationViewer( l);
		/*
		 * this thread controls the continuous update. If it is not started, 
		 * you can only click through your localisation stepwise
		 */
		new LocalizationDriver( 500, viewer).start();
	}
}	