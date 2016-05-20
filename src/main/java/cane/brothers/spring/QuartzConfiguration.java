package cane.brothers.spring;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
 

@Configuration
public class QuartzConfiguration {
	
	// register all triggers and details here
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
		//scheduler.setOverwriteExistingJobs(true);
		scheduler.setTriggers(cronTriggerFactoryBean().getObject());
		scheduler.setJobDetails(jobDetailFactoryBean().getObject());
		return scheduler;
	}

	// Job is scheduled after every 5 sec
	@Bean
	public CronTriggerFactoryBean cronTriggerFactoryBean() {
		CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
		trigger.setJobDetail(jobDetailFactoryBean().getObject());
		trigger.setStartDelay(3000);
		trigger.setCronExpression("0/5 * * * * ?");
		return trigger;
	}

	// configure job bean
	@Bean
	public JobDetailFactoryBean jobDetailFactoryBean() {
		JobDetailFactoryBean jobDetail = new JobDetailFactoryBean();
		jobDetail.setJobClass(HelloworldJob.class);
		
		// bring real task
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("task", task());
		jobDetail.setJobDataAsMap(map);
		
		jobDetail.setDurability(true);
		jobDetail.setName("helloworld");
		return jobDetail;
	}
	
	@Bean 
	HelloworldTask task() {
		return new HelloworldTask();
	}
}
