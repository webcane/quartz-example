package cane.brothers.spring.web;

import java.text.ParseException;

import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cane on 27.08.16.
 */
@RestController
@RequestMapping(value = "/quartz/simple")
public class SimpleController {

    private static final Logger log = LoggerFactory.getLogger(SimpleController.class);
    	
	@Value("${quartz.simpletrigger.name}") 
	private String triggerName;
	
    @Autowired
    private SchedulerFactoryBean schedulerFactory;
    
    @Autowired
    private SimpleTriggerFactoryBean oldTriggerFactory;    

    @Autowired
    private JobDetailFactoryBean oldJobFactory;

    @RequestMapping(method = RequestMethod.GET)
    public SimpleQuartzDto getSimpleTask() {
        return new SimpleQuartzDto((short) (oldTriggerFactory.getObject().getRepeatInterval() / 1000L));
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public SimpleQuartzDto updateSimpleTask(@RequestBody SimpleQuartzDto cron) throws SchedulerException, ParseException {   	
    	log.info("new interval: " + cron);
    	
    	Trigger trigger = getTrigger(cron, true);
        schedulerFactory.getScheduler().rescheduleJob(trigger.getKey(), trigger);
        return cron;
    }
    
    public Trigger getTrigger(SimpleQuartzDto cron, boolean useOld) throws ParseException {
    	log.info("old trigger: " + oldTriggerFactory.getObject()); 
    	return (useOld ? updateOldTrigger(cron) : getNewTrigger(cron));
    }

    // update old trigger, call afterPropertiesSet() to rebuild trigger
    public Trigger updateOldTrigger(SimpleQuartzDto entry) throws ParseException {      	
            oldTriggerFactory.setRepeatInterval(entry.getIntervalInHours()*1000L);
            oldTriggerFactory.afterPropertiesSet(); 
    	
    	return oldTriggerFactory.getObject();
    }

	// new trigger must have same name
    public Trigger getNewTrigger(SimpleQuartzDto entry) {
    	 Trigger trigger = TriggerBuilder.newTrigger()
    			 .withIdentity(triggerName) 
    			 .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                		  .withIntervalInSeconds(entry.getIntervalInHours())
                		  .repeatForever())
                  .forJob(oldJobFactory.getObject()).build();
    	
    	return trigger;
    }
}
