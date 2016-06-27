package pl.mszarlinski.concurrency.product;

import lombok.RequiredArgsConstructor;
import pl.mszarlinski.concurrency.util.Sleep;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mszarlinski on 2016-06-24.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {

    private final ProductRepository productRepository;

    private final ExecutorService executorService;

    public Product getSyncProduct(final String productName) {
        Sleep.milis(500);
        return productRepository.findByName(productName);
    }

    public CompletableFuture<Product> getAsyncProduct(final String productName) {
        return CompletableFuture.supplyAsync(() -> getSyncProduct(productName), executorService);
    }
}
