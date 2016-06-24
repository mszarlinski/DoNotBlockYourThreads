package pl.mszarlinski.concurrency.permission;

import lombok.Data;
import pl.mszarlinski.concurrency.user.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author mszarlinski on 2016-06-24.
 */
@Entity
@Data
public class Permission {

    public static final String PRODUCTS = "PRODUCTS";
    public static final String REVIEWS = "REVIEWS";

    @Id
    private Long id;

    @ManyToOne
    private User user;

    private String name;
}
