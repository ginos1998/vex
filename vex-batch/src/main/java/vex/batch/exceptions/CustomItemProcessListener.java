package vex.batch.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomItemProcessListener implements ItemProcessListener<Object, Object> {

    @Override
    public void onProcessError(@NonNull Object item, Exception e) {
        log.error("An error occurred while processing item: {}. Exception: {}", item, e.getMessage());
    }
}