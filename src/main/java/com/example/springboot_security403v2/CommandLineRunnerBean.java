package com.example.springboot_security403v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerBean implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CarRepository carRepository;

    public void run(String... args) {
        User user = new User("bart", "bart@domain.com", "bart", "Bart", "Simpson", true);
        Role userRole = new Role("bart", "ROLE_USER");

        userRepository.save(user);
        roleRepository.save(userRole);

        User admin = new User("super", "super@domain.com", "super", "Super", "Hero", true);
        Role adminRole1 = new Role("super", "ROLE_ADMIN");
        Role adminRole2 = new Role("super", "ROLE_USER");

        userRepository.save(admin);
        roleRepository.save(adminRole1);
        roleRepository.save(adminRole2);

        Car car = new Car("CT4", "Cadillac", "compact luxury sudan in red",
                "https://res.cloudinary.com/dnjzvt63c/image/upload/v1628532053/https___specials-images.forbesimg.com_imageserve_5d3703b3090f4300070d570d_2020-Cadillac-CT5_960x0_udnqey.jpg");
        Car car2 = new Car("Corvette SR", "Chevorlet", "Sleek sports car that goes fast", "https://res.cloudinary.com/dnjzvt63c/image/upload/v1628532108/https___specials-images.forbesimg.com_imageserve_5d3703e2f1176b00089761a6_2020-Chevrolet-Corvette-Stingray_960x0_mve90l.jpg");
        carRepository.save(car);
        carRepository.save(car2);
    }
}
