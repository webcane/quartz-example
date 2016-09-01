package cane.brothers.spring.config;

import cane.brothers.spring.AutowiringSpringBeanJobFactory;
import cane.brothers.spring.HelloworldJob;
import cane.brothers.spring.HelloworldTask;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


@Configuration
public class QuartzConfiguration {

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }
	
	// register all triggers and details here
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource, JobFactory jobFactory)
            throws IOException {
		SchedulerFactoryBean scheduler = new SchedulerFactoryBean();

        // allow to update triggers in DB when updating settings in config file
		scheduler.setOverwriteExistingJobs(true);

        scheduler.setDataSource(dataSource);
        scheduler.setJobFactory(jobFactory);

		scheduler.setQuartzProperties(quartzProperties());
		scheduler.setTriggers(cronTriggerFactoryBean().getObject());
		scheduler.setJobDetails(jobDetailFactoryBean().getObject());
		return scheduler;
	}

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

	// Job is scheduled after every 5 sec
	@Bean
	public CronTriggerFactoryBean cronTriggerFactoryBean() {
		CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
		trigger.setJobDetail(jobDetailFactoryBean().getObject());
		trigger.setStartDelay(3000);
		trigger.setCronExpression("0/5 * * * * ?");

        // in case of misfire, ignore all missed triggers and continue :
       // trigger.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
		return trigger;
	}

	// configure job bean
	@Bean
	public JobDetailFactoryBean jobDetailFactoryBean() {
		JobDetailFactoryBean jobDetail = new JobDetailFactoryBean();

		jobDetail.setName("helloworld");
		jobDetail.setJobClass(HelloworldJob.class);

		// bring real task
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("task", task());

		jobDetail.setJobDataAsMap(map);

        // job has to be durable to be stored in DB
		jobDetail.setDurability(true);
		return jobDetail;
	}
	
	@Bean
    HelloworldTask task() {
		return new HelloworldTask();
	}
}