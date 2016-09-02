package cane.brothers.spring.web;

import java.text.ParseException;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cane on 27.08.16.
 */
@RestController
@RequestMapping(value = "/cron")
public class CronController {

    private static final Logger log = LoggerFactory.getLogger(CronController.class);
    	
	@Value("${quartz.crontrigger.name}") 
	private String triggerName;
	
    @Autowired
    private SchedulerFactoryBean schedulerFactory;

    @Autowired
    private CronTriggerFactoryBean oldTriggerFactory;

    @Autowired
    private JobDetailFactoryBean oldJobFactory;

    @RequestMapping(method = RequestMethod.GET)
    public CronDto getCronTask() {
        return new CronDto(oldTriggerFactory.getObject().getCronExpression());
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public CronDto updateCronTask(@RequestBody CronDto cron) throws SchedulerException, ParseException {   	
    	log.info("new cron expression: " + cron);
    	
    	Trigger trigger = getTrigger(cron, true);
        schedulerFactory.getScheduler().rescheduleJob(trigger.getKey(), trigger);
        return cron;
    }
    
    public Trigger getTrigger(CronDto cron, boolean useOld) throws ParseException {
    	log.info("old trigger: " + oldTriggerFactory.getObject()); 
    	return (useOld ? updateOldTrigger(cron) : getNewTrigger(cron));
    }

    // update old trigger, call afterPropertiesSet() to rebuild trigger
    public Trigger updateOldTrigger(CronDto cron) throws ParseException {      	
            oldTriggerFactory.setCronExpression(cron.getExpression());
            oldTriggerFactory.afterPropertiesSet(); 
    	
    	return oldTriggerFactory.getObject();
    }

	// new trigger must have same name
    public Trigger getNewTrigger(CronDto cron) {
    	 Trigger trigger = TriggerBuilder.newTrigger()
                  .withSchedule(CronScheduleBuilder.cronSchedule(cron.getExpression()))
                  .withIdentity(triggerName)
                  .forJob(oldJobFactory.getObject()).build();
    	
    	return trigger;
    }
}
