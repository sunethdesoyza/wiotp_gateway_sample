package com.wiotp.gatewaytest;

import java.util.Calendar;
import java.util.Properties;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ibm.iotf.client.gateway.GatewayClient;

public class GatewayPublisher {

	public static void main(String[] args) throws Exception{
		
		/**
		  * Load device properties
		  */
		Properties props = new Properties();		
		
		props.setProperty("Organization-ID", "4obg3g");
		props.setProperty("Gateway-Type", "e8806385-afe1-4863-ab9e-08a227b226a6");
		props.setProperty("Gateway-ID", "f2af1aa5-61e4-4e5d-aa68-666bba162bed");
		props.setProperty("Authentication-Token", "IvU-wQINo09TU!Gbou");
		
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
				location.addProperty("lon", 4.918212);
				location.addProperty("alt", 19);
				location.addProperty("spd", 13);
				location.addProperty("hdg", 324);
				location.addProperty("nSats", 3);
				location.addProperty("timestamp", Calendar.getInstance().getTimeInMillis());
				st.add("location", location);
				
				JsonObject latest = new JsonObject();
				JsonObject sensonrs = new JsonObject();
				sensonrs.addProperty("247", 56);
				sensonrs.addProperty("248", 56);
				latest.add("sensors", sensonrs);
				latest.addProperty("timestamp", Calendar.getInstance().getTimeInMillis());
				st.add("latest", latest);
				
	
				JsonObject alert1 = new JsonObject();
				alert1.addProperty("primaryCode", "abc");
				alert1.addProperty("alertStatus", "ACTIVE");
				alert1.addProperty("statusChangeTimestamp", Calendar.getInstance().getTimeInMillis());
				JsonObject alert2 = new JsonObject();
				alert2.addProperty("primaryCode", "abc");
				alert2.addProperty("alertStatus", "ACTIVE");
				alert2.addProperty("statusChangeTimestamp", Calendar.getInstance().getTimeInMillis());
				JsonArray alertArray = new JsonArray();
				alertArray.add(alert1);
				alertArray.add(alert2);
				st.add("alerts", alertArray);
				
//				JsonObject software = new JsonObject();
//				software.addProperty("softwareId", "abc");
//				st.add("software", software);
				
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
