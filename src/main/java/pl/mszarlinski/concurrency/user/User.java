package pl.mszarlinski.concurrency.user;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author mszarlinski on 2016-06-24.
 */
@Data
@AllArgsConstructor
public class User {

    private String name;

    private int age;
}
