package pl.mszarlinski.concurrency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import pl.mszarlinski.concurrency.permission.Permission;
import pl.mszarlinski.concurrency.permission.PermissionService;
import pl.mszarlinski.concurrency.product.Product;
import pl.mszarlinski.concurrency.product.ProductService;
import pl.mszarlinski.concurrency.user.User;
import pl.mszarlinski.concurrency.user.UserService;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * @author mszarlinski on 2016-06-27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DoNotBlockYourThreadsApplication.class)
public class ListenableFutureTestCase {

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
        final CountDownLatch waitForFutureCompletionLatch = new CountDownLatch(1);

        final ListenableFuture<Product> fProduct = productService.getAsyncProduct("NOTEBOOK");

        final ListenableFuture<User> fUser = userService.getAsyncUser("mszarl");
        Futures.addCallback(fUser, new FutureCallback<User>() {
            @Override
            public void onSuccess(final User user) {
                final ListenableFuture<Permission> fPermission = permissionService.getAsyncPermission(user.getName(), Permission.PRODUCTS);
                Futures.addCallback(fPermission, new FutureCallback<Permission>() {
                    @Override
                    public void onSuccess(final Permission permission) {
                        final Product product = get(fProduct);

                        assertThat(permission).isNotNull();
                        assertThat(product).isNotNull();

                        waitForFutureCompletionLatch.countDown();
                    }

                    @Override
                    public void onFailure(final Throwable t) {
                        fail(t.getMessage());
                    }
                });
                get(fPermission);
            }

            @Override
            public void onFailure(final Throwable t) {
                fail(t.getMessage());
            }
        });

        fUser.get();

        waitForFutureCompletionLatch.await();
    }

    private <T> T get(final ListenableFuture<T> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
