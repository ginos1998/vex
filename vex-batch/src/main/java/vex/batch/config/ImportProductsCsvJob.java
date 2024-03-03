package vex.batch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import vex.batch.models.entities.Product;
import vex.batch.models.enums.Jobs;
import vex.batch.models.enums.Steps;
import vex.batch.processors.ProductProcessor;
import vex.batch.readers.impl.ProductCsvReaderImpl;
import vex.batch.writers.ProductItemWriter;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ImportProductsCsvJob {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final ProductItemWriter productItemWriter;
    private final ProductCsvReaderImpl productCsvReaderImpl;

    @Bean
    @StepScope
    public ItemReader<Product> reader(@Value("#{jobParameters['INPUT_FILE_NAME']}") String filePath) throws Exception {
        log.info("Reading file: {}", filePath);
        assert productCsvReaderImpl != null;
        productCsvReaderImpl.setResourceFile(new FileSystemResource(filePath));
        productCsvReaderImpl.initialize();
        return productCsvReaderImpl;
    }

    @Bean
    public ItemWriter<Product> writer() {
        productCsvReaderImpl.closeFile();
        return productItemWriter;
    }

    @Bean
    public ProductProcessor processor() {
        return new ProductProcessor();
    }

    @Bean
    public Step importProductsCsvStep() throws Exception {
        int chunkSize = 20;
        return new StepBuilder(Steps.IMPORT_PRODUCT_CSV.name(), jobRepository)
            .<Product, Product>chunk(chunkSize, transactionManager)
            .reader(reader(""))
            .processor(processor())
            .writer(writer())
            .taskExecutor(taskExecutor())
            .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(5);
        return asyncTaskExecutor;
    }

    @Bean
    public Job importProductsCsv() throws Exception {
        return new JobBuilder(Jobs.IMPORT_PRODUCT_CSV_JOB.getName(), jobRepository)
            .start(importProductsCsvStep())
            .next(cleanUpJobStep())
            .build();
    }

    @Bean
    public Step cleanUpJobStep() {
        return new StepBuilder(Steps.CLEAN_UP.name(), jobRepository)
            .tasklet((contribution, chunkContext) -> {
                log.info("Clean up job step");
                productCsvReaderImpl.closeFile();
                productCsvReaderImpl.close();
                return RepeatStatus.FINISHED;
            }, transactionManager)
            .build();
    }

}
