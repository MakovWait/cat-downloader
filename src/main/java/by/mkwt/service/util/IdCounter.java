package by.mkwt.service.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class IdCounter {

    private AtomicLong idCounter = new AtomicLong();

    public Long createID() {
        return idCounter.getAndIncrement();
    }

}
