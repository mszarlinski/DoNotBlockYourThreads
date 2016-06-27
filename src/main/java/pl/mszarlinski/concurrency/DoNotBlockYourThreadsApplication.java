package pl.mszarlinski.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// ab -n1000 -c100 -v1 http://localhost:8080/sync/product/mszarl/NOTEBOOK
// ab -n1000 -c100 -v1 http://localhost:8080/async/product/mszarl/NOTEBOOK
@SpringBootApplication
public class DoNotBlockYourThreadsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoNotBlockYourThreadsApplication.class, args);
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(20);
    }
}
