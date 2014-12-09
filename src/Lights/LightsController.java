package Lights;

import Model.IntensityInfo;
import Model.LightsInfo;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.Observable;

import static Lights.CrossingLights.*;
import static Model.LightsInfo.*;

public class LightsController extends Observable {

	public enum Mode{
        MANUAL,		// manualne sterowanie swiatlami
		AUTOMATIC	// automatyczne sterowanie swiatlami
	};

	// Flaga - czy jest wlasnie prowadzona kontrola
	// automatyczna, czy tez manualna
	private Mode mode = Mode.AUTOMATIC;

	// Zarzadzane przez kontrolera swiatla
	private CrossingLights[] lights;
	
	// Informacje o natezeniu ruchu
	private IntensityInfo intensityInfo;

	/**
	 * Tworzy kontroler swiatel
	 */
	public LightsController(int lightsCount) {
		
		super();
		
		// Stworzenie swiatel do zarzadzania
		lights = new CrossingLights[lightsCount];
		for(int i=0; i<lightsCount; i++)
			lights[i] = new CrossingLights();
		
	}
	
	
	/**
	 * Ustawia tryb sterowania swiatlami
	 * Powiadamia obserwatorow
	 */
	public synchronized void setMode(Mode mode)
	{
		if(mode == Mode.MANUAL)
		{
			if(this.mode != Mode.MANUAL)
				setManualMode();
		}
		else
		{
			if(this.mode != Mode.AUTOMATIC)
				setAutomaticMode();
		}
		
		setChanged();
		notifyObservers();
		
		
	}
	
	public synchronized boolean isAutomatic(){
		return mode==Mode.AUTOMATIC;
	}
	
	
	private void setManualMode(){
		
		mode = Mode.MANUAL;
	
	}
	
	private void setAutomaticMode(){
		
		mode = Mode.AUTOMATIC;
		
		for(CrossingLights l: lights)
			l.reset();
		
	}

	
	/**
	 * Pobiera intensity z serwera
	 * Aktualizuje swiatla (jesli tryb automatyczny)
	 * Wysyla do serwera nowe swiatla
	 */
	public LightsInfo updateLights(IntensityInfo info){

		// Pobranie intensywnosci ruchu
		//System.out.println("GETINTENSITY...");

		if(info==null){
			System.out.println("NULL INTENSITY RECEIVED");
		} else {

			//System.out.println(info.toString());

			for(int i=0; i<lights.length; i++){
				int v = info.getIntensity(i, IntensityInfo.Direction.VERTICAL);
				int h = info.getIntensity(i, IntensityInfo.Direction.HORIZONTAL);
				lights[i].setTrafficDensity(v, h);
			}

			this.intensityInfo = info;
		}

		//System.out.println(" ...dostalem");

		if(isAutomatic()){
			for(CrossingLights l: lights){
				l.tick();
			}
			setChanged();
			notifyObservers();
		}

		// Wyslanie nowych swiatel
		LightsInfo lightsInfo = new LightsInfo();
		for(int i=0; i<lights.length; i++)
        {
            LightsState nextState = LightsState.HORIZONTALLY_GREEN;
            switch(lights[i].getHorizontalState()){
                case GREEN:
                    nextState = LightsState.HORIZONTALLY_GREEN;
                    break;
                case RED:
                    nextState = LightsState.HORIZONTALLY_RED;
                    break;
                case YELLOW:
                    nextState = LightsState.HORIZONTALLY_ORANGE;
                    break;
                case YELLOW_VERTICAL:
                    nextState = LightsState.VERTICALLY_ORANGE;
                    break;

            }
            lightsInfo.setState(i, nextState);
        }

        return lightsInfo;

		//System.out.println("  ...Potwierdzono");
		
	}
	
	

	/**
	 * Wywolywane dla recznej zmiany swiatel
	 * Powiadamia obserwatorow
	 * @param id
	 */
	public void changeLightsOnCrossing(int id) {
		lights[id].change();
		//updateLights();
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Zwraca stan swiatel na podanym skrzyzowaniu
	 * @param crossingID
	 * @return
	 */
	public LightsState getLightsState(int crossingID){
		return (lights[crossingID].getHorizontalState()== State.GREEN) ? LightsState.HORIZONTALLY_GREEN: LightsState.HORIZONTALLY_RED;
	}

	/**
	 * Zwraca info, czy dostepna jset informacja o gestosci ruchu
	 * @return
	 */
	public boolean hasIntensityInfo() {
		return intensityInfo != null;
	}
	
	/**
	 * Zwraca intensywnosc ruchu na danym skrzyzowaniu w dana strone
	 */
	public int getIntensity(int crossingID, IntensityInfo.Direction direction) {
		return intensityInfo.getIntensity(crossingID, direction);
	}

	/**
	 * Zwraca aktualny tryb sterowania
	 * @return
	 */
	public Mode getMode() {
		return this.mode;
	}

	

//	private class ServerCommunicationThread implements Runnable {
//
//		static final private int TICK_TIME = 250;
//
//		private volatile boolean alive = true;
//
//		public ServerCommunicationThread(){
//			super();
//		}
//
//		public synchronized void markToStop(){
//			alive = false;
//		}
//
//		private synchronized boolean isAlive(){
//			return alive;
//		}
//
//
//		@Override
//		public void run() {
//
//			System.out.println("Start komunikacji");
//
//			while(isAlive()){
//				LightsController.this.updateLights();
//				try {
//					Thread.sleep(TICK_TIME);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//
//			System.out.println("Stop komunikacji");
//
//		}
//
//
//	}

	
	
	
	
	
}