package pl.mszarlinski.concurrency;

import static org.assertj.core.api.Assertions.assertThat;
import pl.mszarlinski.concurrency.permission.Permission;
import pl.mszarlinski.concurrency.permission.PermissionService;
import pl.mszarlinski.concurrency.product.Product;
import pl.mszarlinski.concurrency.product.ProductService;
import pl.mszarlinski.concurrency.user.User;
import pl.mszarlinski.concurrency.user.UserService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author mszarlinski on 2016-06-27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DoNotBlockYourThreadsApplication.class)
public class CompletableFutureTestCase {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ProductService productService;

    @Test
    public void should_return_user() throws ExecutionException, InterruptedException {
        // when
        final User user = userService.getAsyncUser("mszarl").get();
        // then
        assertThat(user).isNotNull();
    }

    @Test
    public void should_return_permission() throws ExecutionException, InterruptedException {
        // when
        final Permission perm = permissionService.getAsyncPermission("mszarl", Permission.PRODUCTS).get();
        // then
        assertThat(perm).isNotNull();
    }

    @Test
    public void should_return_product() throws ExecutionException, InterruptedException {
        // when
        final Product product = productService.getAsyncProduct("NOTEBOOK").get();
        assertThat(product).isNotNull();
    }

    @Test(timeout = 1300)
    public void should_return_notebook_product() throws ExecutionException, InterruptedException {
        // when
        final CompletableFuture<Product> fProduct = productService.getAsyncProduct("NOTEBOOK");

        final CompletableFuture<Permission> fPermission = userService.getAsyncUser("mszarl")
            .thenCompose(user -> permissionService.getAsyncPermission(user.getName(), Permission.PRODUCTS));

        CompletableFuture.allOf(fPermission, fProduct).join();

        // then
        assertThat(fPermission.get()).isNotNull();
        assertThat(fProduct.get()).isNotNull();
    }

}
