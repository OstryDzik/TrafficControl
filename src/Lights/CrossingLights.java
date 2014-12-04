package Lights;

public class CrossingLights {

	private final static int DEFAULT_TICK_COUNT = 12;

    public enum State{
		GREEN, 	// poziomo zielone
        YELLOW,
		RED,	// poziomo czerwone
	}
	
	private State horizontalState=State.GREEN;
	
	// Natezenie ruchu
	private int horizontalTrafficDensity=0;
	private int verticalTrafficDensity=0;

	// Czas trwania swiatel
	// w "kwantach" czasu
	private int tickCount = DEFAULT_TICK_COUNT;
	private int unchangedSince = 0;
	
	public CrossingLights() {
		super();
	}
	
	// Interfejs dostepu do stanu swiatel
	public State getVerticalState(){
		return (horizontalState == State.GREEN) ? State.RED : State.GREEN;
	}
	public State getHorizontalState(){
		return horizontalState;
	}
	
	// Ustawia dane o gestosci ruchu w obu kierunkach
	public void setTrafficDensity(int vertical, int horizontal){
		this.verticalTrafficDensity = vertical;
		this.horizontalTrafficDensity = horizontal;
	}
	
	/**
	 * Trigger dla kolejnego kwantu czasu na swiatlach
	 * @return 	true, jesli stan swiatel zostal zmieniony
	 * 			false, wpp
	 */
	public boolean tick()
	{
		
		unchangedSince++;

		//if(unchangedSince>=4)
		//{
		
			// ewentualna interwencja automatu
			if(horizontalState == State.GREEN){
				if(verticalTrafficDensity > horizontalTrafficDensity+3){
					change(); return true;
				}
			} else {
				if(horizontalTrafficDensity > verticalTrafficDensity+3){
					change(); return true;
				}
			}

		//}

        if(unchangedSince == tickCount)
			{change(); return true;}
        return false;


	}


    /**
	 * Zmienia swiatla na przeciwne
	 */
	public void change(){
		if(horizontalState == State.GREEN){
			horizontalState = State.RED;
		} else {
			horizontalState = State.GREEN;
		}
		unchangedSince = 0;
	}
	

	/**
	 * Resetuje swiatla, tj wszystkie ustawienia gestosci ruchu,
	 * pozostalych tickow etc, kolor pozostaje bez zmian
	 */
	public void reset() {
		
	}
	
	
	
}
