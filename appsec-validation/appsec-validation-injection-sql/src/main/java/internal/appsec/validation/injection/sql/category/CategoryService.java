package internal.appsec.validation.injection.sql.category;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE)
@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getCategories(String postSlug) {
        return categoryRepository.findByPostSlug(postSlug);
    }

}
