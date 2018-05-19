package me.kwiatkowsky.PrideChat;

import me.kwiatkowsky.PrideChat.domain.User;
import me.kwiatkowsky.PrideChat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@SpringBootApplication
public class PrideChatApplication implements CommandLineRunner{

	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(PrideChatApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		userService.getList().forEach(user -> userService.delete(user));

		User user = new User("admin123", "qwerty123");
		userService.create(user);
	}
}
