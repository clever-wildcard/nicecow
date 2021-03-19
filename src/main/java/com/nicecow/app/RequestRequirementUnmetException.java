package com.nicecow.app;

import org.json.JSONObject;

public class RequestRequirementUnmetException extends RuntimeException{
    RequestRequirementUnmetException(JSONObject jsonObject) { super(String.valueOf(jsonObject)); }
}
