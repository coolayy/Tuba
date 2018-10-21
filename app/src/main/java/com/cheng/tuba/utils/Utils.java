package com.cheng.tuba.utils;


import static com.cheng.tuba.utils.Constant.ground;
import static com.cheng.tuba.utils.Constant.startqipan;

public class Utils {
	public static boolean isWin(int x, int y) {
		return baiwin() || hewin();

	}
	//检查棋盘是否下满
	public static int  he() {
		int he = 0;
		for (int x = 0; x <3; x++) {
			for (int y = 0; y < 3; y++) {
				if (ground[x][y] == 2) {
					he++;
				}
			}
		}
		return he;
	}
	public static int  bai() {
		int bai = 0;
		for (int x = 0; x <3; x++) {
			for (int y = 0; y < 3; y++) {
				if (ground[x][y] == 1) {
					bai++;
				}
			}
		}
		return bai;
	}
	public static boolean hewin(){

		if (bai()<2)  return true;
		return false;
	}
	public static boolean baiwin(){
		if (he()<2)  return true;
		return false;
	}




//
//public static boolean killbai(){
//		if( (ground[0][0]==1&&ground[0][1]==2&&ground[1][0]==2)||
//		 (ground[2][2]==1&&ground[1][2]==2&&ground[2][1]==2)|| (ground[0][2]==1&&ground[0][1]==2&&ground[1][2]==2)||
//		 (ground[2][0]==1&&ground[1][0]==2&&ground[2][1]==2)) {
//			return true;
//		}
//	return false;
//	}
//	public static boolean killhei(){
//		if( (ground[0][0]==2&&ground[0][1]==1&&ground[1][0]==1)||
//				(ground[2][2]==2&&ground[1][2]==1&&ground[2][1]==1)|| (ground[0][2]==2&&ground[0][1]==1&&ground[1][2]==1)||
//				(ground[2][0]==2&&ground[1][0]==1&&ground[2][1]==1)) {
//			return true;
//		}
//		return false;
//
//	}


	public static boolean baiwulu(int x,int y){
		int aa=1,bb=2;
		if (bai()==3&&((ground[0][0]==aa&&ground[0][1]==aa&&ground[0][2]==aa&&ground[1][0]==bb&&ground[1][2]==bb)||
				(ground[2][2]==aa&&ground[1][2]==aa&&ground[0][2]==aa&&ground[0][1]==bb&&ground[2][1]==bb)||
				(ground[2][0]==aa&&ground[2][1]==aa&&ground[2][2]==aa&&ground[1][0]==bb&&ground[1][2]==bb)||
				(ground[0][0]==aa&&ground[0][1]==aa&&ground[0][2]==aa&&ground[0][1]==bb&&ground[2][1]==bb))){
			return true;
		}
		else if(bai()==2&&
				((ground[0][0]==aa&&ground[0][1]==aa&&ground[0][2]==bb&&ground[0][1]==bb&&ground[1][2]==bb)||
				(ground[0][2]==aa&&ground[0][1]==aa&&ground[0][0]==bb&&ground[0][1]==bb&&ground[1][2]==bb)||
				(ground[0][2]==aa&&ground[1][2]==aa&&ground[0][1]==bb&&ground[2][1]==bb&&ground[2][2]==bb)||
				(ground[1][2]==aa&&ground[2][2]==aa&&ground[0][2]==bb&&ground[0][1]==bb&&ground[2][1]==bb)||
				(ground[2][0]==aa&&ground[2][1]==aa&&ground[1][0]==bb&&ground[1][2]==bb&&ground[2][2]==bb)||
				(ground[2][1]==aa&&ground[2][2]==aa&&ground[2][0]==bb&&ground[1][0]==bb&&ground[1][2]==bb)||
				(ground[0][0]==aa&&ground[1][0]==aa&&ground[2][0]==bb&&ground[0][1]==bb&&ground[2][1]==bb)||
				(ground[1][0]==aa&&ground[2][0]==aa&&ground[0][0]==bb&&ground[0][1]==bb&&ground[2][1]==bb))) {

			return true;
		}
		return false;
	}
	public static boolean hewulu(int x,int y){
		int aa=2,bb=1;
		if (he()==3&&((ground[0][0]==aa&&ground[0][1]==aa&&ground[0][2]==aa&&ground[1][0]==bb&&ground[1][2]==bb)||
				(ground[2][2]==aa&&ground[1][2]==aa&&ground[0][2]==aa&&ground[0][1]==bb&&ground[2][1]==bb)||
				(ground[2][0]==aa&&ground[2][1]==aa&&ground[2][2]==aa&&ground[1][0]==bb&&ground[1][2]==bb)||
				(ground[0][0]==aa&&ground[0][1]==aa&&ground[0][2]==aa&&ground[0][1]==bb&&ground[2][1]==bb)))
		{
			return true;
		}
			else	if(he()==2&&
				((ground[0][0]==aa&&ground[0][1]==aa&&ground[0][2]==bb&&ground[0][1]==bb&&ground[1][2]==bb)||
				(ground[0][2]==aa&&ground[0][1]==aa&&ground[0][0]==bb&&ground[0][1]==bb&&ground[1][2]==bb)||
				(ground[0][2]==aa&&ground[1][2]==aa&&ground[0][1]==bb&&ground[2][1]==bb&&ground[2][2]==bb)||
				(ground[1][2]==aa&&ground[2][2]==aa&&ground[0][2]==bb&&ground[0][1]==bb&&ground[2][1]==bb)||
				(ground[2][0]==aa&&ground[2][1]==aa&&ground[1][0]==bb&&ground[1][2]==bb&&ground[2][2]==bb)||
				(ground[2][1]==aa&&ground[2][2]==aa&&ground[2][0]==bb&&ground[1][0]==bb&&ground[1][2]==bb)||
				(ground[0][0]==aa&&ground[1][0]==aa&&ground[2][0]==bb&&ground[0][1]==bb&&ground[2][1]==bb)||
				(ground[1][0]==aa&&ground[2][0]==aa&&ground[0][0]==bb&&ground[0][1]==bb&&ground[2][1]==bb)))
				{
			return true;
		}
		return false;
	}


	public static boolean isLegal(int x, int y) {
		return x >= 0 && x < 3 && y >= 0 && y <3;
	}
	public static void initGroup(){
		int length =  ground.length;
		for (int i = 0; i < length; i++) {
			for(int j = 0;j < length;j++){
				ground[i][j] = startqipan[i][j];
			}
		}

	}


}
