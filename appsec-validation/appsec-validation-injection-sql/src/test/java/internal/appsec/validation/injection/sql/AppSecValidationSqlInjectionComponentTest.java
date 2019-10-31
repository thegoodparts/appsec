package internal.appsec.validation.injection.sql;

import static internal.appsec.validation.injection.sql.AppSecValidationSqlInjectionComponentTest.Fixture.CATEGORY_B;
import static internal.appsec.validation.injection.sql.AppSecValidationSqlInjectionComponentTest.Fixture.POST_A;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Stream.of;
import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import internal.appsec.validation.injection.sql.category.Category;
import internal.appsec.validation.injection.sql.category.CategoryController;
import internal.appsec.validation.injection.sql.post.Post;
import internal.appsec.validation.injection.sql.post.PostController;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE)
@SpringBootTest
class AppSecValidationSqlInjectionComponentTest {

    @Autowired
    CategoryController categoryController;

    @Autowired
    PostController postController;

    @ParameterizedTest
    @MethodSource
    void shouldGetCategories(String postSlug, List<Category> expectedCategories) {
        assertThat(categoryController.getCategories(postSlug)).containsExactlyElementsOf(expectedCategories);
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> shouldGetCategories() {
        //@formatter:off
        return of(
                //        postSlug,                                                            expectedCategories
                arguments("post-a",                                                            singletonList(CATEGORY_B)),
                arguments("post-a' UNION SELECT id, email, password FROM users WHERE '1'= '1", emptyList())
        );
        //@formatter:on
    }

    @ParameterizedTest
    @MethodSource
    void shouldGetPosts(String slug, List<Post> expectedPosts) {
        assertThat(postController.getPosts(slug)).containsExactlyElementsOf(expectedPosts);
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> shouldGetPosts() {
        //@formatter:off
        return of(
                //        slug,                                                                                   expectedPosts
                arguments("post-a",                                                                               singletonList(POST_A)),
                arguments("post-a' OR '1' = '1",                                                                  emptyList()),
                arguments("post-a' UNION SELECT users.id, slug, email, password FROM users, posts WHERE '1'= '1", emptyList())
        );
        //@formatter:on
    }

    interface Fixture {
        Category CATEGORY_B = Category.builder()
                .id(2)
                .slug("category-b")
                .name("Category B")
                .build();

        Post POST_A = Post.builder()
                .id(1)
                .slug("post-a")
                .title("Post A")
                .description("Description A")
                .build();
    }
}
