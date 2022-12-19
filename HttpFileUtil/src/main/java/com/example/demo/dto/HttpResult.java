package com.example.demo.dto;

import java.util.Map;

public class HttpResult {
	 private int statusCode;
	    private Map<?, ?> body;
	    public HttpResult(int statusCode, Map<?, ?> body) {
	        this.statusCode = statusCode;
	        this.body = body;
	    }
	    public HttpResult() {
	    	
	    }
	    public int getStatusCode() {
	        return statusCode;
	    }
	    public void setStatusCode(int statusCode) {
	        this.statusCode = statusCode;
	    }
	    public Map<?, ?> getBody() {
	        return body;
	    }
	    public void setBody(Map<?, ?> body) {
	        this.body = body;
	    }
	    
	    @Override
	    public String toString() {
	        return "HTTP Response Status: " + statusCode + " HTTP Response Body: " + body;
	    }
	    
}
