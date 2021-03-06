package Simulation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import Model.Car;
import Model.Direction;
import Model.LightsInfo;

class WorldMap
{
    private final int SIZE = 46;
    private final int NR_CROSSING = 9;
    private ArrayList<Car> cars;
    private Car carTable[][];
    private Crossing crossingTable[];
    private int intensityTable[][];
    private ArrayList<Point> startPoints;
    
    WorldMap()
    {
        carTable = new Car[SIZE][SIZE];
        crossingTable = new Crossing[NR_CROSSING];
        cars = new ArrayList<Car>();
        crossingTable[0] = new Crossing(10, 11, 10, 11);
        crossingTable[1] = new Crossing(22, 23, 10, 11);
        crossingTable[2] = new Crossing(34, 35, 10, 11);
        crossingTable[3] = new Crossing(10, 11, 22, 23);
        crossingTable[4] = new Crossing(22, 23, 22, 23);
        crossingTable[5] = new Crossing(34, 35, 22, 23);
        crossingTable[6] = new Crossing(10, 11, 34, 35);
        crossingTable[7] = new Crossing(22, 23, 34, 35);
        crossingTable[8] = new Crossing(34, 35, 34, 35);
        
        startPoints = new ArrayList<>();
        startPoints.add(new Point(0,11));
        startPoints.add(new Point( 0,23 ));
        startPoints.add(new Point( 0,35 ));
        startPoints.add(new Point( 45,10 ));
        startPoints.add(new Point( 45,22 ));
        startPoints.add(new Point( 45,34 ));
        startPoints.add(new Point( 10,0 ));
        startPoints.add(new Point( 22,0 ));
        startPoints.add(new Point( 34,0 ));
        startPoints.add(new Point( 11,45 ));
        startPoints.add(new Point( 23,45 ));
        startPoints.add(new Point( 35,45 ));
        
        intensityTable = new int[9][2];
        
        
        
    }
    
    void addCar(int x, int y)
    {
        carTable[x][y] = new Car(x, y);
        cars.add(carTable[x][y]);
    }
    
    void addRandomCars(int min, int max)
    {
        int howMany = (min + max) / 2;
        Collections.shuffle(startPoints);
        
        for (int i = 0; i < howMany; i++)
        {
            Point tmpPoint = startPoints.get(i);
            if(carTable[tmpPoint.x][tmpPoint.y] == null)
                addCar(tmpPoint.x, tmpPoint.y);
        }
    }
    
    void print()
    {
        for (int i = 0; i < 46; i++)
        {
            System.out.print(i);
            for (int j = 0; j < 46; j++)
            {
                if(carTable[j][i] != null)
                    System.out.print("X");
                else
                    System.out.print(".");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    void setLights(LightsInfo lightsInfo)
    {
        for (int i = 0; i < crossingTable.length; i++)
        {
            crossingTable[i].setLightsState(lightsInfo.getStates()[i]);
        }
    }
    
        void nextMove()
        {
            clearIntensity();
            ArrayList<Point> forbiddenList = new ArrayList<Point>();
            for(int i = cars.size() - 1; i >= 0; i--)
            {
                   Car car = cars.get(i);
                   Point p = getNextPoint(car.getX(), car.getY(), car.getDirection());
                   
                   if(isOutOfTable(p))
                   {
                       carTable[car.getX()][car.getY()] = null;
                       cars.remove(car);
                       continue;
                   }
                   
                   if(isPointfree(p) == false)
                   {
                       continue;
                   }
                   
                   if(isCrossing(p))
                   {
                       if (car.isDirectionChosen() == false)
                       {
                           car.setDirectionChosen(true);
                           car.setNewDirection();
                       }
                       
                       if(isRedOrOrangeLight(p, car.getDirection()))
                       {
                           //jesli jest bezposrednio przed skrzyzowaniem - nie wjezdza
                           if (isCrossing(new Point(car.getX(), car.getY())) == false)
                           {
                               continue;
                           } 
                           else // jesli juz jest na skrzyzowaniu to wyjedz bez wzgledu na swiatla
                           {
                           }
                       } 
                       else //zielone swiatlo
                       {
//                         jesli jest przed skrzyzowaniem
                           if (isCrossing(new Point(car.getX(), car.getY()))==false)
                           {
                               if (getCarCount(p) > 1)
                               {
                                   //skrzyzowanie jest zajete
                                   continue;
                               }
                               if(canExitCrossing(new Point(p),car.getDirection(),car.getDirection())==false)
                               {
                                   //nie wjezdza bo nie ma jak wyjechac
                                   continue;
                               }
                           } 
                       }
                   } 
                   else// p nie jest skrzyzowaniem
                   {
                       car.setDirectionChosen(false);
                   }
                   
                 //lista punktow z ktorych ruszyly sie samochody w tym wywolaniu
                   if (forbiddenList.contains(p))
                   {
//                     System.out.println("z listy zabronionych - nie wolno przesunac");
                   } else
                   {
                       // mozna przesunac samochod
                       forbiddenList.add(new Point(car.getX(), car.getY()));
                       carTable[car.getX()][car.getY()] = null;
                       carTable[p.x][p.y] = car;
                       car.setPoint(p);

                       car.decrementCountTime();
//                     System.out.println("przesunieto samochod");
//                     System.out.println("samochod obecnie" + car.getX() + ","
//                             + car.getY());
                   }

                   // po dekrementacji do zera zmiana kierunku
                   if (car.getCountTime() == 0)
                   {
                       car.changeDirection();
//                     System.out.println("zmiana kierunku samochodu");
                   }
            }
            updateIntensity();
        }
        
        ArrayList<Car> getCars()
        {
            return cars;
        }
        
        int[][] getIntensityTable()
        {
            return intensityTable;
        }
        
        private Point getNextPoint(int x, int y, Direction direction)
        {
            if (direction == Direction.UP)
            {
                return new Point(x, y - 1);
            } else if (direction == Direction.DOWN)
            {
                return new Point(x, y + 1);
            } else if (direction == Direction.LEFT)
            {
                return new Point(x - 1, y);
            } else if (direction == Direction.RIGHT)
            {
                return new Point(x + 1, y);
            } else
                return null;
        }
        
        private boolean isOutOfTable(Point point)
        {
            if (point.x < 0 || point.x >= SIZE || point.y < 0 || point.y >= SIZE)
                return true;
            return false;
        }
        
        private boolean isPointfree(Point p)
        {
            if(carTable[p.x][p.y] == null)
                return true;
            return false;
        }
        
        private boolean isCrossing(Point p)
        {
            for (Crossing crossing : crossingTable)
            {
                if (crossing.thisCrossing(p))
                {
                    return true;
                }
            }
            return false;
        }
        
        private boolean isRedOrOrangeLight(Point p, Direction direction)
        {
            for (Crossing crossing : crossingTable)
            {
                if (crossing.thisCrossing(p))
                {
                    return crossing.isRedOrOrange(direction);
                }
            }
            //nie powinno tu nigdy dojsc
            return true;
        }
        
        private int getCarCount(Point p)
        {
            for (Crossing crossing : crossingTable)
            {
                if (crossing.thisCrossing(p))
                {
                    return crossing.getCarCount(carTable);
                }
            }
            return 0;
        }
        
        private boolean canExitCrossing(Point p, Direction d, Direction newd)
        {
            //no kurde inaczej sie nie dalo
            Point nextP = new Point();
            if(d == Direction.UP)
            {
                if(newd == Direction.UP)
                {
                    p.y -= 2;
                    nextP = new Point(p);
                    nextP.y-=1;
                }
                else if(newd == Direction.DOWN)
                {
                    p.x-=1;
                    p.y+=1;
                    nextP = new Point(p);
                    nextP.y+=1;
                }
                else if( newd == Direction.LEFT)
                {
                    p.x-=2;
                    p.y-=1;
                    nextP = new Point(p);
                    nextP.x-=1;
                }
                else if(newd== Direction.RIGHT)
                {
                    p.x+=1;
                    nextP = new Point(p);
                    nextP.x+=1;
                }
            }
            else if(d == Direction.DOWN)
            {
                if(newd == Direction.UP)
                {
                    p.x+=1;
                    p.y-=1;
                    nextP = new Point(p);
                    nextP.y-=1;
                }
                else if(newd == Direction.DOWN)
                {
                    p.y+=2;
                    nextP = new Point(p);
                    nextP.y+=1;
                }
                else if( newd == Direction.LEFT)
                {
                    p.x-=1;
                    nextP = new Point(p);
                    nextP.x-=1;
                }
                else if(newd== Direction.RIGHT)
                {
                    p.x+=2;
                    p.y+=1;
                    nextP = new Point(p);
                    nextP.x+=1;
                }
            }
            else if( d == Direction.LEFT)
            {
                if(newd == Direction.UP)
                {
                    p.y-=1;
                    nextP = new Point(p);
                    nextP.y-=1;
                }
                else if(newd == Direction.DOWN)
                {
                    p.y+=2;
                    p.x-=1;
                    nextP = new Point(p);
                    nextP.y+=1;
                }
                else if( newd == Direction.LEFT)
                {
                    p.x-=2;
                    nextP = new Point(p);
                    nextP.x-=1;
                }
                else if(newd== Direction.RIGHT)
                {
                    p.x+=1;
                    p.y+=1;
                    nextP = new Point(p);
                    nextP.x+=1;
                }
            }
            else if(d== Direction.RIGHT)
            {
                if(newd == Direction.UP)
                {
                    p.x+=1;
                    p.y-=2;
                    nextP = new Point(p);
                    nextP.y-=1;
                }
                else if(newd == Direction.DOWN)
                {
                    p.y+=1;
                    nextP = new Point(p);
                    nextP.y+=1;
                }
                else if( newd == Direction.LEFT)
                {
                    p.x-=1;
                    p.y-=1;
                    nextP = new Point(p);
                    nextP.x-=1;
                }
                else if(newd== Direction.RIGHT)
                {
                    p.x+=2;
                    nextP = new Point(p);
                    nextP.x+=1;
                }
            }
            if(isPointfree(p)==false)
            {
                if(isPointfree(nextP)==false)
                {
                    return false;
                }
                return true;
            }
            return true;
        }
        
        private void calculateCrossing(Point point, Direction direction)
        {
            if(isCrossing(point))
                return;
            
            do
            {
                if(direction == Direction.UP)
                {
                    point.y--;
                }
                else if( direction == Direction.DOWN)
                {
                    point.y++;
                }
                else if( direction == Direction.LEFT)
                {
                    point.x--;
                }
                else if( direction == Direction.RIGHT)
                {
                    point.x++;
                }
            }
            while( isCrossing(point) == false && isOutOfTable(point) == false );
            
            if(isOutOfTable(point))
            {
                return;
            }
            for (int i = 0; i<crossingTable.length ; i++ )
            {
                if(crossingTable[i].thisCrossing(point))
                {
                    if(direction == Direction.UP || direction == Direction.DOWN)
                    {
                        intensityTable[i][1]++;
                        break;
                    }
                    else
                    {
                        intensityTable[i][0]++;
                    }
                }
            }
        }
        
        private void clearIntensity()
        {
            for(int i =0 ; i<intensityTable.length; i++)
            {
                intensityTable[i][0] = 0;
                intensityTable[i][1] = 0;
            }
        }
        
        private void updateIntensity()
        {
            for(Car car : cars)
            {
                calculateCrossing(new Point(car.getX(),car.getY()),car.getDirection());
            }
        }
}
