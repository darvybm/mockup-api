package com.pucmm.eict.mockupapi;

import com.pucmm.eict.mockupapi.enums.UserRole;
import com.pucmm.eict.mockupapi.models.Mock;
import com.pucmm.eict.mockupapi.models.Project;
import com.pucmm.eict.mockupapi.models.User;
import com.pucmm.eict.mockupapi.repositories.MockRepository;
import com.pucmm.eict.mockupapi.repositories.ProjectRepository;
import com.pucmm.eict.mockupapi.repositories.UserRepository;
import com.pucmm.eict.mockupapi.utils.HashGenerator;
import com.pucmm.eict.mockupapi.utils.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class MockupApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MockupApiApplication.class, args);
	}

	@Component
	public static class DbInitializer implements CommandLineRunner {

		private final UserRepository userRepository;
		private final PasswordEncoder passwordEncoder;
		private final ProjectRepository projectRepository;
		private final MockRepository mockRepository;

		@Autowired
		public DbInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder, ProjectRepository projectRepository, MockRepository mockRepository) {
			this.userRepository = userRepository;
			this.passwordEncoder = passwordEncoder;
			this.projectRepository = projectRepository;
			this.mockRepository = mockRepository;
		}

		@Override
		public void run(String... args) throws Exception {
			if (userRepository.findByRole(UserRole.ADMINISTRADOR).isEmpty()) {
				User admin = new User();
				admin.setName("Administrador");
				admin.setUsername("admin");
				admin.setPassword(passwordEncoder.encode("admin"));
				admin.setRole(UserRole.ADMINISTRADOR);

				userRepository.save(admin);
			}

			User adminUser = userRepository.findByUsername("admin");

			// Create StudentAPI project
			Project studentApiProject = new Project();
			studentApiProject.setName("StudentAPI");
			studentApiProject.setDescription("API for managing and retrieving student data.");
			studentApiProject.setUser(adminUser);
			projectRepository.save(studentApiProject);

			// Create CourseAPI project
			Project courseApiProject = new Project();
			courseApiProject.setName("CourseAPI");
			courseApiProject.setDescription("API for managing and retrieving course data.");
			courseApiProject.setUser(adminUser);
			projectRepository.save(courseApiProject);

			// Add mock for StudentAPI project
			Mock getAllStudentsMock = new Mock();
			getAllStudentsMock.setName("Get All Students");
			getAllStudentsMock.setDescription("Retrieve a list of all students.");
			getAllStudentsMock.setEndpoint("students");
			getAllStudentsMock.setMethod("GET");
			getAllStudentsMock.setHeaders("{}"); // Adjust headers as needed
			getAllStudentsMock.setStatusCode(200);
			getAllStudentsMock.setContentType("application/json");
			getAllStudentsMock.setBody("{\"students\": [" +
					"{\"id\": 1, \"firstName\": \"John\", \"lastName\": \"Doe\", \"age\": 20, \"email\": \"john.doe@example.com\", \"major\": \"Computer Science\"}," +
					"{\"id\": 2, \"firstName\": \"Jane\", \"lastName\": \"Smith\", \"age\": 22, \"email\": \"jane.smith@example.com\", \"major\": \"Mathematics\"}," +
					"{\"id\": 3, \"firstName\": \"Alice\", \"lastName\": \"Johnson\", \"age\": 21, \"email\": \"alice.johnson@example.com\", \"major\": \"Biology\"}," +
					"{\"id\": 4, \"firstName\": \"Bob\", \"lastName\": \"Brown\", \"age\": 23, \"email\": \"bob.brown@example.com\", \"major\": \"Physics\"}," +
					"{\"id\": 5, \"firstName\": \"Charlie\", \"lastName\": \"Davis\", \"age\": 19, \"email\": \"charlie.davis@example.com\", \"major\": \"Chemistry\"}" +
					"]}");
			getAllStudentsMock.setHash(HashGenerator.generarHash()); // Example hash
			getAllStudentsMock.setExpirationDate(LocalDateTime.now().plusYears(1));
			getAllStudentsMock.setDelay(1);
			getAllStudentsMock.setValidateJWT(false); // No JWT validation for StudentAPI
			getAllStudentsMock.setProject(studentApiProject);
			getAllStudentsMock.setUser(adminUser);

			mockRepository.save(getAllStudentsMock);

			// Add mock for CourseAPI project
			Mock getAllCoursesMock = new Mock();
			getAllCoursesMock.setName("Get All Courses");
			getAllCoursesMock.setDescription("Retrieve a list of all courses.");
			getAllCoursesMock.setEndpoint("courses");
			getAllCoursesMock.setMethod("GET");
			getAllCoursesMock.setHeaders("{}"); // Adjust headers as needed
			getAllCoursesMock.setStatusCode(200);
			getAllCoursesMock.setContentType("application/json");
			getAllCoursesMock.setBody("{\"courses\": [{\"id\": 101, \"name\": \"Introduction to Computer Science\", \"description\": \"A foundational course covering basic concepts in computer science.\", \"instructor\": \"Dr. Alice Johnson\"}]}");
			getAllCoursesMock.setHash(HashGenerator.generarHash()); // Example hash
			getAllCoursesMock.setExpirationDate(LocalDateTime.now().plusYears(1));
			getAllCoursesMock.setDelay(1);
			getAllCoursesMock.setValidateJWT(true); // JWT validation for CourseAPI
			getAllCoursesMock.setProject(courseApiProject);
			getAllCoursesMock.setUser(adminUser);

			getAllCoursesMock.setToken(TokenGenerator.createToken(getAllCoursesMock));

			mockRepository.save(getAllCoursesMock);
		}
	}
}
