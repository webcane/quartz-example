package cane.brothers.spring.web;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by cane on 27.08.16.
 */
@Controller
public class CronController {

    @Autowired
    private SchedulerFactoryBean schedulerFactory;

    @Autowired
    private CronTriggerFactoryBean oldTrigger;

    @Autowired
    private JobDetailFactoryBean oldJob;

    @RequestMapping(value = "/cron", method = RequestMethod.GET)
    public String resetCronTask() throws SchedulerException {
        String newSchedule = "0/10 * * * * ?";
        TriggerBuilder<CronTrigger> trBuilder = oldTrigger.getObject().getTriggerBuilder();
        Trigger newTrigger = trBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(newSchedule))
                .forJob(oldJob.getObject()).build();

        TriggerKey newKey = new TriggerKey("cronTriggerFactoryBean", null);
        schedulerFactory.getScheduler().rescheduleJob(newKey, newTrigger);
        return newSchedule;
    }
}
