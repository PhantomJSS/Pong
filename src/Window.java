import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.lang.invoke.ConstantCallSite;
import javax.swing.JFrame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class Window extends JFrame implements Runnable{

    public Graphics2D g2;
    public KL keyListener = new KL();
    public Rect playerOne;
    public Rect ai;
    public Rect ball;
    public PlayerController playerController;
    public AIController aiController;
    public Ball gameBall;
    public Text leftScoreText, rightScoreText;
    public boolean isRunning = true;

    public Window()
    {
        this.setSize(Constants.Screen_Width, Constants.Screen_Height);
        this.setTitle(Constants.Screen_Title);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(keyListener);
        Constants.Toolbar_Top = this.getInsets().top;
        Constants.Toolbar_Bottom = this.getInsets().bottom;
        g2 = (Graphics2D)this.getGraphics();

        playerOne = new Rect(40, 60, Constants.Paddle_Width, Constants.Paddle_Height, Color.blue);
        playerController = new PlayerController(playerOne, keyListener);
        ai = new Rect(Constants.Screen_Width - 60, 450, Constants.Paddle_Width, Constants.Paddle_Height, Color.red);
        ball = new Rect(Constants.Screen_Width/2, Constants.Screen_Height/2, Constants.Ball_WH, Constants.Ball_WH, Color.white);
        leftScoreText = new Text(0, new Font("Times New Roman", Font.BOLD, Constants.Text_Size), 25, Constants.Text_Y_Pos);
        rightScoreText = new Text(0, new Font("Times New Roman", Font.BOLD, Constants.Text_Size), Constants.Screen_Width - 25 - 16, Constants.Text_Y_Pos);
        gameBall = new Ball(ball, playerOne, ai, leftScoreText, rightScoreText);
        aiController = new AIController(new PlayerController(ai), ball);
    }

    public void update(double dt)
    {
        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();
        this.draw(dbg);
        g2.drawImage(dbImage, 0, 0, this);
        
        playerController.update(dt);
        aiController.update(dt);
        gameBall.update(dt);
    }

    public void draw(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, Constants.Screen_Width, Constants.Screen_Height);
    
        leftScoreText.draw(g2);
        rightScoreText.draw(g2);

        playerOne.draw(g2);
        ai.draw(g2);
        ball.draw(g2);
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
