package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecs {

    private RequestSpecs() {
        // Utility class
    }

    public static RequestSpecification jsonRequest() {
        return new RequestSpecBuilder()
                .setContentType("application/json")
                .setAccept("application/json")
                .build();
    }
}