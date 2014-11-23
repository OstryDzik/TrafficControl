package GraphicalInterfaceModule;

import Common.Car;
import Common.CarsInfo;
import Common.LightsInfo;
import Common.WorldInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Observable;

/**
 * Created by Filip on 2014-11-01.
 */
public class WorldPanel extends JPanel
{
    private WorldInfo worldInfo;
    private float X(float x, int idX)
    {
        return (x + idX * 120) * scale + offsetX;
    }
    private float Y(float y, int idY)
    {
        return (y + idY * 120) * scale + offsetY;
    }
    private float scale;
    private float offsetX;
    private float offsetY;

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        float w = getWidth();
        float h = getHeight();
        float d = Math.min(w, h);

        scale = Math.min(h, w) / 460.0f;
        offsetX = (w - d) / 2;
        offsetY = (h - d) / 2;

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.gray);
        for (int y = 0; y < 4; ++y)
        {
            for (int x = 0; x < 4; ++x)
            {
                g2d.fill(new Rectangle.Float(x * 120 * scale + offsetX, y * 120 * scale + offsetY, 100 * scale, 100 * scale));
            }
        }

        g2d.setColor(Color.black);
        float y = 100;
        for (int i = 0; i < 3; ++i)
        {
            g2d.fill(new Rectangle.Float(offsetX, y * scale + offsetY, 460 * scale, 20 * scale));
            g2d.fill(new Rectangle.Float(y * scale + offsetX, offsetY, 20 * scale, 460 * scale));
            y += 120;
        }

        Color redSolid = new Color(1.0f, 0.0f, 0.0f, 0.8f);
        Color redTransparent = new Color(0.50f, 0.0f, 0.0f, 0.0f);
        Color greenSolid = new Color(0.0f, 1.0f, 0.0f, 0.8f);
        Color greenTransparent = new Color(0.0f, 0.5f, 0.0f, 0.0f);
        Color orangeSolid = new Color(1.0f, 0.5f, 0.01f, 0.8f);
        Color orangeTransparent = new Color(0.0f, 0.5f, 0.0f, 0.0f);

        if (worldInfo == null)
        {
            for (int idY = 0; idY < 3; ++idY)
            {
                for (int idX = 0; idX < 3; ++idX)
                {
                    g2d.setPaint(new GradientPaint(X(60, idX), Y(100, idY), greenTransparent, X(100, idX), Y(100, idY), greenSolid));
                    g2d.fill(new Rectangle.Float(X(60, idX), Y(100, idY), 40 * scale, 20 * scale));

                    g2d.setPaint(new GradientPaint(X(120, idX), Y(100, idY), greenSolid, X(160, idX), Y(100, idY), greenTransparent));
                    g2d.fill(new Rectangle.Float(X(120, idX), Y(100, idY), 40 * scale, 20 * scale));

                    // czerwone
                    g2d.setPaint(new GradientPaint(X(100, idX), Y(90, idY), redTransparent, X(100, idX), Y(100, idY), redSolid));
                    g2d.fill(new Rectangle.Float(X(100, idX), Y(90, idY), 20 * scale, 10 * scale));

                    g2d.setPaint(new GradientPaint(X(100, idX), Y(120, idY), redSolid, X(100, idX), Y(130, idY), redTransparent));
                    g2d.fill(new Rectangle.Float(X(100, idX), Y(120, idY), 20 * scale, 10 * scale));

                    g2d.setColor(greenSolid);
                    g2d.fill(new Rectangle.Float(X(100, idX), Y(100, idY), 20 * scale, 20 * scale));
                }
            }
        }
        else
        {
            LightsInfo lightsMockup = worldInfo.getLightsMockup();
            for (int idY = 0; idY < 3; ++idY)
            {
                for (int idX = 0; idX < 3; ++idX)
                {
                    LightsInfo.LightsState light = lightsMockup.getStates()[3 * idY + idX];
                    if (light == LightsInfo.LightsState.HORIZONTALLY_GREEN)
                    {
                        // zielone
                        g2d.setPaint(new GradientPaint(X(60, idX), Y(100, idY), greenTransparent, X(100, idX), Y(100, idY), greenSolid));
                        g2d.fill(new Rectangle.Float(X(60, idX), Y(100, idY), 40 * scale, 20 * scale));

                        g2d.setPaint(new GradientPaint(X(120, idX), Y(100, idY), greenSolid, X(160, idX), Y(100, idY), greenTransparent));
                        g2d.fill(new Rectangle.Float(X(120, idX), Y(100, idY), 40 * scale, 20 * scale));

                        // czerwone
                        g2d.setPaint(new GradientPaint(X(100, idX), Y(90, idY), redTransparent, X(100, idX), Y(100, idY), redSolid));
                        g2d.fill(new Rectangle.Float(X(100, idX), Y(90, idY), 20 * scale, 10 * scale));

                        g2d.setPaint(new GradientPaint(X(100, idX), Y(120, idY), redSolid, X(100, idX), Y(130, idY), redTransparent));
                        g2d.fill(new Rectangle.Float(X(100, idX), Y(120, idY), 20 * scale, 10 * scale));

                        g2d.setColor(greenSolid);
                        g2d.fill(new Rectangle.Float(X(100, idX), Y(100, idY), 20 * scale, 20 * scale));
                    }
                    else if (light == LightsInfo.LightsState.HORIZONTALLY_ORANGE)
                    {
                        // pomarańczowe
                        g2d.setPaint(new GradientPaint(X(60, idX), Y(100, idY), orangeTransparent, X(100, idX), Y(100, idY), orangeSolid));
                        g2d.fill(new Rectangle.Float(X(60, idX), Y(100, idY), 40 * scale, 20 * scale));

                        g2d.setPaint(new GradientPaint(X(120, idX), Y(100, idY), orangeSolid, X(160, idX), Y(100, idY), orangeTransparent));
                        g2d.fill(new Rectangle.Float(X(120, idX), Y(100, idY), 40 * scale, 20 * scale));

                        // czerwone
                        g2d.setPaint(new GradientPaint(X(100, idX), Y(90, idY), redTransparent, X(100, idX), Y(100, idY), redSolid));
                        g2d.fill(new Rectangle.Float(X(100, idX), Y(90, idY), 20 * scale, 10 * scale));

                        g2d.setPaint(new GradientPaint(X(100, idX), Y(120, idY), redSolid, X(100, idX), Y(130, idY), redTransparent));
                        g2d.fill(new Rectangle.Float(X(100, idX), Y(120, idY), 20 * scale, 10 * scale));

                        g2d.setColor(orangeSolid);
                        g2d.fill(new Rectangle.Float(X(100, idX), Y(100, idY), 20 * scale, 20 * scale));
                    }
                    else if (light == LightsInfo.LightsState.VERTICALLY_ORAGNE)
                    {
                        // czerwone
                        g2d.setPaint(new GradientPaint(X(90, idX), Y(100, idY), redTransparent, X(100, idX), Y(100, idY), redSolid));
                        g2d.fill(new Rectangle.Float(X(90, idX), Y(100, idY), 10 * scale, 20 * scale));

                        g2d.setPaint(new GradientPaint(X(120, idX), Y(100, idY), redSolid, X(130, idX), Y(100, idY), redTransparent));
                        g2d.fill(new Rectangle.Float(X(120, idX), Y(100, idY), 10 * scale, 20 * scale));

                        // pomaranczowe
                        g2d.setPaint(new GradientPaint(X(100, idX), Y(60, idY), orangeTransparent, X(100, idX), Y(100, idY), orangeSolid));
                        g2d.fill(new Rectangle.Float(X(100, idX), Y(60, idY), 20 * scale, 40 * scale));

                        g2d.setPaint(new GradientPaint(X(100, idX), Y(120, idY), orangeSolid, X(100, idX), Y(160, idY), orangeTransparent));
                        g2d.fill(new Rectangle.Float(X(100, idX), Y(120, idY), 20 * scale, 40 * scale));

                        g2d.setColor(orangeSolid);
                        g2d.fill(new Rectangle.Float(X(100, idX), Y(100, idY), 20 * scale, 20 * scale));
                    }
                    else
                    {
                        // czerwone
                        g2d.setPaint(new GradientPaint(X(90, idX), Y(100, idY), redTransparent, X(100, idX), Y(100, idY), redSolid));
                        g2d.fill(new Rectangle.Float(X(90, idX), Y(100, idY), 10 * scale, 20 * scale));

                        g2d.setPaint(new GradientPaint(X(120, idX), Y(100, idY), redSolid, X(130, idX), Y(100, idY), redTransparent));
                        g2d.fill(new Rectangle.Float(X(120, idX), Y(100, idY), 10 * scale, 20 * scale));

                        // zielone
                        g2d.setPaint(new GradientPaint(X(100, idX), Y(60, idY), greenTransparent, X(100, idX), Y(100, idY), greenSolid));
                        g2d.fill(new Rectangle.Float(X(100, idX), Y(60, idY), 20 * scale, 40 * scale));

                        g2d.setPaint(new GradientPaint(X(100, idX), Y(120, idY), greenSolid, X(100, idX), Y(160, idY), greenTransparent));
                        g2d.fill(new Rectangle.Float(X(100, idX), Y(120, idY), 20 * scale, 40 * scale));

                        g2d.setColor(greenSolid);
                        g2d.fill(new Rectangle.Float(X(100, idX), Y(100, idY), 20 * scale, 20 * scale));
                    }

                }
            }
            CarsInfo carsInfo = worldInfo.getCarsInfo();
            if (carsInfo != null)
            {
                for (Car car : carsInfo.getCars())
                {
                    float cx,cy,cw,ch;
                    cx = (car.getX()*10)*scale + offsetX;
                    cy = (car.getY()*10)*scale + offsetY;
                    cw = 10*scale;
                    ch = 10*scale;
                    g2d.setColor(car.getColor());
                    g2d.fill(new Ellipse2D.Float(cx, cy, cw, ch));
                }
            }
        }
    }

    public void update(Observable o, Object arg)
    {
        System.out.println("Update");
        repaint();
    }

    public void update(WorldInfo mockup)
    {
        worldInfo = mockup;
        System.out.println("Update");
        repaint();
    }


}
