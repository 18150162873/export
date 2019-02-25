package com.lance.export.common;

@SuppressWarnings("all")
public class Server implements Runnable {
	
	private SocketTest socket;
	
	public Server(SocketTest socket){
		this.socket = socket;
		
	}
	
	@Override
	public void run() {
		
	}

}
