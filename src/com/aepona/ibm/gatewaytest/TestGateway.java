package com.aepona.ibm.gatewaytest;

import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

import com.google.gson.JsonObject;
import com.ibm.iotf.client.api.APIClient;
import com.ibm.iotf.client.gateway.GatewayClient;

public class TestGateway {

	public static void main(String[] args) throws Exception{
		
		/**
		  * Load device properties
		  */
		Properties props = new Properties();
//		try {
//			props.load(GatewayEventPublishWithCounter.class.getResourceAsStream(PROPERTIES_FILE_NAME));
//		} catch (IOException e1) {
//			System.err.println("Not able to read the properties file, exiting..");
//			System.exit(-1);
//		}		
		
		
		props.setProperty("Organization-ID", "w6dwxh");
		props.setProperty("Gateway-Type", "1701aaba-fbce-4524-8d31-fb8400537c48");
		props.setProperty("Gateway-ID", "ec8ffc44-ea3b-406b-8038-d2832853b516");
		props.setProperty("Authentication-Token", "47q335b6A1n8ps8qdh");
//		props.setProperty("Gateway-Type", "49f54c75-d704-4fba-860b-6da4d5b34aaf");
//		props.setProperty("Gateway-ID", "e13c0608-258d-454e-a5b8-909f5a20598a");
//		props.setProperty("Authentication-Token", "Mn9iJ6SIXnHpNJjCTw");
		props.setProperty("Authentication-Method", "token");
		props.setProperty("WebSocket", "false");
		props.setProperty("Secure", "false");
		//props.setProperty("Registration-Mode", "Manual");
		//props.setProperty("API-Key", "a-b4fty6-uyyd5w3nj4");
		//props.setProperty("API-Token", "N5kc_HW)d(9!znZ+97");

				
		GatewayClient myClient = null;
		try {
			//Instantiate and connect to IBM Watson IoT Platform
			myClient = new GatewayClient(props);
			//myClient.
			myClient.connect();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		//SystemObject obj = new SystemObject();
		
		/**
		 * Publishes the process load event for every 1 second
		 */
		long counter = 0;
		boolean status = true;
		while(true) {
			try {
				
				//Generate a JSON object of the event to be published
				JsonObject st = new JsonObject();
				JsonObject location = new JsonObject();
				location.addProperty("lat", 54.606314);
				location.addProperty("lon", -5.918212);
				location.addProperty("alt", 10);
				location.addProperty("spd", 10);
				location.addProperty("hdg", 324);
				location.addProperty("nSats", 3);
				location.addProperty("timestamp", Calendar.getInstance().getTimeInMillis());
				st.add("location", location);
				
				JsonObject latest = new JsonObject();
				JsonObject sensonrs = new JsonObject();
				sensonrs.addProperty("247", 100);
				latest.add("sensors", sensonrs);
				latest.addProperty("timestamp", Calendar.getInstance().getTimeInMillis());
				st.add("latest", latest);

				JsonObject md = new JsonObject();
				md.addProperty("$lastUpdated", "2017-10-03T11:12:18.964114Z");
				JsonObject lu = new JsonObject();
				lu.addProperty("$lastUpdated", "2017-10-03T11:12:18.964114Z");
				JsonObject lc = new JsonObject();
				lc.add("lat", lu);
				lc.add("lon", lu);
				lc.add("alt", lu);
				lc.add("spd", lu);
				lc.add("hdg", lu);
				lc.add("nSats", lu);
				lc.add("timestamp", lu);
				lc.addProperty("$lastUpdated", "2017-10-03T11:12:18.964114Z");
				md.add("location", location);
				
				JsonObject ltd = new JsonObject();
				ltd.addProperty("$lastUpdated", "2017-10-03T11:12:18.964114Z");
				JsonObject sd = new JsonObject();
				sd.addProperty("$lastUpdated", "2017-10-03T11:12:18.964114Z");
				sd.add("1", lu);
				ltd.add("sensors", sd);
				ltd.add("timestamp", lu);
				md.add("latest", ltd);

				st.add("$metadata", md);

				status = myClient.publishGatewayEvent("status", st);
				System.out.println(st);
				Thread.sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(!status) {
				System.out.println("Failed to publish the event......");
				System.exit(-1);
			}
		}

	}

}
