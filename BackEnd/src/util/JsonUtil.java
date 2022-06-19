package util;

import javax.json.*;

public class JsonUtil {
    static JsonObjectBuilder responseInfo;

    public static JsonObject generateResponse(int status, String message, String data) {
        responseInfo = Json.createObjectBuilder();
        responseInfo.add("status", status);
        responseInfo.add("message", message);
        responseInfo.add("data", data);
        return responseInfo.build();
    }

    public static JsonObject generateResponse(int status, String message, JsonObject data) {
        responseInfo = Json.createObjectBuilder();
        responseInfo.add("status", status);
        responseInfo.add("message", message);
        responseInfo.add("data", data);
        return responseInfo.build();
    }

    public static JsonObject generateResponse(int status, String message, JsonArray data) {
        responseInfo = Json.createObjectBuilder();
        responseInfo.add("status", status);
        responseInfo.add("message", message);
        responseInfo.add("data", data);
        return responseInfo.build();
    }
}
