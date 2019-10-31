package internal.appsec.validation.injection.sql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ImportAutoConfiguration
public class AppSecValidationSqlInjectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppSecValidationSqlInjectionApplication.class);
    }

}
