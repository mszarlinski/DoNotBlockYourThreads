package pl.mszarlinski.concurrency;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.mszarlinski.concurrency.permission.Permission;
import pl.mszarlinski.concurrency.permission.PermissionService;
import pl.mszarlinski.concurrency.product.Product;
import pl.mszarlinski.concurrency.product.ProductService;
import pl.mszarlinski.concurrency.user.User;
import pl.mszarlinski.concurrency.user.UserService;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author mszarlinski on 2016-06-24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DoNotBlockYourThreadsApplication.class)
public class SyncTestCase {

    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private ProductService productService;

    @Test
    public void should_return_user() {
        // when
        final User user = userService.getSyncUser("mszarl");
        // then
        assertThat(user).isNotNull();
    }

    @Test
    public void should_return_permission() {
        // when
        final Permission perm = permissionService.getSyncPermission("mszarl", Permission.PRODUCTS);
        // then
        assertThat(perm).isNotNull();
    }

    @Test
    public void should_return_product() {
        // when
        final Product product = productService.getSyncProduct("NOTEBOOK");
        assertThat(product).isNotNull();
    }

    @Test
    public void should_return_notebook_product() {
        // when
        final User user = userService.getSyncUser("mszarl");
        final Permission permission = permissionService.getSyncPermission(user.getName(), Permission.PRODUCTS);
        final Product product = productService.getSyncProduct("NOTEBOOK");

        // then
        assertThat(user).isNotNull();
        assertThat(permission).isNotNull();
        assertThat(product).isNotNull();
    }
}
