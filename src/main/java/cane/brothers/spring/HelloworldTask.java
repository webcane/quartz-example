package cane.brothers.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("task")
public class HelloworldTask implements Serializable {

	private static final Logger log = LoggerFactory.getLogger(HelloworldTask.class);

	public void print() {
			log.info("Executing job.......");
	}
}
