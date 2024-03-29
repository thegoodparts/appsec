package internal.appsec.validation.injection.sql.post;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PostDto {

    Integer id;

    String slug;

    String title;

    String description;

}
