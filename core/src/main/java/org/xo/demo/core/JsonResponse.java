package org.xo.demo.core;

import com.alibaba.fastjson.JSON;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class JsonResponse {

    private int errCode;
    private String errMessage;
    private Object data;

    public static String success() {
        return success(new Object());
    }

    public static String success(Object data) {
        return response(0, "success", data);
    }

    public static String fail() {
        return response(1, "fail", new Object());
    }

    public static String response(int errCode, String errMessage, Object data) {
        JsonResponse response = JsonResponse.builder()
                .errCode(errCode)
                .errMessage(errMessage)
                .data(data)
                .build();
        return JSON.toJSONString(response);
    }
}
