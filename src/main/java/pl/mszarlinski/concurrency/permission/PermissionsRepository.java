package pl.mszarlinski.concurrency.permission;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * @author mszarlinski on 2016-06-24.
 */
@Component
public class PermissionsRepository {

    private static final Map<String, List<String>> DATABASE = ImmutableMap.<String, List<String>>builder()
            .put("mszarl", asList("SPORT", "INFO"))
            .build();

    public Permissions findForUserName(final String userName) {
        return new Permissions(userName, DATABASE.getOrDefault(userName, Collections.<String>emptyList()));
    }
}
