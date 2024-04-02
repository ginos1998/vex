package vex.batch.readers.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import vex.batch.exceptions.BatchException;
import vex.batch.exceptions.ExceptionType;
import vex.batch.models.enums.Reader;
import vex.batch.models.entities.Product;

import java.util.List;

@Slf4j
@Component
public class ProductCsvReaderImpl extends FlatFileItemReader<Product> {
    
    public ProductCsvReaderImpl() {
        super();
        setName(Reader.PRODUCT_ITEM_READER.name());
        setLinesToSkip(1);
        setLineMapper(lineMapper());
    }

    public void setResourceFile(Resource resource) {
        setResource(resource);
    }

    public void initialize() {
        try {
            log.info("Initializing ProductCsvReaderImpl");
            super.doOpen();
            super.read();
        } catch (Exception e) {
            throw new BatchException(ExceptionType.PRODUCT_CSV_READER_EXCEPTION, List.of(e.getMessage()), e);
        }
    }

    public void closeFile() {
        try {
            log.info("Closing ProductCsvReaderImpl");
            super.doClose();
        } catch (Exception e) {
            throw new BatchException(ExceptionType.PRODUCT_CSV_READER_EXCEPTION, List.of(e.getMessage()), e);
        }
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
