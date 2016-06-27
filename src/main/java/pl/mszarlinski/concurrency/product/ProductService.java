package pl.mszarlinski.concurrency.product;

import lombok.RequiredArgsConstructor;
import pl.mszarlinski.concurrency.util.Sleep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

/**
 * @author mszarlinski on 2016-06-24.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {

    private final ProductRepository productRepository;

    private final ListeningExecutorService executorService;

    public Product getSyncProduct(final String productName) {
        Sleep.milis(500);
        return productRepository.findByName(productName);
    }

    public ListenableFuture<Product> getAsyncProduct(final String productName) {
        return executorService.submit(() -> getSyncProduct(productName));
    }
}
