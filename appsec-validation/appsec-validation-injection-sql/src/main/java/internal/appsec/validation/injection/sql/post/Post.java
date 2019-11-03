package internal.appsec.validation.injection.sql.post;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    Integer id;

    @Column
    String slug;

    @Column
    String title;

    @Column
    String description;

}
