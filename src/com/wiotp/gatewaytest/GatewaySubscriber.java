package com.wiotp.gatewaytest;

import java.util.Properties;
import com.ibm.iotf.client.gateway.GatewayClient;

public class GatewaySubscriber {

	public static void main(String[] args) throws Exception{
Properties props = new Properties();		
		
		props.setProperty("Organization-ID", "w6dwxh");
		props.setProperty("Gateway-Type", "cf6e5d3e-fbe3-4ec4-9e1c-e75c2ebddf9a");
		props.setProperty("Gateway-ID", "7bae909a-86e0-4c1d-b3a6-e0386a1cdb37");
		props.setProperty("Authentication-Token", "mycfIcD!_cg8(Dz&Ka");
		
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


