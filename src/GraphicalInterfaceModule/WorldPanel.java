package GraphicalInterfaceModule;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import Model.Car;
import Model.CarsInfo;
import Model.LightsInfo;

/**
 * Created by Filip on 2014-11-01.
 */

class WorldInfo
{
	private LightsInfo lightsInfo = null;
	private CarsInfo carsInfo = null;

	public WorldInfo()
	{
		lightsInfo = new LightsInfo();
		carsInfo = new CarsInfo(new ArrayList<Car>());
	}

	public void put(Object obj)
	{
		// świat sam rozpozna typ obiektu
		if (obj instanceof LightsInfo)
		{
			lightsInfo = (LightsInfo) obj;
			InterfaceFrame.writeToConsole("# Zaktualizowana lista świateł");
		} else if (obj instanceof CarsInfo)
		{
			carsInfo = (CarsInfo) obj;
			InterfaceFrame.writeToConsole("# Zaktualizowana lista samochodów");
		}
	}

	public LightsInfo getLightsMockup()
	{
		// TODO Auto-generated method stub
		return lightsInfo;
	}

	public CarsInfo getCarsInfo()
	{
		// TODO Auto-generated method stub
		return carsInfo;
	}

	public static interface WorldInfoElement
	{
	}

}

public class WorldPanel extends JPanel
{
	private static WorldPanel instance = null;
	private WorldInfo worldInfo = new WorldInfo();

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

	public WorldPanel()
	{
		super();
		instance = this;

		this.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent mouseEvent)
			{
				int X = (int) ((float)mouseEvent.getX()/getWidth()*46);
				int Y = (int) ((float)mouseEvent.getY()/getHeight()*46);
				
				Car car = new Car();
				car.setX(X);
				car.setY(Y);
				
				GUIConnection.carsToAdd.add(car);
				
				worldInfo.getCarsInfo().addCar(car);
				WorldPanel.this.invalidate();
				WorldPanel.this.repaint();
			}
		});
	}

	public static WorldPanel getInstance()
	{
		return instance;
	}

	private enum Direction
	{
		TOP, BOTTOM, LEFT, RIGHT
	};

	private void paintLight(Graphics2D g2d, Direction direction, int idX, int idY)
	{
		switch (direction)
		{
		case LEFT:
			g2d.fill(new Ellipse2D.Float(X(88, idX), Y(122, idY), 10 * scale, 10 * scale));
			break;
		case RIGHT:
			g2d.fill(new Ellipse2D.Float(X(122, idX), Y(87, idY), 10 * scale, 10 * scale));
			break;
		case TOP:
			g2d.fill(new Ellipse2D.Float(X(88, idX), Y(87, idY), 10 * scale, 10 * scale));
			break;
		case BOTTOM:
			g2d.fill(new Ellipse2D.Float(X(122, idX), Y(122, idY), 10 * scale, 10 * scale));
			break;
		}
	}

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

		if (worldInfo == null)
		{
			for (int idY = 0; idY < 3; ++idY)
			{
				for (int idX = 0; idX < 3; ++idX)
				{
					// zielone
					g2d.setColor(Color.green);
					paintLight(g2d, Direction.LEFT, idX, idY);
					paintLight(g2d, Direction.RIGHT, idX, idY);

					// czerwone
					g2d.setColor(Color.red);
					paintLight(g2d, Direction.TOP, idX, idY);
					paintLight(g2d, Direction.BOTTOM, idX, idY);
				}
			}
		} else
		{
			LightsInfo lightsMockup = worldInfo.getLightsMockup();
			if (lightsMockup != null)
			{
				for (int idY = 0; idY < 3; ++idY)
				{
					for (int idX = 0; idX < 3; ++idX)
					{
						LightsInfo.LightsState light = lightsMockup.getStates()[3 * idY + idX];
						if (light == LightsInfo.LightsState.HORIZONTALLY_GREEN)
						{
							// zielone
							g2d.setColor(Color.green);
							paintLight(g2d, Direction.LEFT, idX, idY);
							paintLight(g2d, Direction.RIGHT, idX, idY);

							// czerwone
							g2d.setColor(Color.red);
							paintLight(g2d, Direction.TOP, idX, idY);
							paintLight(g2d, Direction.BOTTOM, idX, idY);
						} else if (light == LightsInfo.LightsState.HORIZONTALLY_ORANGE)
						{
							// zielone
							g2d.setColor(Color.orange);
							paintLight(g2d, Direction.LEFT, idX, idY);
							paintLight(g2d, Direction.RIGHT, idX, idY);

							// czerwone
							g2d.setColor(Color.red);
							paintLight(g2d, Direction.TOP, idX, idY);
							paintLight(g2d, Direction.BOTTOM, idX, idY);
						} else if (light == LightsInfo.LightsState.VERTICALLY_ORANGE)
						{
							// czerwone
							g2d.setColor(Color.red);
							paintLight(g2d, Direction.LEFT, idX, idY);
							paintLight(g2d, Direction.RIGHT, idX, idY);

							// pomarańczowe
							g2d.setColor(Color.orange);
							paintLight(g2d, Direction.TOP, idX, idY);
							paintLight(g2d, Direction.BOTTOM, idX, idY);
						} else
						{
							// czerwone
							g2d.setColor(Color.red);
							paintLight(g2d, Direction.LEFT, idX, idY);
							paintLight(g2d, Direction.RIGHT, idX, idY);

							// zielone
							g2d.setColor(Color.green);
							paintLight(g2d, Direction.TOP, idX, idY);
							paintLight(g2d, Direction.BOTTOM, idX, idY);
						}
					}
				}
			}
			CarsInfo carsInfo = worldInfo.getCarsInfo();
			if (carsInfo != null)
			{
				for (Car car : carsInfo.getCars())
				{
					float cx, cy, cw, ch;
					cx = (car.getX() * 10) * scale + offsetX;
					cy = (car.getY() * 10) * scale + offsetY;
					cw = 10 * scale;
					ch = 10 * scale;
					g2d.setColor(car.getColor());
					g2d.fill(new Ellipse2D.Float(cx, cy, cw, ch));
				}
			}
		}
	}

	public void update()
	{
		repaint();
	}

	public void updateUnknown(Object worldInfoElement)
	{
		worldInfo.put(worldInfoElement);
		repaint();
	}

	public void update(WorldInfo mockup)
	{
		worldInfo = mockup;
		InterfaceFrame.writeToConsole("# Update makiety");
		repaint();
	}

}
