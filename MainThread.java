package ramansb.fourcorners;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;

/**
 * Created by RamanSB on 22/09/15.
 */
public class MainThread extends Thread {


    private static final int FPS = 100;
    private long averageFPS;
    private boolean paused;
    private boolean running;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private static Canvas canvas;
    private Object mPauseLock = new Object();

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    public void run(){

        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000/FPS;


        while(running){
            startTime = System.nanoTime();
            canvas = null;

            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.gamePanel.update();

                    while(running & paused){
                        try{

                            this.gamePanel.draw(canvas);
                            Thread.sleep(50);

                            if(canvas!=null){
                                try{
                                    surfaceHolder.unlockCanvasAndPost(canvas);
                                    surfaceHolder.lockCanvas();
                                }catch(Exception ex){
                                    ex.printStackTrace();
                                }
                            }

                        }catch(Exception ex){
                            System.out.println("Error in pause");
                        }
                    }
                    this.gamePanel.draw(canvas);
                }

            }catch(Exception e){
                Log.e("Eratum", "main thread");
            }
            finally{
                if(canvas!=null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime)/1000000;

            waitTime = targetTime - timeMillis;

            try{
                this.sleep(waitTime);
            }catch(Exception e){

            }

            totalTime += System.nanoTime()-startTime;
            frameCount++;

            if(frameCount == FPS){
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
            }
        }
    }

    public void setRunning(boolean b){
        this.running = b;
    }
    public void setPaused(boolean b){
        this.paused = b;
    }
    public boolean isPaused(){
        return paused;
    }

}



