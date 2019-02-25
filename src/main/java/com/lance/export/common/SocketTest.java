package com.lance.export.common;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
@SuppressWarnings("all")
public class SocketTest {
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		socketTest();
	}
	
	public static void socketTest() throws UnknownHostException, IOException {
		Socket socket =  new Socket("127.0.0.1", 8465);
		OutputStream out = socket.getOutputStream();
		PrintWriter pw =  new PrintWriter(out);
		pw.write("lance_test");
		pw.close();
		out.close();
		out.flush();
	}
}
