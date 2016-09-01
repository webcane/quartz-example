package cane.brothers.spring;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("task")
public class HelloworldTask implements Serializable {

	public void print() {
		System.out.println("Executing helloworld job.......");
	}
}
