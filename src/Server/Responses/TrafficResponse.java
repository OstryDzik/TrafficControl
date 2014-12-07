package Server.Responses;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Model.CarsInfo;

/**
 * Created by ThinkPad on 2014-11-23.
 */
public class TrafficResponse extends AbstractResponse {
    public TrafficResponse(CarsInfo carsInfo) {
        this.carsInfo = carsInfo;
    }

    public CarsInfo getCarsInfo()
	{
		return carsInfo;
	}

	private CarsInfo carsInfo;
}
