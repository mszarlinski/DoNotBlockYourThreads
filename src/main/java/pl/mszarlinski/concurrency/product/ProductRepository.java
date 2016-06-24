package pl.mszarlinski.concurrency.product;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author mszarlinski on 2016-06-25.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String productName);
}
