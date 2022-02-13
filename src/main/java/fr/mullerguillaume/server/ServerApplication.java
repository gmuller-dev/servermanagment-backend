package fr.mullerguillaume.server;

import fr.mullerguillaume.server.enumeration.Status;
import fr.mullerguillaume.server.model.Server;
import fr.mullerguillaume.server.repo.ServerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {

		SpringApplication.run(ServerApplication.class, args);
	}


	@Bean
	CommandLineRunner run(ServerRepo serverRepo){
		return args -> {
			serverRepo.save(new Server(null,"192.168.0.31","Fedora linux","16 GB","Personnal laptop","http://localhost:8080/server/image/server1.png", Status.SERVER_UP));
		};
	}
}
