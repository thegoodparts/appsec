package internal.appsec.validation.injection.sql.category;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Category {

    Integer id;
    String slug;
    String name;

}
