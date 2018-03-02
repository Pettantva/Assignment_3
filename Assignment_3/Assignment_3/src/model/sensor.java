package model;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Sensor {
	private int rows;
	private int cols;
	private int rowr;
	private int colr;
	private RobotGrid robot;
	private Random rand;
	private double[][] nM;

	public Sensor(RobotGrid robot, int rows, int cols){
		this.robot = robot;
		this.rows = rows;
		this.cols = cols;
		this.rand = new Random();
		this.nM = nothingProb();
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
	
	public int getCols(){
		return this.cols;
	}
	
	public int getRows(){
		return this.rows;
	}
	
	private void scan(){
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
	public double[][] observationMatrix(int r, int c){
		if(r == -1 || c == -1){
			return this.nM;
		}
		else{
			List<Double> oV = new ArrayList<Double>();
			int size = rows*cols*4;
			double[][] oM = new double[size][size];
			double noll = 0;
			for(int i = 0; i < rows; i++){
				for(int j = 0; j < cols; j++){
					for(int h = 0; h < 4; h++){
						int d = distance(i,j,r,c);
						if(d == 0){
							oV.add(0.1);
						}
						else if(d == 1){
							oV.add(0.05);
						}
						else if(d == 2){
							oV.add(0.025);
						}
						else{
							oV.add(noll);
						}
					}
				}
			}
			for(int i = 0; i < oV.size(); i++){
				oM[i][i] = oV.get(i);
			}
			return oM;
		}
	}
	private int distance(int i,int j, int r, int c){
		int rD = Math.abs(i-r);
		int cD = Math.abs(j-c);
		if(rD == 0 && cD == 0){
			return 0;
		}
		else if((rD == 0 && cD == 1) || (rD == 1 && cD == 0) || (rD == 1 && cD == 1)){
			return 1;
		}
		else if((rD == 0 && cD == 2) || (rD == 2 && cD == 0) || (rD == 2 && cD == 1) || (rD == 1 && cD == 2) || (rD == 2 && cD == 2)){
			return 2;
		}
		else{
			return -1;
		}
	}
	
	private double nP(int nN, int nF){
		return (1-0.1-nN*0.05-nF*0.025);
	}
	
	private double[][] nothingProb(){
		List<Double> oV = new ArrayList<Double>();
		int size = rows*cols*4;
		double[][] oM = new double[size][size];
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				for(int h = 0; h < 4; h++){
					int cn = cN(i,j);  //close neibours
					int fn = fN(i,j); //far neibours
					oV.add(nP(cn-1, fn-cn-1));
				}
			}
		}
		for(int q = 0; q < oV.size(); q++){
			oM[q][q] = oV.get(q);
		}
		return oM;
	}
	
	private int cN(int r, int c){
		int nN = 0;
		for(int i = r-1; i <= r+1; i++){
			for(int j = c-1; j <= c+1; j++){
				if((i >= 0) && (i < rows) && (j >= 0) && (j < cols)){
					nN++;
				}
			}
		}
		return nN;
	}
	
	private int fN(int r, int c){
		int nN = 0;
		for(int i = r-2; i <= r+2; i++){
			for(int j = c-2; j <= c+2; j++){
				nN++;
			}
		}
		return nN;
	}
	
	public double[][] getTM(){
		return robot.getTM();
	}
}
