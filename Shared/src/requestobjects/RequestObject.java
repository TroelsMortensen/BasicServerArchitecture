package requestobjects;

import java.io.Serializable;

public class RequestObject implements Serializable {

    private final String requestType;
    private final Object payload;

    public RequestObject(String requestType, Object payload) {

        this.requestType = requestType;
        this.payload = payload;
    }

    public String getRequestType() {
        return requestType;
    }

    public Object getPayload() {
        return payload;
    }
}
