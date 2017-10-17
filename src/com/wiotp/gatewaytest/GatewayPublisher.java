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
		props.setProperty("Gateway-Type", "9f360cd6-23ee-4015-9e3b-37309f34d853");
		props.setProperty("Gateway-ID", "48dd6553-05fd-4bcb-8720-764095cb264a");
		props.setProperty("Authentication-Token", "I?fuMoPLIPs1QyX4gR");
		
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
				location.addProperty("lat", 53.606314);
				location.addProperty("lon", -4.918212);
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
