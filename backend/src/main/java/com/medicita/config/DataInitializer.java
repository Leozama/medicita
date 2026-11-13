package com.medicita.config;

import com.medicita.entity.Paciente;
import com.medicita.repository.PacienteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(PacienteRepository pacienteRepository) {
        return args -> {
            Optional<Paciente> adminOpt = pacienteRepository.findByUserName("admin");
            if (adminOpt.isEmpty()) {
                Paciente admin = new Paciente();
                admin.setFirstName("Administrador");
                admin.setSecondName("Sistema");
                admin.setUserName("admin");
                admin.setPassword("admin123");
                admin.setEmail("admin@medicita.com");
                admin.setTelefono("123456789");
                admin.setFechaNacimiento("2000-01-01");
                pacienteRepository.save(admin);
                System.out.println("✅ Usuario administrador creado en la base de datos (username: admin, password: admin123)");
            } else {
                Paciente admin = adminOpt.get();
                if (!"admin123".equals(admin.getPassword())) {
                    admin.setPassword("admin123");
                    pacienteRepository.save(admin);
                }
                System.out.println("ℹ️ Usuario administrador verificado en la base de datos.");
            }
        };
    }
}

