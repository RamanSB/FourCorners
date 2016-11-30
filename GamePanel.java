package ramansb.fourcorners;


import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.provider.Telephony;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by RamanSB on 22/09/15.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public static final int HEIGHT = 1920;
    public static final int WIDTH = 1080;
    private MainThread thread;
    private ArrayList<Ball> balls;
    public static int score = 0;
    private Background bgC;
    private long backgroundStartTime;
    protected Paint pausePaint;
    protected Paint swipePaint;
    Intent intentEnd;
    public static int updateCount;
    float scaleFactorY = (float) Game.HEIGHT/GamePanel.HEIGHT;
    float scaleFactorX = (float) Game.WIDTH/GamePanel.WIDTH;
    private Context passAsParameter;
    protected static boolean canvasWhite;
    private MediaPlayer mp;
    private MediaPlayer mpgover;
    private boolean mGameIsRunning;

    public GamePanel(Context context){
        super(context);
        setOnTouchListener(new OnSwipeTouchListener(context));
        init();
        passAsParameter = context;
        CustomPhoneStateListener phoneStateListener = new CustomPhoneStateListener(context);
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

        getHolder().addCallback(this);
        setFocusable(true);
        if(Game.sound){
            mpgover = MediaPlayer.create(passAsParameter, R.raw.gameendsfx);
            mp = MediaPlayer.create(passAsParameter, R.raw.bubblesoundsfx);
        }
    }

    public void init(){
        balls = new ArrayList<Ball>();
        bgC = new Background();
        pausePaint = new Paint();
        swipePaint = new Paint();
        canvasWhite = Game.canvasColor;
        if(canvasWhite) {
            pausePaint.setColor(Color.BLACK);
            swipePaint.setColor(Color.BLACK);
        }else{
            pausePaint.setColor(Color.WHITE);
            swipePaint.setColor(Color.WHITE);
        }

        swipePaint.setTypeface(Typeface.MONOSPACE);
        swipePaint.setLinearText(true);
        swipePaint.setTextAlign(Paint.Align.CENTER);
        swipePaint.setTextSize(50);

        pausePaint.setTypeface(Typeface.SANS_SERIF);
        pausePaint.setLinearText(true);
        pausePaint.setTextSize(100);
        pausePaint.setTextAlign(Paint.Align.CENTER);
        pausePaint.setFakeBoldText(true);
        pausePaint.setHinting(Paint.HINTING_ON);


       intentEnd = new Intent(getContext(), EndGameActivity.class);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

            thread = new MainThread(getHolder(), this);
            thread.setRunning(true);
            if(StartActivity.counter>0){
                thread.setPaused(true);
            }
            thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;
        while(retry){
            try{
                StartActivity.counter++;
                thread.setRunning(false);
                thread.join();
                retry = false;
            }catch(InterruptedException ex){}
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!thread.isPaused())
        {
            try {
                if (!bgC.isSpecialAbility()) {
                    if ((balls.get(0).getX() * scaleFactorX - balls.get(0).getRadius() <= event.getX() &&
                            event.getX() <= balls.get(0).getX() * scaleFactorX + balls.get(0).getRadius()) &&
                            (balls.get(0).getY() * scaleFactorY - (balls.get(0).getRadius()+10) <= event.getY() &&
                                    event.getY() <= balls.get(0).getY() * scaleFactorY + balls.get(0).getRadius())) {
                        if (balls.get(0).ballColor != bgC.getCornerColor()) {
                            balls.remove(0);
                            try{
                                mp.start();
                            }catch(NullPointerException ex){

                            }
                            score++;

                        } else {
                            thread.setRunning(false);
                            thread.setPaused(false);
                            try{
                                mpgover.start();
                            }catch(NullPointerException ex){}
                            intentEnd.putExtra("Score", GamePanel.score);
                            intentEnd.putExtra("Canvas", canvasWhite);

                            balls.clear();
                            getContext().startActivity(intentEnd);

                        }
                    }
                }

            } catch (IndexOutOfBoundsException ex) {
                ex.printStackTrace();
                balls.add(new Ball());
                balls.add(new Ball());

            }

            if (bgC.isSpecialAbility()) {
                try {
                    if ((balls.get(0).getX() * scaleFactorX - balls.get(0).getRadius() <= event.getX() &&
                            event.getX() <= balls.get(0).getX() * scaleFactorX + balls.get(0).getRadius()) &&
                            (balls.get(0).getY() * scaleFactorY - (balls.get(0).getRadius()+10) <= event.getY() &&
                                    event.getY() <= balls.get(0).getY() * scaleFactorY + balls.get(0).getRadius())) {
                        score++;
                        try {
                            mp.start();
                        } catch (NullPointerException ex) {

                        }
                        balls.remove(0);
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public void update() {
        if (balls.isEmpty()) {
            balls.add(new Ball());
        }
        balls.get(0).update();
        if(score<=25 && balls.get(0).getY()<GamePanel.HEIGHT*5/7) {
            if (updateCount % 200 == 0) {
                bgC.setSpecialAbility(false);
                bgC.update();
            } else if (updateCount % 500 == 0) {
                bgC.update();
            }else if(updateCount % 1900 == 0){
                bgC.setSpecialAbility(true);
                updateCount = 0;
            }
        }
        if(score>=25 && balls.get(0).getY()<GamePanel.HEIGHT*2/3) {
            if (updateCount % 300 == 0) {
                bgC.setSpecialAbility(false);
                bgC.update();
            } else if (updateCount % 600 == 0) {
                bgC.update();
            }else if(updateCount % 2100 == 0){
                bgC.setSpecialAbility(true);
                updateCount = 0;
            }
        }
        if(score>=100 && balls.get(0).getY()<GamePanel.HEIGHT*3/7) {
            if (updateCount % 300 == 0) {
                bgC.setSpecialAbility(false);
                bgC.update();
            } else if (updateCount % 600 == 0) {
                bgC.update();
            }else if(updateCount % 2100 == 0){
                bgC.setSpecialAbility(true);
                updateCount = 0;
            }
        }

        if (balls.get(0).getY() >= GamePanel.HEIGHT + balls.get(0).getRadius()) {
            if(balls.get(0).getBallColor() != bgC.getCornerColor()) {
                if (bgC.isSpecialAbility()) {
                } else {
                        thread.setRunning(false);
                        thread.setPaused(false);
                        intentEnd.putExtra("Score", GamePanel.score);
                        intentEnd.putExtra("Canvas", canvasWhite);
                    balls.clear();
                    try{
                        mpgover.start();
                    }catch(NullPointerException ex){}
                    getContext().startActivity(intentEnd);
                }
            }
            balls.remove(0);
        }
        updateCount++;

    }

    public void draw(Canvas canvas){
        if(canvas!=null){
            final float scaleFactorX = getWidth() / (WIDTH * 1.f);
            final float scaleFactorY = getHeight() / (HEIGHT * 1.f);
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            if(canvasWhite){
                canvas.drawColor(Color.WHITE);
            }
            else{
                canvas.drawColor(Color.BLACK);
            }

            if(balls.isEmpty()){
                balls.add(new Ball());
            }else {
                balls.get(0).draw(canvas);
            }

            bgC.draw(canvas);

            if(thread.isPaused()){
                canvas.drawText("PAUSED", GamePanel.WIDTH/2, GamePanel.HEIGHT/2, pausePaint);
                canvas.drawText("Swipe Left To Play", GamePanel.WIDTH/2, GamePanel.HEIGHT*0.8f , swipePaint);
            }
            canvas.restoreToCount(savedState);
        }
    }

     class OnSwipeTouchListener implements OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener(Context ctx) {
            gestureDetector = new GestureDetector(ctx, new GestureListener());
        }

        public boolean onTouch(final View view, final MotionEvent motionEvent) {
            return gestureDetector.onTouchEvent(motionEvent);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }
        public void onSwipeRight() {
            thread.setPaused(true);
        }
        public void onSwipeLeft() {
            thread.setPaused(false);
        }
    }

    private class CustomPhoneStateListener extends PhoneStateListener {

        //private static final String TAG = "PhoneStateChanged";
        Context context; //Context to make Toast if required
        public CustomPhoneStateListener(Context context) {
            super();
            this.context = context;
        }

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);

            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    Toast.makeText(context, "Incoming Call", Toast.LENGTH_LONG).show();
                    thread.setPaused(true);
                    break;
                default:
                    break;
            }
        }
    }

}
