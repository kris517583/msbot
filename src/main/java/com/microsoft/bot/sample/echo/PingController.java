package com.microsoft.bot.sample.echo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

	  @GetMapping("/ping")
	  public String all() {
	    return "Running APP ID - " + System.getenv().get("MicrosoftAppID") ;
	  }
}
