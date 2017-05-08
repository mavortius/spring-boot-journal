package com.apress.journal;

import com.apress.journal.domain.Journal;
import com.apress.journal.domain.Role;
import com.apress.journal.domain.RoleAuthority;
import com.apress.journal.domain.User;
import com.apress.journal.repository.JournalRepository;
import com.apress.journal.repository.RoleRepository;
import com.apress.journal.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootJournalApplication {

	@Bean
	InitializingBean initData(JournalRepository repository, UserService userService, RoleRepository roleRepository) {
		return () -> {
			Role reader = new Role(RoleAuthority.READER);
			Role admin = new Role(RoleAuthority.ADMINISTRATOR);

			roleRepository.save(reader);
			roleRepository.save(admin);
			userService.save(new User("johndoe", "1234", reader));
			userService.save(new User("marcelomartins", "mam57523", admin));
			repository.save(new Journal("Get to know Spring Boot", "Today, I will learn Spring Boot",
					"01/01/2016"));
			repository.save(new Journal("Simple Spring Boot Project", "I will do my first Spring Boot Projet",
					"01/02/2016"));
			repository.save(new Journal("Spring Boot Reading", "Read more about Spring Boot",
					"02/01/2016"));
			repository.save(new Journal("Spring Boot in the Cloud", "Spring Boot using Cloud Foundry",
					"03/01/2016"));
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJournalApplication.class, args);
	}
}
