package model;

public class DENAIEstimator {
	
	private Sensor sensor;
	private double[][] f;
	private double[][] O;
	
	public DENAIEstimator(Sensor sensor){
		this.sensor = sensor;
		f = new double[sensor.getTM().length][1];
		this.O = sensor.observationMatrix(sensor.getRow(), sensor.getCol());
		initF();
		System.out.println(f.length + " här");
	}
	
	public void update(Sensor sensor){
		this.sensor = sensor;
		this.O = sensor.observationMatrix(sensor.getRow(), sensor.getCol());
		double[][] ttrans = matrixTrans(sensor.getTM());
		double[][] OTTrans = matrixMult(O, ttrans);
		f = matrixMult(OTTrans, f);
		double alpha = 0;
		double temp = 0;
		for(int i = 0; i < f.length; i++){
			for(int j = 0; j < f[0].length; j++){
				alpha = alpha + f[i][j];
			}
		}
		for(int i = 0; i < f.length; i++){
			for(int j = 0; j < f[0].length; j++){
				f[i][j] = (f[i][j])/alpha;
				temp = temp + f[i][j];
			}
		}
		System.out.println(temp);
	}
	
	public double getSummedProb(int i, int j){
		double value = 0;
		for(int h = 0; h < 4; h++){
			value = value + f[sensor.getRows()*i + 4*j + h][0];
		}
		//double value = f[(f.length/sensor.getRows())*i + j[0] ;
		return value;
	}
	
	public double getNothingProb(int i, int j, int h){
		double[][] nothingMatrix = sensor.observationMatrix(-1, -1);					//hämta nothingMatrix
		//int size = nothingMatrix.length*nothingMatrix[0].length;
		//int value = (size/nothingMatrix.length)*i + j*4 + h;
		int value = sensor.getRows()*i + j*4 + h;
		return nothingMatrix[value][value];
	}	
	
	public double getStateProb(int rI, int rJ, int i, int j, int h){
		double[][] obsMatrix = sensor.observationMatrix(rI, rJ);					//hämtarättObsmatrix
		//int value = (obsMatrix.length/sensor.getRows())*i + j*4 + h;
		int value = sensor.getRows()*i + j*4 + h;
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
		double[][] b = new double[a.length][a[0].length];
		
		for(int i = 0 ; i < a.length ; i++){
			for(int j = 0; j < a[0].length; j++){
				b[j][i] = a[i][j];
			}
		}
		return b;
		
	}
	private void initF(){
		//double initprob = 1.0/(sensor.getTM().length/4);
		double initprob = 1.0/(sensor.getCols()*sensor.getRows()*4);
		for(int i = 0; i < f.length ; i++){
			f[i][0] = initprob;
		}
	}
}
