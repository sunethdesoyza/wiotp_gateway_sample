package com.wiotp.gatewaytest;

import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

import com.google.gson.JsonObject;
import com.ibm.iotf.client.api.APIClient;
import com.ibm.iotf.client.gateway.GatewayClient;

public class GatewayPublisher {

	public static void main(String[] args) throws Exception{
		
		/**
		  * Load device properties
		  */
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
				location.addProperty("alt", 19);
				location.addProperty("spd", 13);
				location.addProperty("hdg", 324);
				location.addProperty("nSats", 3);
				location.addProperty("timestamp", Calendar.getInstance().getTimeInMillis());
				st.add("location", location);
				
				JsonObject latest = new JsonObject();
				JsonObject sensonrs = new JsonObject();
				sensonrs.addProperty("247", 56);
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