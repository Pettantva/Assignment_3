package model;

import java.util.Random;

public class RobotGrid {
	private int rows;
	private int cols;
	private int head;
	private int rowp;
	private int colp;
	private boolean[][][] loc;
	private Random rand;
	private double[][] TM;
	
	public RobotGrid(int rows, int cols, int head){
		this.head = head;
		this.rows = rows;
		this.cols = cols;
		this.loc = new boolean[rows][cols][4];
		this.rand = new Random();
		setGrid();
		int size = rows*cols*4;
		this.TM = new double[size][size];
		setTransitionMatrix();
	}
	
	private void setGrid(){
		resetGrid();
		this.rowp = this.rand.nextInt(rows);
		this.colp = this.rand.nextInt(cols);
		this.head = this.rand.nextInt(4);
		loc[this.rowp][this.colp][this.head] = true;
	}
	
	public void update(){
		Random rand = new Random();
		if(rowp == 0){  //upp
			if(colp == 0){ //vänster
				if(head == 0 || head == 3){ //byta
					
				}
				else{ //inte byta
					
				}
			}
			else if(colp == cols-1){ //höger
				if(head == 0 || head == 1){ //byta
					
				}
				else{ //inte byta
					
				}
			}
			else{ //mitten
				if(head == 0){ //byta
					
				}
				else{ //inte byta
					
				}
			}
		}
		else if(rowp == rows-1){ //ner
			if(colp == 0){ //vänster
				if(head == 2 || head == 3){ //byta
					
				}
				else{ //inte byta
					
				}
			}
			else if(colp == cols-1){ //höger
				if(head == 1 || head == 2){ //byta
					
				}
				else{ //inte byta
					
				}
			}
			else{ //mitten
				if(head == 2){ //byta
					
				}
				else{ //inte byta
					
				}
			}
		}
		else{ //mitten
			if(colp == 0){ //vänster
				if(head == 3){ //byta
					
				}
				else{ //inte byta
					
				}
			}
			else if(colp == cols-1){ //höger
				if(head == 1){ //byta
					
				}
				else{ //inte byta
					
				}
			}
			else{ //mitten byta
				
			}
		}
	}
	
	private void headUpdateTwo(int a, int b){
		
	}
	
	private void headUpdateThree(int a){
		
	}

	private void headUpdate(){
		
	}
	
	public int getRow(){
		return this.rowp;
	}
	
	public int getCol(){
		return this.colp;
	}
	
	private double countTransProb(int x, int y, int h, int nX, int nY, int nH){
		return 0;
	}
	
	public double getTransProb(int x, int y, int h, int nX, int nY, int nH){
		return 0;
	}
	
	private void resetGrid(){
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				for(int h = 0; h < 4; h++){
					loc[i][j][h] = false;
				}
			}
		}
		//loc[this.rowp][this.colp][this.head] = false; snabbare men osäkrare
	}
	
	private void setTransitionMatrix(){
		
	}
	/*  Ändrar beroende på heading
	
	
	 public void update(){
		if(rowp == 0){  //upp
			if(colp == 0){ //vänster
				headUpdateTwo(0, 3);
			}
			else if(colp == cols-1){ //höger
				headUpdateTwo(0, 1);
			}
			else{ //mitten
				headUpdateThree(0);
			}
		}
		else if(rowp == rows-1){ //ner
			if(colp == 0){ //vänster
				headUpdateTwo(2, 3);
			}
			else if(colp == cols-1){ //höger
				headUpdateTwo(2, 1);
			}
			else{ //mitten
				headUpdateThree(2);
			}
		}
		else{ //mitten
			if(colp == 0){ //vänster
				headUpdateThree(3);
			}
			else if(colp == cols-1){ //höger
				headUpdateThree(1);
			}
			else{ //mitten
				headUpdate();
			}
		}
	}*/
}
