package internal.appsec.validation.injection.sql.category;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE)
@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/posts/{postSlug}/categories")
    public List<Category> getCategories(@PathVariable("postSlug") String postSlug) {
        return categoryService.getCategories(postSlug);
    }

}
