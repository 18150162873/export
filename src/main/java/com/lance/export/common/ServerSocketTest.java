package com.lance.export.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

@SuppressWarnings("all")
public class ServerSocketTest {
	
	private ExecutorService executorService = null;
	
	private static int port= 8465;

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(port);
		while(true) {
			Socket client =  server.accept();
			InputStream in = client.getInputStream();
			BufferedReader bufe = new BufferedReader(new InputStreamReader(in));
			String info = "";
			while((info=bufe.readLine())!=null) {
				System.out.println(info);
			}
			client.close();
			in.close();
		}
	}
	
}

