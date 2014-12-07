package Server.Responses;

import Model.LightsInfo;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by ThinkPad on 2014-11-23.
 */
public class LightsResponse extends AbstractResponse {
    public LightsResponse(LightsInfo lightsInfo) {
        this.lightsInfo = lightsInfo;
    }


    public LightsInfo getLightsInfo()
	{
		return lightsInfo;
	}

	private LightsInfo lightsInfo;
}
