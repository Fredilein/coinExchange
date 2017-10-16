package exchange;

import java.awt.Point;
import java.util.Scanner;
import java.util.Stack;

class Main {
	
	static Scanner s = new Scanner(System.in);
	
	public static int[] cash = {1, 2, 5, 10, 20, 50, 100, 200};

	public static int sum = s.nextInt();
	public static int n = cash.length;
	public static int m = sum + 1;
	
	public static int[][] dp = new int[n][m];
	public static int[][][] pointer = new int[n][m][2]; // pointer[row][col][0] = r, [1] = c
	public static int[][] min = new int[n][m];
	
	public static void main(String[] args) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				pointer[i][j][0] = -1;
				pointer[i][j][1] = -1;
				
			}
		}
		createDP();
		//printDP(dp);
		System.out.println();
		//printDP(min);
		System.out.println();
		//printpointer();
		getRes();
	}

	public static void createDP() {
		int n = cash.length;
		int m = sum + 1;
		
		// erste Spalte auf 0 setzen
		// dp[row][col]
		for (int i = 0; i < n; i++) {
			dp[i][0] = 1;
		}
		
		for (int col = 1; col < m; col++) {
			for (int row = 0; row < n; row++) {
				int c = isReachable(col - cash[row], col, row);
				if (c != -1) {
					dp[row][col] = 1;
					min[row][col] = c + 1;
				}
			}
		}
				
	}
	
	public static int isReachable(int col, int colO, int rowO) {
		if (col < 0) {
			return -1;
		}
		int mincoins = 999;
		for (int r = 0; r < n; r++) {
			if (dp[r][col] == 1) {
				if (mincoins > min[r][col]) {
					mincoins = min[r][col];
					pointer[rowO][colO][0] = r;
					pointer[rowO][colO][1] = col;
				}
			}
		}
		if (mincoins < 999) {
			return mincoins;
		}
		else {
			return -1;
		}
	}
	
	public static void getRes() {
		int minimum = 999;
		int row = 0;
		Stack<Integer> s = new Stack<Integer>();
		for (int r = 0; r < n; r++) {
			if (min[r][m-1] < minimum && min[r][m-1] > 0) {
				minimum = min[r][m-1];
				row = r;
			}
		}
		if (minimum < 999) {
			Point p = new Point();
			p.x = row;
			p.y = m-1;
			
			while (pointer[p.x][p.y][0] >= 0 && pointer[p.x][p.y][1] >= 0) {
				s.push(cash[p.x]);
				Point old = new Point();
				old.x = pointer[p.x][p.y][0];
				old.y = pointer[p.x][p.y][1];
				p.setLocation(old.x, old.y);
			}
			
			System.out.println("Coins: ");
			while (!s.isEmpty()) {
				System.out.println(s.pop());
			}
		}
		else {
			System.out.println("Betrag nicht m�glich");
		}
	}
	
	public static void printDP(int[][] A) {
		for (int r = 0; r < A.length; r++) {
			for (int c = 0; c < A[r].length; c++) {
				System.out.print(A[r][c] + " ");
			}
			System.out.println();
		}
	}
	
	public static void printpointer() {
		System.out.println("pointer r: ");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				System.out.print(pointer[i][j][0]);
			}
			System.out.println();
		}
		System.out.println("pointer c: ");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				System.out.print(pointer[i][j][1]);
			}
			System.out.println();
		}
	}
	
}
