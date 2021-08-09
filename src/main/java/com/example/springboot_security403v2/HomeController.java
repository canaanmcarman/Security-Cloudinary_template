package com.example.springboot_security403v2;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String listCars(Model model) {
        model.addAttribute("cars", carRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String newCar(Model model) {
        Car car = new Car();
        car.setPic("");
        model.addAttribute("car", car);
        return "carForm";
    }
    @PostMapping("/add")
    public String processCar(@ModelAttribute Car car, @RequestParam("file") MultipartFile file) {
        if(file.isEmpty()) {
            return "redirect:/add";
        }

        try {
            Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourceType", "auto"));
            car.setPic(uploadResult.get("url").toString());
           carRepository.save(car);
        }catch(IOException e) {
            e.printStackTrace();
            return "redirect:/add";
        }
        return "redirect:/";

    }





    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/processregister")
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.clearPassword();
            model.addAttribute("user", user);
            return "register";
        } else {
            model.addAttribute("user", user);
            model.addAttribute("message", "New user account created");
            user.setEnabled(true);
            userRepository.save(user);

            Role role = new Role(user.getUsername(), "ROLE_USER");
            roleRepository.save(role);
        }
        return "redirect:/";

    }

    @RequestMapping("/secure")
    public String secure(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("user", userRepository.findByUsername(username));
        return "secure";

    }



    @RequestMapping("/course")
    public String coursePage() {
        return "course";
    }
    @RequestMapping("/admin")
    public String teacherPage() {
        return "admin";
    }
    @RequestMapping("/student")
    public String studenPage() {
        return "student";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        return  "redirect:/login?logout=true";
    }
}
