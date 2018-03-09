package model;

public class DENAIEstimator {
	
	private Sensor sensor;
	private double[][] f;
	private double[][] O;
	private double avgMan;
	private double totMan;
	private double man;
	private int steps;
	private double percentCorrect;
	private double nbrOfCorrect;
	
	public DENAIEstimator(Sensor sensor){
		this.sensor = sensor;
		f = new double[sensor.getTM().length][1];
		this.O = sensor.observationMatrix(sensor.getRow(), sensor.getCol());
		initF();
		this.avgMan = 0.0;
		this.man = 0.0;
		this.steps = 0;
		this.totMan = 0.0;
		this.percentCorrect = 0.0;
		this.nbrOfCorrect = 0.0;
	}
	
	public void update(Sensor sensor){
		this.sensor = sensor;
		this.O = sensor.observationMatrix(sensor.getRow(), sensor.getCol());
		double[][] ttrans = matrixTrans(sensor.getTM());
		double[][] OTTrans = matrixMult(O, ttrans);
		f = matrixMult(OTTrans, f);
		double alpha = 0.0;
		for(int i = 0; i < f.length; i++){
			for(int j = 0; j < f[0].length; j++){
				alpha = alpha + f[i][j];
			}
		}
		for(int i = 0; i < f.length; i++){
			for(int j = 0; j < f[0].length; j++){
				f[i][j] = (f[i][j])/alpha;
			}
		}
		manhattan();
	}
	
	private void manhattan(){
		this.steps++;
		int index = 0;
		double mValue = 0;
		double xe = -1;
		double ye = -1;
		double summed = 0;
		for(int i = 0; i < sensor.getRows(); i++){
			for(int j = 0; j < sensor.getCols(); j++){
				for(int h = 0; h < 4; h++){
					summed = summed + f[index][0];
					index++;
				}
				if(summed > mValue){
					xe = i;
					ye = j;
					mValue = summed;
				}
				summed = 0;
			}
		}
		double xDist = Math.abs(xe - this.sensor.getRobot().getRow());
		double yDist = Math.abs(ye - this.sensor.getRobot().getCol());
		this.man = xDist + yDist;
		if(this.man == 0){
			this.nbrOfCorrect = this.nbrOfCorrect + 1;
			System.out.println("CORRECT");
		}
		this.percentCorrect = (double)((this.nbrOfCorrect/this.steps)*100);
		this.totMan = this.totMan + this.man;
		this.avgMan = (double)(this.totMan/this.steps);
		System.out.println("Percentage correct: " + this.percentCorrect);
		System.out.println("Number of steps: " + this.steps);
		System.out.println("This step: " + this.man);
		System.out.println("Average steps: " + this.avgMan);
		System.out.println(" ");
	}
	
	public double getSummedProb(int i, int j){
		double value = 0;
		for(int h = 0; h < 4; h++){
			value = value + f[sensor.getRows()*i*4 + 4*j + h][0];
		}
		return value;
	}
	
	public double getNothingProb(int i, int j, int h){
		double[][] nothingMatrix = sensor.observationMatrix(-1, -1);
		int value = sensor.getRows()*i*4 + j*4 + h;
		return nothingMatrix[value][value];
	}	
	
	public double getStateProb(int rI, int rJ, int i, int j, int h){
		double[][] obsMatrix = sensor.observationMatrix(rI, rJ);
		int value = sensor.getRows()*i*4 + j*4 + h;
		return obsMatrix[value][value];
	}
	public double[][] matrixMult(double[][]a , double[][] b){
		int aRows = a.length;
        int aColumns = a[0].length;
        
        int bColumns = b[0].length;
		
        double[][] c = new double[aRows][bColumns];
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bColumns; j++) {
                c[i][j] = 0.0;
            }
        }

        for (int i = 0; i < aRows; i++) { // aRow
            for (int j = 0; j < bColumns; j++) { // bColumn
                for (int k = 0; k < aColumns; k++) { // aColumn
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
		return c;
	}
	public double[][] matrixTrans(double[][] a){
		double[][] b = new double[a[0].length][a.length];
		for(int i = 0 ; i < a.length ; i++){
			for(int j = 0; j < a[0].length; j++){
				b[j][i] = a[i][j];
			}
		}
		return b;
		
	}
	private void initF(){
		double initprob = 1.0/(sensor.getCols()*sensor.getRows()*4);
		for(int i = 0; i < f.length ; i++){
			f[i][0] = initprob;
		}
	}
}
