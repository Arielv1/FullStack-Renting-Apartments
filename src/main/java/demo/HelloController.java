package demo;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@RequestMapping(path = "/hello",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public MessageBoundary hello () {
		return new MessageBoundary ("Hello World!");
	}	
	
	//REST API: /hello/myname --> {"message" : "Hello myname"} JSON
	@RequestMapping(path = "/hello/{name}", 
					method = RequestMethod.GET,
					produces = MediaType.APPLICATION_JSON_VALUE)
	public MessageBoundary hello (@PathVariable("name") String name) {
		if (name != null && !name.trim().isEmpty())
			return new MessageBoundary ("Hello " +  name + " ! ");
		else 
			throw new NameNotFoundException("Invalid Name!");
	}
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Map <String, Object> handleError (NameNotFoundException e) {
		String error = e.getMessage();
		if (error == null)
		{
			error = "name not found";
		}
		
		// {"error" : "name not found"}
		return Collections.singletonMap("error", error);
	}
}
