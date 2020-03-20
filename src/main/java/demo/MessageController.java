package demo;

import java.util.stream.*;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
	@RequestMapping(path = "/hello", 
					method = RequestMethod.POST,
					produces = MediaType.APPLICATION_JSON_VALUE,
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public MessageBoundary hello(@RequestBody MessageBoundary msg) {
		return new MessageBoundary("Hello from the other boundary. Recieved message: " + msg.getMessage());
	}
	
	
	@RequestMapping (path = "/messages/{count}",
					 method = RequestMethod.GET,
					 produces = MediaType.APPLICATION_JSON_VALUE)
	public MessageBoundary[] getManyMessages (@PathVariable("count") int count) {
		return IntStream.range(0, count) //stream of integers
				.mapToObj(i -> "message #" + i)  //stream of strings
				.map(msg -> new MessageBoundary(msg)) // stream of message boundary
				.collect(Collectors.toList()) // list of MessageBoundary
				.toArray(new MessageBoundary[0]); //MessageBoundary[]
	}
}
