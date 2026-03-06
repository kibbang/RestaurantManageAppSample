package hello.restaurantmanage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RestaurantManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantManageApplication.class, args);
    }

}
