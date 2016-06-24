package pl.mszarlinski.concurrency.product;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author mszarlinski on 2016-06-24.
 */
@Entity
@Data
public class Product {

    @Id
    private Long id;

    private String name;

    private BigDecimal price;
}
