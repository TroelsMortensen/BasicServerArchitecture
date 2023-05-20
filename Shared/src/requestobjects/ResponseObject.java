package requestobjects;

import java.io.Serializable;

public class ResponseObject implements Serializable {
    private final String status;
    private final Object payload;

    public ResponseObject(String status, Object response) {
        this.status = status;
        this.payload = response;
    }

    public Object getPayload() {
        return payload;
    }
}
