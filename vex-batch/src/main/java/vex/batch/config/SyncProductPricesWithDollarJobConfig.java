package vex.batch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import vex.batch.exceptions.BatchException;
import vex.batch.models.entities.Product;
import vex.batch.models.enums.Jobs;
import vex.batch.models.enums.Steps;
import vex.batch.processors.SyncProductPricesWithDollarProcessor;
import vex.batch.readers.SyncProductPricesWithDollarReader;
import vex.batch.writers.SyncProductPricesWithDollarWritter;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SyncProductPricesWithDollarJobConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final SyncProductPricesWithDollarReader syncProductPricesWithDollarReader;
    private final SyncProductPricesWithDollarProcessor syncProductPricesWithDollarProcessor;
    private final SyncProductPricesWithDollarWritter syncProductPricesWithDollarWritter;

    @Bean
    public Job syncProductPricesWithDollarJob() throws BatchException {
        return new JobBuilder(Jobs.SYNC_PRODUCT_PRICES_WITH_DOLLAR_JOB.name(),jobRepository)
            .start(findProductToSyncPriceWithDollarStep())
            .next(endJobStep())
            .build();
    }

    @Bean
    public Step findProductToSyncPriceWithDollarStep() {
        int chunkSize = 5;
        return new StepBuilder(Steps.FIND_PRODUCT_TO_SYNC_PRICE_WITH_DOLLAR_STEP.name(), jobRepository)
            .chunk(chunkSize, transactionManager)
            .reader(readProductsToSyncPriceWithDollar())
            .processor(processProductsToSyncPriceWithDollar())
            .writer(writeProductsToSyncPriceWithDollar())
            .build();
    }

    @Bean
    public ItemReader<List<Product>> readProductsToSyncPriceWithDollar() {
        return syncProductPricesWithDollarReader;
    }

    @Bean
    public ItemProcessor processProductsToSyncPriceWithDollar() {
        return syncProductPricesWithDollarProcessor;
    }

    @Bean
    public ItemWriter<List<Product>> writeProductsToSyncPriceWithDollar() {
        return syncProductPricesWithDollarWritter;
    }

    @Bean
    public Step endJobStep() {
        return new StepBuilder(Steps.CLEAN_UP.name(), jobRepository)
            .tasklet((contribution, chunkContext) -> {
                log.info("Clean up job step");
                syncProductPricesWithDollarReader.setRead(false);
                return RepeatStatus.FINISHED;
            }, transactionManager)
            .build();
    }


}
