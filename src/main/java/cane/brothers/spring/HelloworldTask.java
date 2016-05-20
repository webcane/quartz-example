package cane.brothers.spring;

import org.springframework.stereotype.Component;

@Component("task")
public class HelloworldTask {

	public void print() {
		System.out.println("Executing helloworld job.......");
	}
}
