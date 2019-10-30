package internal.appsec.validation.injection.sql.model;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Post {

    Integer id;
    String slug;
    String title;
    String description;

}
