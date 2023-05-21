package bsa.dummyclient;

import requestobjects.user.CreateUserRequest;
import requestobjects.RequestObject;
import requestobjects.ResponseObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

public class DummyClient {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", 2910);

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        CreateUserRequest payload = new CreateUserRequest("Username", "P4zzw0rc!");
        RequestObject request = new RequestObject("user/create", payload);
        out.writeObject(request);

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ResponseObject response = (ResponseObject) in.readObject();
        UUID id = (UUID)response.getPayload();

        System.out.println(id);
    }
}
