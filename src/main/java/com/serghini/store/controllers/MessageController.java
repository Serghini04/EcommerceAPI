package com.serghini.store.controllers;

import com.serghini.store.entities.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@Tag(name="Message", description="Operations related to messages")
public class MessageController {
	@RequestMapping("/hello")
	@Operation(summary="Returns a greeting message.")
	public Message hello() {
		return new Message("Hello World!");
	}
}
