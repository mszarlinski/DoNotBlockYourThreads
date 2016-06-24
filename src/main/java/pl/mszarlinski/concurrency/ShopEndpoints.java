package pl.mszarlinski.concurrency;

import lombok.RequiredArgsConstructor;
import pl.mszarlinski.concurrency.permission.Permission;
import pl.mszarlinski.concurrency.permission.PermissionService;
import pl.mszarlinski.concurrency.product.Product;
import pl.mszarlinski.concurrency.product.ProductService;
import pl.mszarlinski.concurrency.user.User;
import pl.mszarlinski.concurrency.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mszarlinski on 2016-06-24.
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShopEndpoints {

    private final UserService userService;

    private final PermissionService permissionService;

    private final ProductService productService;

    @RequestMapping("sync/product/{userName}/{productName}")
    public Product getSyncProduct(@PathVariable String userName, @PathVariable String productName) {
        final User user = userService.getSyncUser(userName);
        final Permission permission = permissionService.getSyncPermission(user.getName(), Permission.PRODUCTS);
        Assert.notNull(permission, "User is not permitted to browse products");

        return productService.getSyncProduct(productName);
    }
}
