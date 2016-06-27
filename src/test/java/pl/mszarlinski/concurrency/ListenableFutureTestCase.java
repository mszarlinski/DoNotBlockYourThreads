package pl.mszarlinski.concurrency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import lombok.AllArgsConstructor;
import pl.mszarlinski.concurrency.permission.Permission;
import pl.mszarlinski.concurrency.permission.PermissionService;
import pl.mszarlinski.concurrency.product.Product;
import pl.mszarlinski.concurrency.product.ProductService;
import pl.mszarlinski.concurrency.user.User;
import pl.mszarlinski.concurrency.user.UserService;

import java.util.List;
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
        final ListenableFuture<Permission> fPermission = Futures.transformAsync(fUser, user -> permissionService.getAsyncPermission(user.getName(), Permission.PRODUCTS));

        final ListenableFuture<List<Object>> fWholeOperation = Futures.allAsList(fPermission, fProduct);
        final ListenableFuture<Result> fResult = Futures.transform(fWholeOperation, this::fromList);

        // then

        Futures.addCallback(fResult, new FutureCallback<Result>() {
            @Override
            public void onSuccess(final Result result) {
                assertThat(result.permission).isNotNull();
                assertThat(result.product).isNotNull();
                waitForFutureCompletionLatch.countDown();
            }

            @Override
            public void onFailure(final Throwable t) {
                fail(t.getMessage());
            }
        });

        fResult.get();

        waitForFutureCompletionLatch.await();
    }

    private Result fromList(final List<Object> input) {
        return new Result((Permission) input.get(0), (Product) input.get(1));
    }

    @AllArgsConstructor
    private class Result {
        Permission permission;

        Product product;
    }

}
