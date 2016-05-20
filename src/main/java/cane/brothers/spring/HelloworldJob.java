package cane.brothers.spring;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

//@PersistJobDataAfterExecution
//@DisallowConcurrentExecution
public class HelloworldJob extends QuartzJobBean {

	HelloworldTask task;

	public void setTask(HelloworldTask task) {
		this.task = task;
	}
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		task.print();
	}

}
