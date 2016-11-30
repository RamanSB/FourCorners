package ramansb.fourcorners;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;

/**
 * Created by RamanSB on 24/09/15.
 */

public class Background {

    static Paint cornerPaint;
    private int cornerColor;
    int cornerLength = 75;
    Paint textPaint = new Paint();
    boolean specialAbility = false;
    static Paint TL_CornerPaint;
    static Paint BL_CornerPaint;
    static Paint TR_CornerPaint;
    static Paint BR_CornerPaint;
    private int TL_CornerColor = Color.RED;
    private int BL_CornerColor = Color.BLUE;
    private int TR_CornerColor = Color.YELLOW;
    private int BR_CornerColor = Color.GREEN;


    public Background() {
        if(Game.canvasColor) {
            textPaint.setColor(Color.BLACK);
        }else{
            textPaint.setColor(Color.WHITE);
        }
        textPaint.setTextSize(75);
        int randColor = (int) (Math.random() * 4);
        switch (randColor) {
            case 0:
                this.cornerColor = Color.RED;
                break;
            case 1:
                this.cornerColor = Color.BLUE;
                break;
            case 2:
                this.cornerColor = Color.GREEN;
                break;
            case 3:
                this.cornerColor = Color.YELLOW;
                break;
            default:
                break;
        }
        this.cornerPaint = new Paint();
        cornerPaint.setColor(cornerColor);
        this.TL_CornerPaint = new Paint();
        TL_CornerPaint.setColor(TL_CornerColor);
        this.TR_CornerPaint = new Paint();
        TR_CornerPaint.setColor(TR_CornerColor);
        this.BL_CornerPaint = new Paint();
        BL_CornerPaint.setColor(BL_CornerColor);
        this.BR_CornerPaint = new Paint();
        BR_CornerPaint.setColor(BR_CornerColor);
    }

    public Paint getCornerPaint() {
        return cornerPaint;
    }
    public Paint getBR_CornerPaint() {
        return BR_CornerPaint;
    }
    public Paint getBL_CornerPaint() {
        return BL_CornerPaint;
    }
    public Paint getTR_CornerPaint() {
        return TR_CornerPaint;
    }
    public Paint getTL_CornerPaint() {
        return TL_CornerPaint;
    }
    public int getCornerLength() {
        return cornerLength;
    }
    public int getCornerColor() {
        return cornerColor;
    }
    public void setCornerColor(int cornerColor) {
        this.cornerColor = cornerColor;
    }
    public boolean isSpecialAbility() {
        return this.specialAbility;
    }
    public void setSpecialAbility(boolean b) {
        this.specialAbility = b;
    }

    public void update() {
        int randomNum = (int) (Math.random() * 4);
        switch (randomNum) {
            case 0:
                setCornerColor(Color.RED);
                cornerPaint.setColor(Color.RED);
                break;
            case 1:
                setCornerColor(Color.BLUE);
                cornerPaint.setColor(Color.BLUE);
                break;
            case 2:
                setCornerColor(Color.YELLOW);
                cornerPaint.setColor(Color.YELLOW);
                break;
            case 3:
                setCornerColor(Color.GREEN);
                cornerPaint.setColor(Color.GREEN);
                break;
        }
    }

    public void draw(Canvas canvas) {
        if (!isSpecialAbility()) {
            canvas.drawText("Score: " + GamePanel.score, getCornerLength() + 10, getCornerLength(), textPaint);
            canvas.drawRect(0, 0, getCornerLength(), getCornerLength(), getCornerPaint());
            canvas.drawRect(0, GamePanel.HEIGHT - getCornerLength(), getCornerLength(), GamePanel.HEIGHT, getCornerPaint());
            canvas.drawRect(GamePanel.WIDTH - getCornerLength(), 0, GamePanel.WIDTH, getCornerLength(), getCornerPaint());
            canvas.drawRect(GamePanel.WIDTH - getCornerLength(), GamePanel.HEIGHT - getCornerLength(), GamePanel.WIDTH, GamePanel.HEIGHT, getCornerPaint());
        } else if (isSpecialAbility()) {
            canvas.drawText("Score: " + GamePanel.score, getCornerLength() + 10, getCornerLength(), textPaint);
            canvas.drawRect(0, 0, getCornerLength(), getCornerLength(), getTL_CornerPaint());
            canvas.drawRect(0, GamePanel.HEIGHT - getCornerLength() , getCornerLength(), GamePanel.HEIGHT, getBR_CornerPaint());
            canvas.drawRect(GamePanel.WIDTH - getCornerLength(), 0, GamePanel.WIDTH, getCornerLength(), getTR_CornerPaint());
            canvas.drawRect(GamePanel.WIDTH - getCornerLength(), GamePanel.HEIGHT - getCornerLength(), GamePanel.WIDTH, GamePanel.HEIGHT, getBL_CornerPaint());
        }
    }
}


