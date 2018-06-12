import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Yard extends Frame {
    public static final int ROWS = 30;
    public static final int COLS = 30;
    public static final int BLOCK_SIZE = 15;

    public boolean gameOver = false;
    public int score = 0;
    //public boolean pause = false;
    private Font fontGameOver = new Font("宋体", Font.BOLD, 50);

    PaintThrad paintthread = new PaintThrad();

    Egg e = new Egg(5 + 1, 5);

    Image offScreenImage = null;

    public Snake s = new Snake(this);

    Thread t = new Thread(paintthread);
    public static void main(String[] args) {

        new Yard().launch();

    }

    public void launch() {
        setBackground(Color.GRAY);
        setBounds(300, 100, COLS * BLOCK_SIZE + 9, ROWS * BLOCK_SIZE + 9);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                System.exit(-1);
            }
        });
        addKeyListener(new KeyMonitor());
        t.start();
    }

    public void stop() {
        gameOver = true;
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.drawRect(0, 0, COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
        for (int i = 1; i < ROWS; i++) {
            g.drawLine(0, BLOCK_SIZE * i, COLS * BLOCK_SIZE, BLOCK_SIZE * i);
        }
        for (int i = 1; i < COLS; i++) {
            g.drawLine(BLOCK_SIZE * i, 0, BLOCK_SIZE * i, BLOCK_SIZE * ROWS);
        }
        g.setColor(Color.red);
        g.drawString("score: " + score, 40, 50);
        if (gameOver) {
            g.setColor(Color.GREEN);
            //g.drawString("press f12  to restart", 120, 250);
            g.setColor(Color.red);
            g.setFont(fontGameOver);
            g.drawString("游戏结束", 120, 220);
        }
        e.draw(g);
        s.draw(g);
        s.eat(e);
        g.setColor(c);
    }

//    @Override
//    public void update(Graphics g) {
//        if(offScreenImage == null)
//        {
//            offScreenImage = this.createImage(COLS*BLOCK_SIZE,ROWS*BLOCK_SIZE);
//        }
//        Graphics gOff = offScreenImage.getGraphics();
//        paint(gOff);
//        g.drawImage(offScreenImage,0,0,null);
//
//    }


    private class PaintThrad implements Runnable {

        @Override
        public void run() {
            while (!gameOver) {
                    repaint();
                    try {
                        Thread.sleep(130);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

            }
            repaint();
        }

        public void reStart()
        {
            if(gameOver)
            {
                Snake s = new Snake(Yard.this);
                gameOver=false;
                System.out.println(gameOver);

            }
        }
    }


    private class KeyMonitor extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {

            s.keyEvent(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_F12)
            {
                paintthread.reStart();
            }

        }
    }
}
