package Simulation;

import java.awt.Point;
import java.util.ArrayList;

import Model.Car;
import Model.Direction;

class WorldMap
{
    private final int SIZE = 46;
    private final int NR_CROSSING = 9;
    private int intensityTable[][] = new int[NR_CROSSING][2];
    private ArrayList<Car> cars;
    private Car carTable[][];
    private Crossing crossingTable[];
    
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
                           tickTraffic(p, car.getDirection());
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
        }
        
        private void clearIntensity()
        {
            for(int i =0 ; i<intensityTable.length; i++)
            {
                intensityTable[i][0] = 0;
                intensityTable[i][1] = 0;
            }
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
        
        private void tickTraffic(Point p, Direction d)
        {
            for (Crossing crossing : crossingTable)
            {
                if (crossing.thisCrossing(p))
                {
                    crossing.tickTraffic(d);
                }
            }
        }
        
}