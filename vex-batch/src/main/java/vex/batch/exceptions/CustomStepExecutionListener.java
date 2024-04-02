package vex.batch.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomStepExecutionListener implements StepExecutionListener {

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if (stepExecution.getStatus().isUnsuccessful()) {
            log.error("Step failed: {}. Exit status: {}. Failure Exceptions: {}", stepExecution.getStepName(), stepExecution.getExitStatus(), stepExecution.getFailureExceptions());
        }
        return stepExecution.getExitStatus();
    }
}