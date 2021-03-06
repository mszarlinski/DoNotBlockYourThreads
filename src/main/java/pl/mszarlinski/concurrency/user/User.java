package pl.mszarlinski.concurrency.user;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author mszarlinski on 2016-06-24.
 */
@Entity
@Data
public class User {

    @Id
    private Long id;
    
    private String name;
}
