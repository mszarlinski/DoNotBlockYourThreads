package pl.mszarlinski.concurrency;

import lombok.RequiredArgsConstructor;
import pl.mszarlinski.concurrency.permission.Permission;
import pl.mszarlinski.concurrency.permission.PermissionService;
import pl.mszarlinski.concurrency.product.Product;
import pl.mszarlinski.concurrency.product.ProductService;
import pl.mszarlinski.concurrency.user.User;
import pl.mszarlinski.concurrency.user.UserService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author mszarlinski on 2016-06-24.
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShopEndpoints {

    private final UserService userService;

    private final PermissionService permissionService;

    private final ProductService productService;

    private final ExecutorService executorService;

    @RequestMapping("sync/product/{userName}/{productName}")
    public Product getSyncProduct(@PathVariable String userName, @PathVariable String productName) {
        final User user = userService.getSyncUser(userName);
        final Permission permission = permissionService.getSyncPermission(user.getName(), Permission.PRODUCTS);
        Assert.notNull(permission, "User is not permitted to browse products");

        return productService.getSyncProduct(productName);
    }

    // Thanks to DeferredResult, "http-nio-8080-exec-" threads are not blocked on get(), but are waiting on TaskQueue
    @RequestMapping("async/product/{userName}/{productName}")
    public DeferredResult<Product> getAsyncProduct(@PathVariable String userName, @PathVariable String productName) throws ExecutionException, InterruptedException {
        final CompletableFuture<Product> fProduct = productService.getAsyncProduct(productName);

        final CompletableFuture<Permission> fPermission = userService.getAsyncUser(userName)
            .thenComposeAsync(user -> permissionService.getAsyncPermission(user.getName(), Permission.PRODUCTS), executorService);

        DeferredResult<Product> result = new DeferredResult<>();

        CompletableFuture.allOf(fPermission, fProduct)
            .thenAccept(aVoid -> {
                try {
                    Assert.notNull(fPermission.get(), "User is not permitted to browse products");
                    result.setResult(fProduct.get());
                } catch (Exception ex) {
                    result.setErrorResult(ex);
                }
            });

        return result;
    }

}
