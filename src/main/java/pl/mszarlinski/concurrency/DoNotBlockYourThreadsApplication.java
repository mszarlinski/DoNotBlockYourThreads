package pl.mszarlinski.concurrency;

import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

@EnableAsync
@SpringBootApplication
public class DoNotBlockYourThreadsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoNotBlockYourThreadsApplication.class, args);
    }

    @Bean
    public ListeningExecutorService executorService() {
        return MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
    }
}
