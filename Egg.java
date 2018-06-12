import java.awt.*;
import java.util.Random;

public class Egg {
    int row,col;
    int w = Yard.BLOCK_SIZE;
    int h =Yard.BLOCK_SIZE;
    Random r = new Random();
    public Egg(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void repair()
    {
         this.row =  r.nextInt(Yard.ROWS-3)+3;
         this.col = r.nextInt(Yard.COLS-2)+2;

    }


    public Rectangle getRect(){
        return new Rectangle(Yard.BLOCK_SIZE*col,Yard.BLOCK_SIZE*row,w,h);
    }

    public void draw(Graphics g)
    {
        Color c =  g.getColor();
        g.setColor(Color.GREEN);
        g.fillOval(col*Yard.BLOCK_SIZE,row*Yard.BLOCK_SIZE,w,h);
        g.setColor(c);
    }

}
