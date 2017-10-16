package com.wiotp.gatewaytest;

import java.util.Properties;
import com.ibm.iotf.client.gateway.GatewayClient;

public class GatewaySubscriber {

	public static void main(String[] args) throws Exception{
Properties props = new Properties();		
		
		props.setProperty("Organization-ID", "w6dwxh");
		props.setProperty("Gateway-Type", "7a84b60a-4e7e-4251-84b0-8b6004bf1d12");
		props.setProperty("Gateway-ID", "d1bd9f7d-4e74-48e0-9fe4-a8f2d723c12f");
		props.setProperty("Authentication-Token", "o6Z9?2yjthn5BlcKA(");
		
		props.setProperty("Authentication-Method", "token");
		props.setProperty("WebSocket", "false");
		props.setProperty("Secure", "false");

				
		GatewayClient myClient = null;
		try {
			//Instantiate and connect to IBM Watson IoT Platform
			myClient = new GatewayClient(props);
			myClient.connect();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		String deviceType =props.getProperty("Device-Type");
		String deviceId = props.getProperty("Device-ID");
		new GatewaySubscriber().addCommandCallback(myClient, deviceType, deviceId);
	}
	
	private void addCommandCallback(GatewayClient gwClient, String deviceType,String  deviceId) {
		GatewayCommandCallback callback = new GatewayCommandCallback(gwClient);
		gwClient.setGatewayCallback(callback);
		gwClient.subscribeToDeviceCommands(deviceType, deviceId);
		
		Thread t = new Thread(callback);
		t.start();
		
	}

}


