package model;

import control.EstimatorInterface;

public class P1AILocalizer implements EstimatorInterface {
	
	private int rows;
	private int cols;
	private int heads;
	private RobotGrid robot;
	private Sensor sensor;
	private DENAIEstimator prob;

	public P1AILocalizer( int rows, int cols, int headss) {
		this.rows = rows;
		this.cols = cols;
		this.heads = heads;
		this.robot  = new RobotGrid(rows, cols, heads);
		this.sensor = new Sensor(this.robot, rows, cols);
		this.prob = new DENAIEstimator(this.sensor);
		
	}	
	
	public int getNumRows() {
		return this.rows;
	}
	
	public int getNumCols() {
		return this.cols;
	}
	
	public int getNumHead() {
		return this.heads;
	}
	
	public double getTProb( int x, int y, int h, int nX, int nY, int nH) {
		return this.robot.getTransProb(x, y, h, nX, nY, nH);
	}

	public double getOrXY( int rX, int rY, int x, int y, int h) {
		if(rX == -1 || rY == -1){
			return this.prob.getNothingProb(x, y, h);
		}
		else{
			return this.prob.getStateProb(rX, rY, x, y, h);
		}
	}

	public int[] getCurrentTruePosition() {
		int[] pos = new int[2];
		pos[0] = this.robot.getRow();
		pos[1] = this.robot.getCol();
		return pos;

	}

	public int[] getCurrentReading() {
		int[] read = new int[2];
		read[0] = this.sensor.getRow();
		read[1] = this.sensor.getCol();
		return read;
	}

	public double getCurrentProb( int x, int y) {
		return this.prob.getSummedProb(x,y);
	}
	
	public void update() {
		this.robot.update();  //makes random move according to rules
		this.sensor.update(this.robot); //reads the new state
		this.prob.update(this.sensor); //uppdates the probabilities depending on the sensor read
						//this is the smart one. with the matrixes, i think
		System.out.println("Nothing is happening, no model to go for...");
	}
	
	
}