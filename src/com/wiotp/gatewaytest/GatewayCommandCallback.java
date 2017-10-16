

package com.wiotp.gatewaytest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.ibm.iotf.client.gateway.Command;
import com.ibm.iotf.client.gateway.GatewayCallback;
import com.ibm.iotf.client.gateway.GatewayClient;
import com.ibm.iotf.client.gateway.Notification;


public class GatewayCommandCallback implements GatewayCallback, Runnable {

	private GatewayClient gateway = null;
	
	// A queue to hold & process the commands
	private BlockingQueue<Command> queue = new LinkedBlockingQueue<Command>();

	public GatewayCommandCallback(GatewayClient gateway) {
		this.gateway = gateway;
	}

	@Override
	public void processCommand(Command cmd) {
		try {
			queue.put(cmd);
		} catch (InterruptedException e) {
		}			
	}

	@Override
	public void run() {
		while(true) {
			Command cmd = null;
			try {
				cmd = queue.take();
				// check if this command is for the gateway
				if(cmd.getDeviceId().equals(this.gateway.getGWDeviceId()) &&
						cmd.getDeviceType().equals(this.gateway.getGWDeviceType())) {
					System.out.println("-->(GW) Got command for this gateway:: "+cmd);
					return;
				} else {
					System.out.println("-->(DE) Got command for the device:: "+cmd.getDeviceId());
				}
			} catch (InterruptedException e1) {
					// Ignore the Interuppted exception, retry
					continue;
			}
				
		}
	}

	@Override
	public void processNotification(Notification notification) {
		// TODO Auto-generated method stub
		
	}
}