package demo;

import java.util.Arrays;

/*
{

	"name":vatikim,
	"streets":[
		"yoav","avner ben ner","raziel"
	],
	radius:3.14

 }
 */
public class NeighborhoodBoundary {
	private String name;
	private String streets[];
	private double radius;
	
	

	public NeighborhoodBoundary(String name, String[] streets, double radius) {
		super();
		this.name = name;
		this.streets = streets;
		this.radius = radius;
	}



	public NeighborhoodBoundary() {
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String[] getStreets() {
		return streets;
	}



	public void setStreets(String[] streets) {
		this.streets = streets;
	}



	public double getRadius() {
		return radius;
	}



	public void setRadius(double radius) {
		this.radius = radius;
	}



	@Override
	public String toString() {
		return "NeighborhoodBoundary [name=" + name + ", streets=" + Arrays.toString(streets) + ", radius=" + radius
				+ "]";
	}
}
