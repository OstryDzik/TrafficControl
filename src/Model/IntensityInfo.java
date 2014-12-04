package Model;

public class IntensityInfo
{

    public enum Direction{
		HORIZONTAL, VERTICAL;
	};
	
	private int tablica[][];

	/**
	 * @param tablica
	 */
	public IntensityInfo(int[][] tablica)
	{
		this.tablica = tablica;
	}

	/**
	 * @return the tablica
	 */
	public int[][] getTablica()
	{
		return tablica;
	}
	
	/**
	 * Zwraca intensywnosc z danego skrzyzowania, w danym kierunku
	 * @return
	 */
	public int getIntensity(int crossingID, Direction direction){
		if(direction == Direction.HORIZONTAL){
			return tablica[crossingID][0];
		} else {
			// Direction.VERTICAL
			return tablica[crossingID][1];
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(int i=0; i<tablica.length; i++){
			builder.append("C"+Integer.toString(i) + ": H " + Integer.toString(tablica[i][0]) + " V " + Integer.toString(tablica[i][1]) + " # ");
		}
		return builder.toString();
	}
	
	
	
	
}