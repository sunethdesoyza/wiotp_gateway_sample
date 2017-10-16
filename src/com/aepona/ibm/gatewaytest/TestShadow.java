package com.aepona.ibm.gatewaytest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class TestShadow {
	public static void main(String[] args) throws Exception{
		JsonObject shadow= new JsonObject(), state= new JsonObject(), desiredState= new JsonObject(), 
				reportedState= new JsonObject(),
				 	metadata= new JsonObject(), desiredMetadata= new JsonObject(), reportedMetadata = new JsonObject(), 
				 	version= new JsonObject(),clientToken= new JsonObject(),timestamp= new JsonObject();
		
		state.add("desired", desiredState);
		state.add("reported", reportedState);
		shadow.add("state", state);
		metadata.add("desired", desiredMetadata);
		metadata.add("reported", reportedMetadata);
		shadow.add("metadata", metadata);
		shadow.addProperty("version", "");
		shadow.addProperty("clientToken", "");
		shadow.addProperty("timestamp", "");
		
		System.out.println("=> " +  shadow.toString());
		
		
		JsonObject o= new JsonObject();
		
		
//		{
//		    "state" : {
//		        "desired" : {
//		          "color" : "RED",
//		          "sequence" : [ "RED", "GREEN", "BLUE" ]
//		        },
//		        "reported" : {
//		          "color" : "GREEN"
//		        }
//		    },
//		    "metadata" : {
//		        "desired" : {
//		            "color" : {
//		                "timestamp" : 12345
//		            },
//		            "sequence" : {
//		                "timestamp" : 12345
//		            }
//		        },
//		        "reported" : {
//		            "color" : {
//		                "timestamp" : 12345
//		            }
//		        }
//		    },
//		    "version" : 10,
//		    "clientToken" : "UniqueClientToken",
//		    "timestamp": 123456789
//		}
	}
}
