package bsa.network;

import requestobjects.RequestObject;
import requestobjects.ResponseObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestReceiver implements Runnable {
    private final Socket client;
    private final RequestHandler requestHandler;

    public RequestReceiver(Socket client, RequestHandler requestHandler) {
        this.client = client;
        this.requestHandler = requestHandler;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream inFromClient = new ObjectInputStream(client.getInputStream());
            RequestObject requestObject = (RequestObject) inFromClient.readObject();

            String status;
            Object responsePayLoad;

            try {
                responsePayLoad = requestHandler.handle(requestObject);
                status = "success";
            } catch (Exception e) {
                responsePayLoad = e.getMessage();
                status = "error";
            }

            ResponseObject responseObject = new ResponseObject(status, responsePayLoad);

            ObjectOutputStream outToClient = new ObjectOutputStream(client.getOutputStream());

            outToClient.writeObject(responseObject);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
