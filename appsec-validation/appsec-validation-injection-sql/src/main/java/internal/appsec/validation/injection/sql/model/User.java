package internal.appsec.validation.injection.sql.model;

import lombok.Builder;

@Builder
public class User {

    Integer id;
    String username;
    String email;

}
