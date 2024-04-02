package vex.batch.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomJobExecutionListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Job {} started with id {}", jobExecution.getJobInstance().getJobName(), jobExecution.getJobId());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus().isUnsuccessful()) {
            log.error("Job failed: [{}] {}. Exit status: {}. Failure Exceptions: {}", jobExecution.getJobId(), jobExecution.getJobInstance().getJobName(), jobExecution.getExitStatus(), jobExecution.getAllFailureExceptions());
        }
    }
}
