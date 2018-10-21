package com.cheng.tuba.utils;

public class Constant {
	// 白棋
	public final static int WHITE_CHESS = 1;
	// 黑棋
	public final static int BLACK_CHESS = 2;
	public static boolean isnoPlaySound=true;

	// 对方还未获胜
	public final static int ENEMYNOTWIN = 0;
	// 对方获胜
	public final static int ENEMYWIN = 1;
	// 格子长和高度
//	public  static int RECT_R = 26;
//	// 棋子长和高度
	public  static int CHESS_R = 14;

	
	//屏幕宽度
	public  static int SCREENWIDTH;
	//屏幕高度	
	public  static int SCREENHEIGHT;

	public static String address ;
	public static String LogTag = "马牵马";

	/**
	 * 棋盘
	 * **/
	public static int[][] ground = new int[][] { {  1, 1, 1 },

			{ 0, 0, 0 }, {  2, 2, 2 } };
	public static int[][] startqipan= new int[][] { {  1, 1, 1 },

			{ 0, 0, 0 }, {  2, 2, 2 } };

	

	

}
