package cane.brothers.spring;

import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class HelloworldJob extends QuartzJobBean {

	private HelloworldTask task;

	public void setTask(HelloworldTask task) {
		this.task = task;
	}
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Cron Quartz Job started at time: "+ LocalDateTime.now());
		task.print();
	}

}
