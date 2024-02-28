package vex.batch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import vex.batch.models.entities.Product;
import vex.batch.repository.ProductRepository;

@Configuration
@Slf4j
public class ImportProductsCsvJob {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final ProductRepository productRepository;

    public ImportProductsCsvJob(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                ProductRepository productRepository) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.productRepository = productRepository;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Product> reader(@Value("#{jobParameters['csvFilePath']}") String filePath) {
        return new FlatFileItemReaderBuilder<Product>()
            .name(Reader.PRODUCT_ITEM_READER.name())
            .resource(new ClassPathResource(filePath))
            .linesToSkip(1)
            .lineMapper(lineMapper())
            .build();
    }

    @Bean
    public ProductProcessor processor() {
        return new ProductProcessor();
    }

    @Bean
    public RepositoryItemWriter<Product> writer() {
        RepositoryItemWriter<Product> itemWriter = new RepositoryItemWriter<>();
        itemWriter.setRepository(productRepository);
        itemWriter.setMethodName("save");
        return itemWriter;
    }

    @Bean
    public Step importProductsCsvStep() {
        int chunkSize = 20;
        return new StepBuilder("importProductsCsvStep", jobRepository)
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
    public Job importProductsCsv() {
        return new JobBuilder(Jobs.IMPORT_PRODUCT_CSV_JOB.getName(), jobRepository)
            .start(importProductsCsvStep())
            .build();
    }

    private LineMapper<Product> lineMapper() {
        DefaultLineMapper<Product> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        BeanWrapperFieldSetMapper<Product> fieldSetMapper = new BeanWrapperFieldSetMapper<>();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(Product.getCsvHeaders());
        fieldSetMapper.setTargetType(Product.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }
}
