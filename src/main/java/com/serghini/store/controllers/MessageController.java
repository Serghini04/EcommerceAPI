package com.serghini.store.controllers;

import com.serghini.store.entities.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
	@RequestMapping("/hello")
	public Message MessageController() {
		return new Message("Hello World!");
	}
}
