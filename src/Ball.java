
import java.util.function.DoubleToLongFunction;

public class Ball {
    
    public Rect rect;
    public Rect leftPaddle, rightPaddle;
    public Text leftScoreText, rightScoreText;

    private double vy = 250.0;
    private double vx = -150.0;

    public Ball(Rect rect, Rect leftPaddle, Rect rightPaddle, Text leftScoreText, Text rightScoreText)
    {
        this.rect = rect;
        this.leftPaddle = leftPaddle;
        this.rightPaddle = rightPaddle;
        this.leftScoreText = leftScoreText;
        this.rightScoreText = rightScoreText;
    }

    public double calculateNewVelocityAngle(Rect paddle)
    {
        double relativeIntersectY = (paddle.y + (paddle.height / 2.0)) - (this.rect.y + (this.rect.height / 2.0));
        double normalIntersectY = relativeIntersectY / (paddle.height / 2.0);
        double theta = normalIntersectY * Constants.Max_Angle;

        return Math.toRadians(theta);
    }

    public void update(double dt)
    {
        if (vx < 0)
        {
            if (this.rect.x <= this.leftPaddle.x + this.leftPaddle.width && this.rect.x  + this.rect.width >= this.leftPaddle.x && this.rect.y >= this.leftPaddle.y && this.rect.y <= this.leftPaddle.y + this.leftPaddle.height)
            {
                double theta = calculateNewVelocityAngle(leftPaddle);
                double newVx = Math.abs((Math.cos(theta)) * Constants.Ball_Speed);
                double newVy = Math.abs((Math.sin(theta)) * Constants.Ball_Speed);

                double oldSign = Math.signum(vx);
                this.vx = newVx * (-1.0 * oldSign);
                this.vy = newVy;
            }
            else if (this.rect.x + this.rect.width < this.leftPaddle.x)
            {
                int rightScore = Integer.parseInt(rightScoreText.text);
                rightScore++;
                rightScoreText.text = "" + rightScore;

                this.rect.x = Constants.Screen_Width / 2.0;
                this.rect.y = Constants.Screen_Width / 2.0;
                this.vx = -150.0;
                this.vy = 250.0;

                if (rightScore > Constants.Win_Score)
                {
                    Main.changeState(0);
                }
            }
        }
        else if (vx > 0)
        {
            if (this.rect.x + this.rect.width >= this.rightPaddle.x && this.rect.x <= this.rightPaddle.x + this.rightPaddle.width && this.rect.y >= this.rightPaddle.y && this.rect.y <= this.rightPaddle.y + this.rightPaddle.height)
            {
                double theta = calculateNewVelocityAngle(rightPaddle);
                double newVx = Math.abs((Math.cos(theta)) * Constants.Ball_Speed);
                double newVy = Math.abs((Math.sin(theta)) * Constants.Ball_Speed);

                double oldSign = Math.signum(vx);
                this.vx = newVx * (-1.0 * oldSign);
                this.vy = newVy;
            }
            else if (this.rect.x + this.rect.width > this.rightPaddle.x + this.rightPaddle.width)
            {
                int leftScore = Integer.parseInt(leftScoreText.text);
                leftScore++;
                leftScoreText.text = "" + leftScore;

                this.rect.x = Constants.Screen_Width / 2.0;
                this.rect.y = Constants.Screen_Width / 2.0;
                this.vx = 250.0;
                this.vy = -150.0;

                if (leftScore > Constants.Win_Score)
                {
                    Main.changeState(0);
                }
            }
        }

        if (vy > 0)
        {
            if (this.rect.y + this.rect.height > Constants.Screen_Height)
            {
                this.vy *= -1;
            }
        }
        else if (vy < 0)
        {
            if (this.rect.y < Constants.Toolbar_Top)
            {
                this.vy *= -1;
            }
        }

        this.rect.x += vx * dt;
        this.rect.y += vy * dt;
    }
}
