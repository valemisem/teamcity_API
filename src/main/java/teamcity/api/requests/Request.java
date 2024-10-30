package teamcity.api.requests;

import io.restassured.specification.RequestSpecification;
import teamcity.api.enums.Endpoint;

public class Request {
    /**
     * Request – a class, which describes changing parameters of the request like specification, endpoint (relative URL, model (DTO))
     */
    protected final RequestSpecification spec;
    protected final Endpoint endpoint;

    public Request(RequestSpecification spec, Endpoint endpoint) {
        this.spec = spec;
        this.endpoint = endpoint;
    }
}