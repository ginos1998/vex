package vex.batch.integration;

import lombok.Setter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import vex.batch.models.enums.Parameters;

import java.io.File;
import java.util.Date;

@Component
@Setter
public class FileMessageToJobRequest {
    private Job job;

    @Transformer
    public JobLaunchRequest jobLaunchRequest(Message<File> fileMessage){
        var jobParameters = new JobParametersBuilder();
        jobParameters.addString(Parameters.INPUT_FILE_NAME.name() ,fileMessage.getPayload().getAbsolutePath());
        jobParameters.addDate(Parameters.UNIQUENESS.name(), new Date());
        return new JobLaunchRequest(job,jobParameters.toJobParameters());
    }

}