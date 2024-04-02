package vex.batch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import vex.batch.exceptions.BatchException;
import vex.batch.models.entities.Product;
import vex.batch.models.enums.Jobs;
import vex.batch.models.enums.Steps;
import vex.batch.processors.ProductDatabaseProcessor;
import vex.batch.readers.ProductDatabaseReader;
import vex.batch.writers.ProductDatabaseWriter;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ModifyProductPriceJobConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final ProductDatabaseWriter productDatabaseWriter;
    private final ProductDatabaseReader productDatabaseReader;
    private final ProductDatabaseProcessor productDatabaseProcessor;

    @Bean
    public Job modifyProductPriceJob() throws BatchException {
        return new JobBuilder(Jobs.MODIFY_PRODUCT_PRICE_JOB.name(),jobRepository)
                .start(modifyProductPriceStep())
                .preventRestart()
                .build();
    }

    @Bean
    public Step modifyProductPriceStep() {
        int chunkSize = 5;
        return new StepBuilder(Steps.MODIFY_PRODUCT_PRICE_STEP.name(), jobRepository)
                .chunk(chunkSize, transactionManager)
                .reader(productDatabaseReader(0L))
                .processor(modifyProductPriceProcessor(100L))
                .writer(productDatabaseWriter())
                .taskExecutor(modifyProductPriceTaskExecutor())
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<List<Product>> productDatabaseReader(@Value("#{jobParameters['BRANCH_ID']}") Long branchId) {
        log.info("Setting branchId to {}", branchId);
        productDatabaseReader.setBranchId(branchId.intValue());
        return productDatabaseReader;
    }

    @Bean
    @StepScope
    public ItemProcessor modifyProductPriceProcessor(@Value("#{jobParameters['PERCENTAGE']}") Long branchId) {
        productDatabaseProcessor.setPercentage(branchId.intValue());
        return productDatabaseProcessor;
    }

    @Bean
    public ItemWriter productDatabaseWriter() {
        return productDatabaseWriter;
    }

    @Bean
    public TaskExecutor modifyProductPriceTaskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(1);
        return asyncTaskExecutor;
    }
}
