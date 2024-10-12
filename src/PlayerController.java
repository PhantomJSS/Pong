
import com.sun.jdi.event.ThreadStartEvent;
import java.awt.event.KeyEvent;

public class PlayerController {

    public Rect rect;
    public KL keyListener;

    public PlayerController(Rect rect, KL keyListener)
    {
        this.rect = rect;
        this.keyListener = keyListener;
    }

    public PlayerController(Rect rect)
    {
        this.rect = rect;
        this.keyListener = null;
    }

    public void update(double dt)
    {
        if (keyListener != null)
        {
            if (keyListener.isKeyPressed(KeyEvent.VK_DOWN))
            {
                moveDown(dt);    
            }
            else if (keyListener.isKeyPressed(KeyEvent.VK_UP))
            {
                moveUp(dt);
            }
        } 
    }

    public void moveUp(double dt)
    {
        if (rect.y - Constants.Paddle_Speed * dt > Constants.Toolbar_Top)
        {
            this.rect.y -= Constants.Paddle_Speed * dt;
        }
    }

    public void moveDown(double dt)
    {
        if ((rect.y + Constants.Paddle_Speed * dt) + rect.height < Constants.Screen_Height - Constants.Toolbar_Bottom)
        {
            this.rect.y += Constants.Paddle_Speed * dt;
        } 
    }
    
}
