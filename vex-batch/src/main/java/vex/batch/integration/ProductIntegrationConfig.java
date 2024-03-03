package vex.batch.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.integration.launch.JobLaunchingGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.file.DefaultFileNameGenerator;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@EnableIntegration
@IntegrationComponentScan
@RequiredArgsConstructor
public class ProductIntegrationConfig {

    @Value("${resources.folder}")
    private String resourcesFolder;

    private final Job importProductsCsvJob;
    private final JobRepository jobRepository;

    @Bean
    public IntegrationFlow integrationFlow(JobLaunchingGateway jobLaunchingGateway){
        return IntegrationFlow.from(fileReadingMessageSource(),
                sourcePolling -> sourcePolling.autoStartup(Boolean.FALSE))
            //.channel(fileIn())
            //.handle(fileRenameProcessingHandler())
            .transform(fileMessageToJobRequest())
            .handle(jobLaunchingGateway)
            .log(LoggingHandler.Level.WARN, "headers.id + ': ' + payload")
            .get();
    }

    @Bean
    public JobLaunchingGateway jobLaunchingGateway(){
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SyncTaskExecutor());
        return new JobLaunchingGateway(jobLauncher);
    }

    public DirectChannel fileIn(){
        return new DirectChannel();
    }

    public FileWritingMessageHandler fileRenameProcessingHandler(){
        var fileWritingMessage = new FileWritingMessageHandler(new File(resourcesFolder));
        fileWritingMessage.setFileExistsMode(FileExistsMode.REPLACE);
        fileWritingMessage.setDeleteSourceFiles(Boolean.TRUE);
        fileWritingMessage.setFileNameGenerator(new DefaultFileNameGenerator());
        fileWritingMessage.setFileNameGenerator(fileNameGenerator());
        fileWritingMessage.setRequiresReply(Boolean.FALSE);
        return fileWritingMessage;
    }

    public FileMessageToJobRequest fileMessageToJobRequest(){
        var transformer = new FileMessageToJobRequest();
        transformer.setJob(importProductsCsvJob);
        return transformer;
    }


    public FileReadingMessageSource fileReadingMessageSource(){
        var messageSource = new FileReadingMessageSource();
        messageSource.setDirectory(new File(resourcesFolder));
        messageSource.setFilter(new SimplePatternFileListFilter("*.csv"));
        return messageSource;
    }

    public DefaultFileNameGenerator fileNameGenerator(){
        var filenameGenerator = new DefaultFileNameGenerator();
        filenameGenerator.setExpression("payload.name + '.processing'");
        return filenameGenerator;
    }
}
