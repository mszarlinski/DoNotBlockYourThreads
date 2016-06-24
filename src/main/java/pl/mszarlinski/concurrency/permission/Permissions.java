package pl.mszarlinski.concurrency.permission;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mszarlinski on 2016-06-24.
 */
@Data
@AllArgsConstructor
public class Permissions {

    private String userName;

    private List<String> perms = new ArrayList<>();
}
