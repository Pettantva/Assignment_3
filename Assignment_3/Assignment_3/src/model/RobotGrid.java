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
	
	private double countTransProb(int i, int j, int h, int nI, int nJ, int nH){
		if(i == 0){  //upp
			if(j == 0){ //vänster
				if(h == 0 || h == 3){ //byta
					return probTwo(0, 3, true, i, j, h, nI, nJ, nH);
				}
				else{ //inte byta
					return probTwo(0, 3, false, i, j, h, nI, nJ, nH);
				}
			}
			else if(j == cols-1){ //höger
				if(h == 0 || h == 1){ //byta
					return probTwo(0, 1, true, i, j, h, nI, nJ, nH);
				}
				else{ //inte byta
					return probTwo(0, 1, false, i, j, h, nI, nJ, nH);
				}
			}
			else{ //mitten
				if(h == 0){ //byta
					return probThree(0, true, i, j, h, nI, nJ, nH);
				}
				else{ //inte byta
					return probThree(0, false, i, j, h, nI, nJ, nH);
				}
			}
		}
		else if(i == rows-1){ //ner
			if(j == 0){ //vänster
				if(h == 2 || h == 3){ //byta
					return probTwo(2, 3, true, i, j, h, nI, nJ, nH);
				}
				else{ //inte byta
					return probTwo(2, 3, false, i, j, h, nI, nJ, nH);
				}
			}
			else if(j == cols-1){ //höger
				if(h == 2 || h == 1){ //byta
					return probTwo(2, 1, true, i, j, h, nI, nJ, nH);
				}
				else{ //inte byta
					return probTwo(2, 1, false, i, j, h, nI, nJ, nH);
				}
			}
			else{ //mitten
				if(h == 2){ //byta
					return probThree(2, true, i, j, h, nI, nJ, nH);
				}
				else{ //inte byta
					return probThree(2, false, i, j, h, nI, nJ, nH);
				}
			}
		}
		else{ //mitten
			if(j == 0){ //vänster
				if(h == 3){ //byta
					return probThree(3, true, i, j, h, nI, nJ, nH);
				}
				else{ //inte byta
					return probThree(3, false, i, j, h, nI, nJ, nH);
				}
			}
			else if(j == cols-1){ //höger
				if(h == 1){ //byta
					return probThree(1, true, i, j, h, nI, nJ, nH);
				}
				else{ //inte byta
					return probThree(1, false, i, j, h, nI, nJ, nH);
				}
			}
			else{ //mitten byta
				return probAll(i, j, h, nI, nJ, nH);
			}
		}
	}
	
	public double getTransProb(int i, int j, int h, int nI, int nJ, int nH){
		int realI;
		int realJ;
		int size = TM.length;
		
		realI = (size/rows)*i + j*4 + h;
		realJ = (size/cols)*nI + nJ*4 + nH;
		return TM[realI][realJ];
	}
	
	private void setHead(int h){
		loc[this.rowp][this.colp][this.head] = false;
		loc[this.rowp][this.colp][h] = true;
		this.head = h;
	}
	
	private void setTransitionMatrix(){
		int i;
		int j;
		int h;
		int nI;
		int nJ;
		int nH;
		int size = TM.length;
		double value;
		for(int r = 0; r < rows ; r ++){
			h = r%4;
			i = r/(4*rows);
			j = (r-h-((size/rows)*i))/4;
			for(int c = 0; c < cols; c++){
				nH = c%4;
				nI = c/(4*cols);
				nJ = (c-nH-((size/cols)*nI))/4;
				
				value = countTransProb(i,j,h,nI,nJ,nH);
				TM[r][c] = value;
			}
		}
	}
	
	private double probTwo(int a, int b, boolean wallHead, int i, int j, int h, int nI, int nJ, int nH){
		double sameP = 0.7;
		double allChangeP = 1/2;
		double changeP = 0.3;
		double noP = 0;
		boolean up = (nI == i-1 && nJ == j);
		boolean down = (nI == i+1 && nJ == j);
		boolean left = (nI == i && nJ == j-1);
		boolean right = (nI == i && nJ == j+1);
		if(wallHead){
			if(up || down || left || right){
				if(up && nH == 0){
					return allChangeP;
				}
				else if(right && nH == 1){
					return allChangeP;
				}
				else if(down && nH == 2){
					return allChangeP;
				}
				else if(left && nH == 3){
					return allChangeP;
				}
				else{
					return noP;
				}
			}
			else{
				return noP;
			}
		}
		else{
			if(up || down || left || right){
				if(h == nH){
					return sameP;
				}
				else{
					if(up && nH == 0){
						return changeP;
					}
					else if(right && nH == 1){
						return changeP;
					}
					else if(down && nH == 2){
						return changeP;
					}
					else if(left && nH == 3){
						return changeP;
					}
					else{
						return noP;
					}
				}
			}
			else{
				return noP;
			}
		}
	}
	
	private double probThree(int a, boolean wallHead, int i, int j, int h, int nI, int nJ, int nH){
		double sameP = 0.7;
		double allChangeP = 1/3;
		double changeP = 0.15;
		double noP = 0;
		boolean up = (nI == i-1 && nJ == j);
		boolean down = (nI == i+1 && nJ == j);
		boolean left = (nI == i && nJ == j-1);
		boolean right = (nI == i && nJ == j+1);
		if(wallHead){
			if(up || down || left || right){
				if(up && nH == 0){
					return allChangeP;
				}
				else if(right && nH == 1){
					return allChangeP;
				}
				else if(down && nH == 2){
					return allChangeP;
				}
				else if(left && nH == 3){
					return allChangeP;
				}
				else{
					return noP;
				}
			}
			else{
				return noP;
			}
		}
		else{
			if(up || down || left || right){
				if(h == nH){
					return sameP;
				}
				else{
					if(up && nH == 0){
						return changeP;
					}
					else if(right && nH == 1){
						return changeP;
					}
					else if(down && nH == 2){
						return changeP;
					}
					else if(left && nH == 3){
						return changeP;
					}
					else{
						return noP;
					}
				}
			}
			else{
				return noP;
			}
		}
	}
	
	private double probAll(int i, int j, int h, int nI, int nJ, int nH){
		double sameP = 0.7;
		double changeP = 0.1;
		double noP = 0;
		boolean up = (nI == i-1 && nJ == j);
		boolean down = (nI == i+1 && nJ == j);
		boolean left = (nI == i && nJ == j-1);
		boolean right = (nI == i && nJ == j+1);
		if(up || down || left || right){
			if(h == nH){
				return sameP;
			}
			else{
				if(up && nH == 0){
					return changeP;
				}
				else if(right && nH == 1){
					return changeP;
				}
				else if(down && nH == 2){
					return changeP;
				}
				else if(left && nH == 3){
					return changeP;
				}
				else{
					return noP;
				}
			}
		}
		else{
			return noP;
		}
	}
	
	public double[][] getTM(){
		return this.TM;
	}
}
