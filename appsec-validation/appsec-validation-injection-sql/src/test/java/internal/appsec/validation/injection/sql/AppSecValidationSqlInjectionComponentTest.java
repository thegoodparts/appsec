package internal.appsec.validation.injection.sql;

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

import internal.appsec.validation.injection.sql.post.PostController;
import internal.appsec.validation.injection.sql.post.PostDto;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE)
@SpringBootTest
class AppSecValidationSqlInjectionComponentTest {

    @Autowired
    PostController postController;

    @ParameterizedTest
    @MethodSource
    void shouldGetPosts(String slug, List<PostDto> expectedPosts) {
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
        PostDto POST_A = PostDto.builder()
                .id(1)
                .slug("post-a")
                .title("Post A")
                .description("Description A")
                .build();
    }
}
