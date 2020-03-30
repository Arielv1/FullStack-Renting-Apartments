package demo;

import java.util.stream.*;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NeighborhoodController {
	@RequestMapping(path = "/neighborhood/{name}", 
					method = RequestMethod.POST,
					produces = MediaType.APPLICATION_JSON_VALUE)
	public NeighborhoodBoundary neighborhoods(@PathVariable("name") String name) {
		return new NeighborhoodBoundary(name, new String[]{"yoav","avner ben ner","raziel"}, 3.14);
	
	}
	@RequestMapping (path = "/neighborhoodPost", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
			public NeighborhoodBoundary neighborhoodPost(@RequestBody NeighborhoodBoundary neighborhoodpostman) {
				return new NeighborhoodBoundary(neighborhoodpostman.getName(), neighborhoodpostman.getStreets(), neighborhoodpostman.getRadius());
		}
	
	@RequestMapping (path = "/neighborhood/{count}",
					 method = RequestMethod.GET,
					 produces = MediaType.APPLICATION_JSON_VALUE)
	public NeighborhoodBoundary[] getManyneighborhoods (@PathVariable("count") int count) {
		return IntStream.range(0, count) //stream of integers  // we using stream but we can use for as well
				.mapToObj(i -> "Neighborhood #" + (i+1))  //stream of strings
				.map(msg -> new NeighborhoodBoundary(msg, new String[]{"hadar","limon"},9.99)) // stream of message boundary
				.collect(Collectors.toList()) // list of MessageBoundary
				.toArray(new NeighborhoodBoundary[0]); //MessageBoundary[]
	}
}

