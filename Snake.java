import com.sun.tracing.dtrace.ProviderAttributes;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Snake {
    int w = Yard.BLOCK_SIZE;
    int h = Yard.BLOCK_SIZE;
    int row,col;

    private int size = 0 ;
    private Node n = new Node(5,10,Dir.R);
    private Node head =null;
    private Node tail = null;
    private Yard y;

    public Snake(Yard y) {
        head = n;
        tail = n;
        size =1;
        this.y = y;
    }

    public void addToHead()
    {
        Node node = null;
        switch (head.dir)
        {
            case L:
                node = new Node(head.rows,head.cols-1,head.dir);
                break;
            case R:
                node = new Node(head.rows,head.cols+1,head.dir);
                break;
            case U:
                node = new Node(head.rows-1,head.cols,head.dir);
                break;
            case D:
                node = new Node(head.rows+1,head.cols,head.dir);
                break;
        }
        head.prev = node;
        node.next = head;
        head = node;
        size++;
    }

    public void addToTail()
    {
        Node node = null;
        switch (tail.dir)
        {
            case L:
                node = new Node(tail.rows,tail.cols+1,tail.dir);
                break;
            case R:
                node = new Node(tail.rows,tail.cols-1,tail.dir);
                break;
            case U:
                node = new Node(tail.rows+1,tail.cols,tail.dir);
                break;
            case D:
                node = new Node(tail.rows-1,tail.cols,tail.dir);
                break;
        }
        tail.next = node;
        node.prev = tail;
        tail = node;
        size ++ ;
    }

    public void deleteFromTail()
    {
        if(size == 0) return;
        tail = tail.prev;
        tail.next = null;
    }

    public void draw(Graphics g)
    {
        if(size<=0) return;
        move();
        for (Node n =head;n!=null;n = n.next )
        {
            n.draw(g);
        }
    }

    public void move()
    {
        addToHead();
        deleteFromTail();
    }
    public Rectangle getRect(){
        return new Rectangle(head.cols * Yard.BLOCK_SIZE,head.rows*Yard.BLOCK_SIZE,w,h );
    }

    public void eat(Egg e)
    {
        if (this.getRect().intersects(e.getRect()))
        {

            this.addToHead();
            e.repair();
            y.score += 5;
        }
        checkDead();
    }

    public void checkDead()
    {
        if(head.rows<3 || head.cols<2 || head.rows>Yard.ROWS-2 || head.cols>Yard.COLS-2  )
        {
            y.stop();

        }
        for(Node n = head.next;n!=null;n = n.next)
        {
            if(head.rows == n.rows && head.cols == n.cols)
            {
                y.stop();
            }
        }



    }

    private class Node{
        int width = Yard.BLOCK_SIZE;
        int height = Yard.BLOCK_SIZE;
        int rows,cols;
        Dir dir =Dir.L;
        Node next;
        Node prev;

        public Node(int rows, int cols, Dir dir) {
            this.rows = rows;
            this.cols = cols;
            this.dir = dir;
        }

        public void draw(Graphics g)
        {
            Color c = g.getColor();
            g.setColor(Color.BLACK);
            g.fillRect(cols*Yard.BLOCK_SIZE,rows*Yard.BLOCK_SIZE,width,height);
            g.setColor(c);
        }


    }
    public void keyEvent(KeyEvent e)
    {
        int key =e.getKeyCode();
        switch (key)
        {
            case KeyEvent.VK_LEFT:
                if (head.dir != Dir.R )
                {
                    head.dir=Dir.L;
                }

                break;
            case KeyEvent.VK_RIGHT:
                if(head.dir != Dir.L)
                {
                    head.dir=Dir.R;
                }
                break;
            case KeyEvent.VK_UP:
                if(head.dir != Dir.D)
                {
                    head.dir=Dir.U;
                }
                break;
            case KeyEvent.VK_DOWN:
                if(head.dir != Dir.U)
                {
                    head.dir=Dir.D;
                }
                break;
        }
    }
}
