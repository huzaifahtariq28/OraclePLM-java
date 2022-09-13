package com.gosaas.utils;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class GetJson {
	public static JSONObject getParamsFromRequest(HttpServletRequest httpRequest) throws Exception {
		String paramString = httpRequest.getParameter("params");
		if (paramString == null) {
			throw new Exception("Params not passed to servlet.");
		}
		return (JSONObject) JSONValue.parse(paramString);
	}
}
