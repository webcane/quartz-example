package cane.brothers.spring;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class HelloworldJob extends QuartzJobBean {

	private static final Logger log = LoggerFactory.getLogger(HelloworldJob.class);

	private HelloworldTask task;

	public void setTask(HelloworldTask task) {
		this.task = task;
	}
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		if(context.getTrigger() instanceof SimpleTrigger) {
			log.info("Simple Quartz Job started");
		} else {
			log.error("Cron Quartz Job started");
		}
		task.print();
	}

}
