package pl.mszarlinski.concurrency.broadcast;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author mszarlinski on 2016-06-24.
 */
@Data
@AllArgsConstructor
public class Channel {

    private String name;

    private double rating;
}
