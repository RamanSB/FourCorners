package ramansb.fourcorners;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.Display;

/**
 * Created by RamanSB on 22/09/15.
 */
public class Ball {

    Paint ballPaint;
    int ballColor;
    int radius;
    int X;
    int Y;
    int dX;
    int dY;
    float scaleFactorY = (float) Game.HEIGHT/GamePanel.HEIGHT;
    float scaleFactorX = (float) Game.WIDTH/GamePanel.WIDTH;

    public Ball(){

        int numToColor = (int) (Math.random()*4);
        switch(numToColor){
            case 0: this.ballColor = Color.RED;
                break;
            case 1: this.ballColor = Color.BLUE;
                break;
            case 2: this.ballColor = Color.GREEN;
                break;
            case 3: this.ballColor = Color.YELLOW;
                break;
            default:
                break;
        }
        this.ballPaint = new Paint();
        ballPaint.setColor(getBallColor());

        int numToPosition = (int) (Math.random()*3);
        int numToBallSize = (int) (Math.random()*3);
        switch(numToPosition){
            case 0: this.X = (GamePanel.WIDTH/2 + 250);

                break;
            case 1: this.X = (GamePanel.WIDTH/2 - 250);

                break;
            case 2: this.X = (GamePanel.WIDTH/2);

                break;
        }
        switch(numToBallSize){
            case 0: this.radius = (int) (50*scaleFactorX);
                break;
            case 1: this.radius = (int) (75*scaleFactorX);
                break;
            case 2: this.radius = (int) (100*scaleFactorX);
                break;
        }
        if(this.radius < 50){
            this.radius = 50;
        }else if(this.radius > 120){
            this.radius = 120;
        }
        this.Y = 0-getRadius();
    }

    public int getX(){return X;}
    public int getY(){return Y;}
    public int getRadius(){return radius;}
    public int getBallColor(){return ballColor;}
    public Paint getBallPaint(){return ballPaint;}
    public void setY(int y){this.Y = y;}
    public void setX(int x){this.X = x;}

    public void update(){

        if(GamePanel.score<3){
            dY = 5;
        }else if(GamePanel.score>=3 && GamePanel.score<5){
            dY = 7;
        }else if(GamePanel.score>=5 && GamePanel.score<7){
            dY = 9;
        }else if(GamePanel.score>=7 && GamePanel.score<10){
            dY = 10;
        }else if(GamePanel.score>=10 && GamePanel.score<15){
            dY = 12;
        } else if(GamePanel.score>=15 && GamePanel.score<20){
            dY = 14;
        }
        else if(GamePanel.score>=20 && GamePanel.score<25){
            dY = 16;
        }else if(GamePanel.score>=25 && GamePanel.score<40){
            dY = 20;
        }else if(GamePanel.score>=40 && GamePanel.score<50){
            dY = 23;
        }else if(GamePanel.score>=50 && GamePanel.score<60) {
            dY = 25;
        }
        else if(GamePanel.score>=60 && GamePanel.score<80){
            dY = 30;
        }else if(GamePanel.score>=80 && GamePanel.score<82){
            dY = 29;
        }else if(GamePanel.score>=82 && GamePanel.score<84){
            dY = 28;
        }else if(GamePanel.score>=84 && GamePanel.score<86){
            dY = 27;
        }else if(GamePanel.score>=86 && GamePanel.score<88){
            dY = 26;
        }else if(GamePanel.score>=88 && GamePanel.score<90){
            dY = 25;
        }
        else if(GamePanel.score>=90 && GamePanel.score<95){
            dY = 27;
        } else if(GamePanel.score>=95 && GamePanel.score<100){
            dY = 29;
        }
        else if(GamePanel.score>=100){
            dY = 35;
        }

        Y+= (int) (dY * scaleFactorY);
    }

    public void draw(Canvas canvas){
        canvas.drawCircle(getX(), getY(), radius, getBallPaint());
    }

}
