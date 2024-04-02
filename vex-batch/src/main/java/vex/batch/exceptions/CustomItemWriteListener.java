package vex.batch.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomItemWriteListener implements ItemWriteListener<Object> {

    @Override
    public void onWriteError(Exception exception, @NonNull Chunk<?> items) {
        log.error("An error occurred while writing items: {}. Exception: {}", items, exception.getMessage());
    }
}
