package com.example.demo.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.HttpResult;
import com.example.demo.object.EmailRequest;
import com.example.demo.util.HttpFileUtil;

@Service
public class emailService {

	@Autowired
	HttpFileUtil httpUtil;
	
	public HttpResult emailSend(MultipartFile part, EmailRequest emailRequest) throws IOException {
		
		Map<String, Object> emailParam = new HashMap<>();
		emailParam.put("email", emailRequest.getEmail());
		emailParam.put("subject", emailRequest.getSubject());
		emailParam.put("body", emailRequest.getBody());
		
		return httpUtil.post("http://localhost:8080/email/sendWithFile", part, emailParam);
	}
	
	
}
