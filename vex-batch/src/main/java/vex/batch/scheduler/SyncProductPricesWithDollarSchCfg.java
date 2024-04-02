package vex.batch.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class SyncProductPricesWithDollarSchCfg {

    private final JobLauncher jobLauncher;
    private final Job syncProductPricesWithDollarJob;

    @Scheduled(cron = "0 0 7-19 * * MON-FRI") // Every hour from 7 to 19 from Monday to Friday
    public void syncProductPricesWithDollar() {
        try {
            log.info("Starting sync product prices with dollar job");
            jobLauncher.run(syncProductPricesWithDollarJob, new JobParametersBuilder()
                .addLong("uniqueness", System.nanoTime()).toJobParameters());
            log.info("Job scheduler finished to work");
        } catch (Exception e) {
            log.error("Error occurred while syncing product prices with dollar", e);
        }
    }

}
