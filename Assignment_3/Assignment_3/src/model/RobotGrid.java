package model;

import java.util.Random;

public class RobotGrid {
	private int rows;
	private int cols;
	private int heads;
	private int rowp;
	private int colp;
	private int head;
	private boolean[][][] loc;
	private Random rand;
	private double[][] TM;
	
	public RobotGrid(int rows, int cols, int heads){
		this.heads = heads;
		this.rows = rows;
		this.cols = cols;
		this.loc = new boolean[rows][cols][heads];
		this.rand = new Random();
		setGrid();
		int size = rows*cols*heads;
		this.TM = new double[size][size];
		setTransitionMatrix();
	}
	
	private void setGrid(){
		this.rowp = this.rand.nextInt(rows);
		this.colp = this.rand.nextInt(cols);
		this.head = this.rand.nextInt(4);
		loc[this.rowp][this.colp][this.head] = true;
	}
	
	public void update(){
		if(rowp == 0){  //upp
			if(colp == 0){ //vänster
				if(head == 0 || head == 3){ //byta
					headUpdateTwo(0, 3, true);
				}
				else{ //inte byta
					headUpdateTwo(0, 3, false);
				}
			}
			else if(colp == cols-1){ //höger
				if(head == 0 || head == 1){ //byta
					headUpdateTwo(0, 1, true);
				}
				else{ //inte byta
					headUpdateTwo(0, 1, false);
				}
			}
			else{ //mitten
				if(head == 0){ //byta
					headUpdateThree(0, true);
				}
				else{ //inte byta
					headUpdateThree(0, false);
				}
			}
		}
		else if(rowp == rows-1){ //ner
			if(colp == 0){ //vänster
				if(head == 2 || head == 3){ //byta
					headUpdateTwo(2, 3, true);
				}
				else{ //inte byta
					headUpdateTwo(2, 3, false);
				}
			}
			else if(colp == cols-1){ //höger
				if(head == 2 || head == 1){ //byta
					headUpdateTwo(2, 1, true);
				}
				else{ //inte byta
					headUpdateTwo(2, 1, false);
				}
			}
			else{ //mitten
				if(head == 2){ //byta
					headUpdateThree(2, true);
				}
				else{ //inte byta
					headUpdateThree(2, false);
				}
			}
		}
		else{ //mitten
			if(colp == 0){ //vänster
				if(head == 3){ //byta
					headUpdateThree(3, true);
				}
				else{ //inte byta
					headUpdateThree(3, false);
				}
			}
			else if(colp == cols-1){ //höger
				if(head == 1){ //byta
					headUpdateThree(1, true);
				}
				else{ //inte byta
					headUpdateThree(1, false);
				}
			}
			else{ //mitten byta
				headUpdateAll();
			}
		}
		int verhor = this.head % 2;
		if(verhor == 0){
			if(this.head == 0){
				this.rowp--;
			}
			else{
				this.rowp++;
			}
		}
		else{
			if(this.head == 3){
				this.colp--;
			}
			else{
				this.colp++;
			}
		}
	}
	
	private void headUpdateTwo(int a, int b, boolean wallHead){
		boolean cont = true;
		int random;
		if(wallHead){
			while(cont){
				random = rand.nextInt(heads);
				if(random != a && random != b){
					setHead(random);
					cont = false;
				}
			}
		}
		else{
			double prob = rand.nextDouble();
			if(prob < 0.3){
				while(cont){
					random = rand.nextInt(heads);
					if(random != a && random != b){
						setHead(random);
						cont = false;
					}
				}
			}
		}
	}
	
	private void headUpdateThree(int a, boolean wallHead){
		boolean cont = true;
		int random;
		if(wallHead){
			while(cont){
				random = rand.nextInt(heads);
				if(random != a){
					setHead(random);
					cont = false;
				}
			}
		}
		else{
			double prob = rand.nextDouble();
			if(prob < 0.3){
				while(cont){
					random = rand.nextInt(heads);
					if(random != a){
						setHead(random);
						cont = false;
					}
				}
			}
		}
	}

	private void headUpdateAll(){
		int random;
		double prob = rand.nextDouble();
		if(prob < 0.3){
				random = rand.nextInt(heads);
				setHead(random);
		}
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
	
	private void setHead(int h){
		loc[this.rowp][this.colp][this.head] = false;
		loc[this.rowp][this.colp][h] = true;
		this.head = h;
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
