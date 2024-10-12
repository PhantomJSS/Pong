import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.lang.invoke.ConstantCallSite;
import javax.swing.JFrame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import jdk.jfr.Timespan;

public class MainMenu extends JFrame implements Runnable{

    public Graphics2D g2;
    public KL keyListener = new KL();
    public ML mouseListener = new ML();
    public Text startGame, exitGame, pong;
    public boolean isRunning = true;

    public MainMenu()
    {
        this.setSize(Constants.Screen_Width, Constants.Screen_Height);
        this.setTitle(Constants.Screen_Title);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(keyListener);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.pong = new Text("PONG", new Font("Times New Roman", Font.BOLD, 60), Constants.Screen_Width / 2.0 - 89.0, Constants.Screen_Height / 2.0 - 100.0, Color.white);
        this.startGame = new Text("Start Game", new Font("Times New Roman", Font.BOLD, 40), Constants.Screen_Width / 2.0 - 100.0, Constants.Screen_Height / 2.0, Color.white);
        this.exitGame = new Text("Exit", new Font ("Times New Roman", Font.BOLD, 40), Constants.Screen_Width / 2.0 - 40.0, Constants.Screen_Height / 2.0 + 60.0, Color.white);
    
        g2 = (Graphics2D)getGraphics();
    }

    public void update(double dt)
    {
        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();
        this.draw(dbg);
        g2.drawImage(dbImage, 0, 0, this);

        if (mouseListener.getMouseX() > startGame.x && mouseListener.getMouseX() < startGame.x + startGame.width && mouseListener.getMouseY() > startGame.y - startGame.height / 2 && mouseListener.getMouseY() < startGame.y + startGame.height / 2)
        {
            startGame.color = new Color(158, 158, 158);

            if (mouseListener.isMousePressed())
            {
                Main.changeState(1);
            }
        }
        else
        {
            startGame.color = Color.WHITE;
        }

        if (mouseListener.getMouseX() > exitGame.x && mouseListener.getMouseX() < exitGame.x + exitGame.width && mouseListener.getMouseY() > exitGame.y - exitGame.height / 2 && mouseListener.getMouseY() < exitGame.y + exitGame.height / 2)
        {
            exitGame.color = new Color(158, 158, 158);

            if (mouseListener.isMousePressed())
            {
                this.dispose();
            }
        }
        else
        {
            exitGame.color = Color.WHITE;
        }
    }

    public void draw(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, Constants.Screen_Width, Constants.Screen_Height);

        startGame.draw(g2);
        exitGame.draw(g2);
        pong.draw(g2);
    }

    public void stop()
    {
        isRunning = false;
    }

    public void run()
    {
        double lastFrameTime = 0.0;

        while (isRunning) 
        { 
            double time = Time.getTime();
            double deltaTime = time - lastFrameTime;
            lastFrameTime = time;

            update(deltaTime);
        }
        this.dispose();
        return;
    }

}

