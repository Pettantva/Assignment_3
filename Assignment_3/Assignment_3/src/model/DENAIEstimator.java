package model;

public class DENAIEstimator {
	private Sensor sensor;
	public DENAIEstimator(Sensor sensor){
		this.sensor = sensor;
	}
	
	public void update(Sensor sensor){
		
	}
	
	public double getSummedProb(int i, int j){
		int gridX = grid.witdh;
		int gridY = grid.height;
		int size = grid.totalStates;
		//f-vektorn behövs pronto pronto
		
		double value = f[(size/gridX)*i + j];
		
		
		
		return value;
	}
	
	public double getNothingProb(int x, int y, int h){
		double[][] nothingMatrix = new double[][];
		int size = nothingMatrix.length*nothingMatrix[0].length;
		int value = (size/nothingMatrix.length)*x + y*4 + h;
		return nothingMatrix[value][value];
	}	
	
	public double getStateProb(int rX, int rY, int x, int y, int h){
		double[][] obsMatrix = getObsmatrix(rX,rY);
		int size = obsMatrix.length*obsMatrix[0].length;
		int value = (size/obsMatrix.length)*x + y*4 + h;
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
}
