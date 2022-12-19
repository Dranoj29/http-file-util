package com.example.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.HttpResult;
import com.example.demo.object.EmailRequest;
import com.example.demo.service.emailService;

@RestController
public class Controller {

	@Autowired
	emailService service;
	
	@PostMapping(value ="/sendEmail", consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<HttpResult> emailSend(@RequestPart MultipartFile part,
			@RequestPart String email, @RequestPart String subject, @RequestPart String body) throws IOException{
		EmailRequest emailRequest = new EmailRequest();
		emailRequest.setEmail(email);
		emailRequest.setSubject(subject);
		emailRequest.setBody(body);
		HttpResult result = service.emailSend(part, emailRequest);
		
		if(result.getStatusCode()==200)
			return ResponseEntity.ok(result);
		
		return ResponseEntity.status(500).body(result);
	}
}
