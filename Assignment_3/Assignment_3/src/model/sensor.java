package model;

import java.util.Random;

public class Sensor {
	private int rows;
	private int cols;
	private int rowr;
	private int colr;
	private RobotGrid robot;

	public Sensor(RobotGrid robot, int rows, int cols){
		this.robot = robot;
		this.rows = rows;
		this.cols = cols;
		scan();
	}
	
	public void update(RobotGrid robot){
		this.robot = robot;
		scan();
	}
	
	public int getRow(){
		return this.rowr;
	}
	
	public int getCol(){
		return this.colr;
	}
	
	private void scan(){
		Random rand = new Random();
		int r = this.robot.getRow();
		int c = this.robot.getCol();
		boolean done = false;
		double prob = rand.nextDouble();
		if(prob < 0.1){
			this.rowr = r;
			this.colr = c;
		}
		else{
			for(int i = r-1; i <= r+1; i++){
				if(!done){
					for(int j = c-1; j <= c+1; j++){
						if((!done) && (i >= 0) && (i < rows) && (j >= 0) && (j < cols)){
							prob = rand.nextDouble();
							if(prob < 0.05){
								this.rowr = i;
								this.colr = j;
								done = true;
								break;
							}
						}
					}
					if(done){
						break;
					}
				}
			}
			if(!done){
				for(int i = r-2; i <= r+2; i++){
					if(!done){
						for(int j = c-2; j <= c+2; j++){
							if((!done) && (i >= 0) && (i < rows) && (j >= 0) && (j < cols)){
								prob = rand.nextDouble();
								if(prob < 0.025){
									this.rowr = i;
									this.colr = j;
									done = true;
									break;
								}
							}
						}
						if(done){
							break;
						}
					}
				}
			}
		}
		if(!done){
			this.rowr = -1;
			this.colr = -1;
		}
	}
}
