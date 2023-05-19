package bsa.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    private RequestHandler requestHandler;

    public SocketServer(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(2910);
        while(true){
            Socket client = serverSocket.accept();
            RequestReceiver handler = new RequestReceiver(client, requestHandler);
            Thread t = new Thread(handler);
            t.start();
        }
    }
}
