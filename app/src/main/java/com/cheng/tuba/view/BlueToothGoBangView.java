package com.cheng.tuba.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.tuba.R;
import com.cheng.tuba.aty.BlueToothGameAty;
import com.cheng.tuba.utils.Constant;
import com.cheng.tuba.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

import static com.cheng.tuba.utils.Constant.CHESS_R;
import static com.cheng.tuba.utils.Constant.ground;
import static com.cheng.tuba.utils.Constant.isnoPlaySound;

//import com.whale.nangua.pumpkingobang.utils.AssetsLoad;

public  class BlueToothGoBangView extends SurfaceView implements Runnable,SurfaceHolder.Callback {

    BlueToothGameAty father;
    private Canvas canvas;
    private SurfaceHolder sfh;
    private Thread th = new Thread(this);


    protected static int GRID_SIZE = 2;    //设置为国际标准
    protected static int GRID_WIDTH = 36; // 棋盘格的宽度
    protected static int CHESS_DIAMETER = 37; // 棋的直径
    protected static int mStartX;// 棋盘定位的左上角X
    protected static int mStartY;// 棋盘定位的左上角Y

    //    private static int[][] mGridArray; // 网格
    private Stack<String> storageArray;


    int wbflag = 1; //该下白棋了=2，该下黑棋了=1. 这里先下黑棋（黑棋以后设置为机器自动下的棋子）
    int mLevel = 1; //游戏难度
    int mWinFlag = 0;
    private final int BLACK = 1;
    private final int WHITE = 2;
    private Paint paint1;

    private boolean WHITE_FLAG ;
    private Bitmap whiteMap;
    private Bitmap blackMap;
    ;
    boolean focus=false;
    int selectqizi=0;
    int startI,startJ;
    int endI,endJ;
    private int whoWin = 0;// 0没有，1白色win，2黑色win
    private boolean isStop = false;

    private TextView mStatusTextView; //  根据游戏状态设置显示的文字

    private Bitmap btm1;
    private final Paint mPaint = new Paint();

    CharSequence mText;
    CharSequence STRING_WIN = "白马赢啦!  ";
    CharSequence STRING_LOSE = "红马赢啦!  ";

    public BlueToothGoBangView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.father=(BlueToothGameAty) context;
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        sfh = this.getHolder();
        sfh.addCallback((SurfaceHolder.Callback) this);

        init();
    }

    //按钮监听器
    MyButtonListener myButtonListener;
    public void setActionCallbak(BlueToothGameAty blueToothGameAty) {
        this.blueToothGameAty = blueToothGameAty;
    }
    private BlueToothGameAty blueToothGameAty;

    // 初始化黑白棋的Bitmap
    public void init() {

//        storageArray = new Stack<>();
        myButtonListener = new MyButtonListener();
//        wbflag = WHITE; //初始为先下黑棋
        mWinFlag = 0; //清空输赢标志。
//        mGridArray = new int[GRID_SIZE - 1][GRID_SIZE - 1];



        paint1=new Paint();
        paint1.setColor(Color.YELLOW);
        paint1.setTextSize(28);
        paint1.setAntiAlias(true);

        Bitmap bitmap = Bitmap.createBitmap(CHESS_DIAMETER, CHESS_DIAMETER, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Resources r = this.getContext().getResources();
        whiteMap = BitmapFactory.decodeResource(r, R.drawable.ai);
        blackMap = BitmapFactory.decodeResource(r, R.drawable.human);
//        renren=BitmapFactory.decodeResource(r,R.drawable.renrengame_background);

    }

    //设置显示的textView
    public void setTextView(TextView tv) {
        mStatusTextView = tv;
//        mStatusTextView.setVisibility(View.INVISIBLE);
    }

    //悔棋按钮
    private Button huiqi;
    //刷新那妞
    private Button refresh;

    //设置两个按钮
    public void setButtons(Button huiqi, Button refresh) {
        this.huiqi = huiqi;
        this.refresh = refresh;
        huiqi.setOnClickListener(myButtonListener);
        refresh.setOnClickListener(myButtonListener);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mStartX = w / 2 - GRID_SIZE * 6*GRID_WIDTH / 2;
        mStartY = h / 2 - GRID_SIZE *6* GRID_WIDTH / 2;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub

    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        th.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        Utils.initGroup();
        isStop = true;
    }
    // 触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!isStop) {
                for (int y = 0;y < 3; y++) {
                    for (int x = 0; x < 3; x++) {
                        if (x==1&&y==1){
                            continue;
                        }
                        if(isInCircle(event.getX(), event.getY(), x, y)) {
                            if (Utils.baiwin()){
                                father.playSound(4,1);
                                whoWin=1;
                                mText = STRING_WIN;
                                showTextView(mText);
                                blueToothGameAty.onPutChess("WHITE");
//                                isStop=true;
//											 Toast.makeText(getContext(), "白赢了", Toast.LENGTH_SHORT).show();
                            }
                            if (Utils.hewin()) {
                                father.playSound(4, 1);
                                whoWin = 2;
                                mText = STRING_LOSE;
                                showTextView(mText);
                                blueToothGameAty.onPutChess("BLACK");
//                                isStop=true;
//												Toast.makeText(getContext(), "黑赢了", Toast.LENGTH_SHORT).show();
                            }

                            if (WHITE_FLAG) {

                                if (Utils.baiwulu(x,y)){
                                    Toast.makeText(getContext(),  "请红马放条生路", Toast.LENGTH_SHORT).show();
                                    WHITE_FLAG=false;
                                }
                                int i = -1, j = -1;
                                int[] pos = getpos(event);
                                i = pos[0];
                                j = pos[1];
                                if (focus == false) {//之前没有选中棋子
                                    if (ground[y][x] != 0) {//有棋子
                                        if (ground[y][x] == 1) {//点击的是自己的棋子

                                            selectqizi = ground[i][j];
                                            focus = true;
                                            startI = i;
                                            startJ = j;

                                        }
                                    }
                                }
                                else {//之前选中棋子
                                    if (ground[y][x] != 0) {//点击有棋子
                                        if (ground[y][x] == 1) {//是自己的棋子
                                            selectqizi = ground[i][j];

                                            startI = i;
                                            startJ = j;
                                        }

                                    }
                                    else{//点击的位置没有棋子

                                        endI=i;endJ=j;
                                        WHITE_FLAG=true;
                                        if (Math.abs(endI-startI)<=1&&Math.abs(endJ-startJ)<=1) {

                                            ground[endJ][endI] = 1;
                                            ground[startJ][startI] = 0;
                                            father.playSound(2,1);
                                            int aa=startI,ab=startJ,ac=endI,ad=endJ;
                                            String temp = aa + ":" + ab+ ":" +ac + ":"+ad+":"+1;
//                                            storageArray.push(temp);
                                            //通过回调方法通知Activity下棋动作
                                            blueToothGameAty.onPutChess(temp);
                                            if (ground[0][0]==2&&ground[0][1]==1&&ground[1][0]==1){
                                                ground[0][0]=0;
                                            }
                                            else if (ground[2][2]==2&&ground[1][2]==1&&ground[2][1]==1){
                                                ground[2][2]=0;
                                            }else if (ground[0][2]==2&&ground[0][1]==1&&ground[1][2]==1){
                                                ground[0][2]=0;
                                            }
                                            else if (ground[2][0]==2&&ground[1][0]==1&&ground[2][1]==1){
                                                ground[2][0]=0;
                                            }

                                            startI = -1;
                                            startJ = -1;
                                            endI = -1;
                                            endJ = -1;//还原保存点
                                            focus = false;//标记买选中棋子
                                            WHITE_FLAG = false;
                                        }
                                    }
                                }
                            }

                            else {
                                if (Utils.hewulu(x, y)) {
                                    Toast.makeText(getContext(), "请白马放条生路", Toast.LENGTH_SHORT).show();
                                    WHITE_FLAG = true;
                                }
                                int i = -1, j = -1;
                                int[] pos = getpos(event);
                                i = pos[0];
                                j = pos[1];
                                if (focus == false) {
                                    if (ground[y][x] != 0) {
                                        if (ground[y][x] == 2) {

                                            selectqizi = ground[i][j];
                                            focus = true;
                                            startI = i;
                                            startJ = j;
                                            // ai在此处落子
                                        }
                                    }
                                } else {
                                    if (ground[y][x] != 0) {
                                        if (ground[y][x] == 2) {
                                            selectqizi = ground[i][j];
                                            focus = true;
                                            startI = i;
                                            startJ = j;
                                        }

                                    } else {//点击的位置没有棋子

                                        endI = i;
                                        endJ = j;
                                        WHITE_FLAG = false;
                                        if (Math.abs(endI - startI) <= 1 && Math.abs(endJ - startJ) <= 1) {

                                            ground[endJ][endI] = 2;
                                            ground[startJ][startI] = 0;
                                            String temp = startI + ":" + startJ + ":" + endI + ":" + endJ+":"+0;
                                            //通过回调方法通知Activity下棋动作
                                            blueToothGameAty.onPutChess(temp);
                                            father.playSound(2, 1);
                                            if (ground[0][0] == 1 && ground[0][1] == 2 && ground[1][0] == 2) {
                                                ground[0][0] = 0;
                                            } else if (ground[2][2] == 1 && ground[1][2] == 2 && ground[2][1] == 2) {
                                                ground[2][2] = 0;
                                            } else if (ground[0][2] == 1 && ground[0][1] == 2 && ground[1][2] == 2) {
                                                ground[0][2] = 0;
                                            } else if (ground[2][0] == 1 && ground[1][0] == 2 && ground[2][1] == 2) {
                                                ground[2][0] = 0;
                                            }

                                            startI = -1;
                                            startJ = -1;
                                            endI = -1;
                                            endJ = -1;//还原保存点
                                            focus = false;//标记买选中棋子

                                            WHITE_FLAG = true;
                                        }
                                    }

                                }
                            }
                            return super.onTouchEvent(event);
                        }
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }
    public int [] getpos(MotionEvent event){

        int []pos=new int[2];
        double x=event.getX();
        double y=event.getY();
        if (x>mStartX-GRID_WIDTH*2&&x<mStartY+14*GRID_WIDTH&y>mStartY-2*GRID_WIDTH&&y<mStartY+14*GRID_WIDTH){
            pos[0]= (int) Math.round ((x-mStartX)/(6*GRID_WIDTH));
            pos[1]= (int) Math.round((y-mStartY)/(6*GRID_WIDTH));
        }else {
            pos[0]=-1;
            pos[1]=-1;
        }
        return pos;
    }

    // 判断是否与某点最近
    private boolean isInCircle(float touch_x, float touch_y, int x, int y) {

        return ((touch_x - x  * 6 * GRID_WIDTH-mStartX)
                * (touch_x - x * 6 * GRID_WIDTH-mStartX) + (touch_y - y*6 * GRID_WIDTH-mStartY)
                * (touch_y - y * 6 * GRID_WIDTH-mStartY))< 9*GRID_WIDTH*GRID_WIDTH;
    }

    public void draw() {
        try {
            canvas = sfh.lockCanvas();
            //canvas.drawColor(Color.YELLOW);
            //先画实木背景
            Paint paintBackground = new Paint();
            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.chess_background);
            canvas.drawBitmap(bitmap, null, new Rect(mStartX - 2 * GRID_WIDTH, mStartY - 2 * GRID_WIDTH, mStartX + 6 * GRID_WIDTH * GRID_SIZE + 2 * GRID_WIDTH, mStartY + 6 * GRID_WIDTH * GRID_SIZE + 2 * GRID_WIDTH), paintBackground);
            // 画棋盘
            Paint paintRect = new Paint();
            paintRect.setColor(Color.BLACK);
            paintRect.setStrokeWidth(2);
            paintRect.setStyle(Paint.Style.STROKE);
            // 横线
            for (int i = 0; i <2; i++) {
                canvas.drawLine(mStartX, 12* GRID_WIDTH * i
                        +mStartY, mStartX+12 * GRID_WIDTH, 12
                        * GRID_WIDTH * i + mStartY, paintRect);
            }
            for (int j = 0; j < 2; j++) {
                canvas.drawLine(mStartX + 12 *GRID_WIDTH * j,
                        mStartY, mStartX + 12
                                * GRID_WIDTH * j,mStartY+12 * GRID_WIDTH,
                        paintRect);
            }
            canvas.drawLine(mStartX+6*GRID_WIDTH,mStartY,mStartX+12*GRID_WIDTH,mStartY+6*GRID_WIDTH,paintRect);
            canvas.drawLine(mStartX+12*GRID_WIDTH,mStartY+6*GRID_WIDTH,mStartX+6*GRID_WIDTH,mStartY+12*GRID_WIDTH,paintRect);
            canvas.drawLine(mStartX+6*GRID_WIDTH,mStartY+12*GRID_WIDTH,mStartX,mStartY+6*GRID_WIDTH,paintRect);
            canvas.drawLine(mStartX,mStartY+6*GRID_WIDTH,mStartX+6*GRID_WIDTH,mStartY,paintRect);
            paintRect.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(mStartX+6*GRID_WIDTH,mStartY+6*GRID_WIDTH,96,paintRect);

            canvas.drawText ("牵"+(3-Utils.bai())+"白马", mStartX+5*GRID_WIDTH-6,mStartY+7*GRID_WIDTH, paint1);
            canvas.drawText("牵"+(3-Utils.he())+"红马", mStartX+5*GRID_WIDTH-6,mStartY+5*GRID_WIDTH, paint1);

            for (int y = 0; y <3; y += 1) {
                for (int x = 0; x < 3; x += 1) {
                    if (ground[y][x] != 0)
                        drawMyBitmap(x, y);
                    if (focus){
                        drawFocus();
                    }
                }
            }
            paintRect.setStrokeWidth(4);
            canvas.drawRect(mStartX - 2 * GRID_WIDTH, mStartY - 2 * GRID_WIDTH, mStartX + 6 * GRID_WIDTH * GRID_SIZE + 2 * GRID_WIDTH, mStartY + GRID_WIDTH * 6 * GRID_SIZE + 2 * GRID_WIDTH, paintRect);
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            if (canvas != null) {
                sfh.unlockCanvasAndPost(canvas);
            }
        }
    }


    private void drawMyBitmap(int x, int y) {
        if (ground[y][x] == 1)
            canvas.drawBitmap(whiteMap, (x * 6 * GRID_WIDTH + mStartX)
                    - 2* Constant.CHESS_R, (y * 6 * GRID_WIDTH + mStartY)
                    - 2* Constant.CHESS_R, null);
        else if (ground[y][x]==2){
            canvas.drawBitmap(blackMap, (x * 6 * GRID_WIDTH + mStartX)
                    - 2*CHESS_R, (y * 6 * GRID_WIDTH + mStartY)
                    -2*CHESS_R, null);
        }
    }
    private void drawFocus() {
        if (focus){
            canvas.drawCircle(startI*6 * GRID_WIDTH+mStartX,startJ*6*GRID_WIDTH+mStartY,18,paint1);
        }
    }

    public static boolean isLegal(int x, int y) {
        return x >= 0 && x < 3&& y >= 0 && y < 3;
    }


    //收到对方传来的棋子
    public void xiaqi(String command) {
        if ((command == null)) {
            return;
        }
        if ((command.equals("HUIQI"))) {


            isnoPlaySound = !isnoPlaySound;
        } else if ((command.equals("REFRESH"))) {
            setVisibility(View.VISIBLE);
            mStatusTextView.invalidate();
            Utils.initGroup();
            init();
            invalidate();
            for (int i = 0; i < showtime.length; i++) {
                showtime[i] = 0;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            mStatusTextView.setText("蓝牙对战模式 佳计：" + simpleDateFormat.format(new Date()));
        } else if ((command.equals("WHITE"))) {
            mStatusTextView.setText("红马赢辣");
        } else if (command.equals("BLACK")) {
            mStatusTextView.setText("黑马赢辣");
        } else {
            Log.d("whalea", "收到的指令:" + command);
//            if (WHITE_FLAG) {
                String[] temps = command.split(":");
                int a = Integer.parseInt(temps[0]);
                int b = Integer.parseInt(temps[1]);
                int c = Integer.parseInt(temps[2]);
                int d = Integer.parseInt(temps[3]);
                int e = Integer.parseInt(temps[4]);
                ground[b][a] = 0;
            if (e==1) {
                ground[d][c] = 1;
                father.playSound(2,1);
                if (ground[0][0]==2&&ground[0][1]==1&&ground[1][0]==1){
                    ground[0][0]=0;
                }
                else if (ground[2][2]==2&&ground[1][2]==1&&ground[2][1]==1){
                    ground[2][2]=0;
                }else if (ground[0][2]==2&&ground[0][1]==1&&ground[1][2]==1){
                    ground[0][2]=0;
                }
                else if (ground[2][0]==2&&ground[1][0]==1&&ground[2][1]==1){
                    ground[2][0]=0;
                }
            }else if(e==0){
                ground[d][c]=2;
                father.playSound(2,1);
                if (ground[0][0]==1&&ground[0][1]==2&&ground[1][0]==2){
                    ground[0][0]=0;
                }
                else if (ground[2][2]==1&&ground[1][2]==2&&ground[2][1]==2){
                    ground[2][2]=0;
                }else if (ground[0][2]==1&&ground[0][1]==2&&ground[1][2]==2){
                    ground[0][2]=0;
                }
                else if (ground[2][0]==1&&ground[1][0]==2&&ground[2][1]==2){
                    ground[2][0]=0;
                }
            }

            if (WHITE_FLAG){
                WHITE_FLAG=false;
            }else {
                WHITE_FLAG=true;
            }
        }
        invalidate();
    }




    public interface BlueToothActionListner {
        void  onPutChess(String temp);
        void onBtnPress(int i); //0悔棋  1刷新
    }



    public void showTextView(CharSequence mT) {
        this.mStatusTextView.setText(mT);
        mStatusTextView.setVisibility(View.VISIBLE);
    }

    private int[] showtime;

    public void setShowTimeTextViewTime(int[] showtime) {
        this.showtime = showtime;
    }

    class MyButtonListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //如果是悔棋
                case R.id.bluetooth_btn1:
                    blueToothGameAty.onBtnPress(0);
                    isnoPlaySound=!isnoPlaySound;
                    break;
                //如果是刷新
                case R.id.bluetooth_btn2:
                    blueToothGameAty.onBtnPress(1);
                    setVisibility(View.VISIBLE);
                    mStatusTextView.invalidate();
                    Utils.initGroup();
                    WHITE_FLAG=false;
                    init();
                    invalidate();
                    for (int i = 0; i < showtime.length; i++) {
                        showtime[i] = 0;
                    }
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
                    mStatusTextView.setText("蓝牙对战模式 妙计：" + simpleDateFormat.format(new Date()));
                    break;
            }
        }
    }




    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (!isStop) {
            draw();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


}
