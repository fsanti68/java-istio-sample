package com.dsf.istioms;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}

@RestController()
@RequestMapping("/genesio/v1")
class EchoController {
	private static final Logger logger = LoggerFactory.getLogger(EchoController.class);

	@Value("${version:1.0}")
	public String version;

	@GetMapping("/echo/{text}")
	public Map<String, String> echo(@Value("${message}") String echoTemplate, @PathVariable String text)
			throws UnknownHostException {

		logger.info("Received echo request for: {}", text);
		Map<String, String> response = new HashMap<>();

		String hostname = InetAddress.getLocalHost().getHostName();
		String message = echoTemplate.replaceAll("\\$name", text).replaceAll("\\$hostname", hostname)
				.replaceAll("\\$version", version);

		response.put("message", message);
		response.put("version", version);
		response.put("hostname", hostname);

		return response;
	}
}