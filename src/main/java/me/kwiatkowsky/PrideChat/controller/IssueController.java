package me.kwiatkowsky.PrideChat.controller;

import me.kwiatkowsky.PrideChat.domain.User;
import me.kwiatkowsky.PrideChat.service.UserService;
import me.kwiatkowsky.PrideChat.web.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class IssueController {

    @Autowired
    UserService userService;

    @Autowired
    Session session;


    @RequestMapping({"/", "/index", "/index.html"})
    public String home(Model model){

        if (session.isLogged()){

            model.addAttribute("username", session.getUser().getUsername());
            return "index";
        }

        return "redirect:/app/login";
    }

    @RequestMapping(value = "/app/login", method = RequestMethod.POST)
    public String logging(@Valid User userEntity, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "Login";
        }

        User userFinded = userService.findByUsername(userEntity.getUsername());

        if (userFinded.getId() == null){
            FieldError fieldError = new FieldError(bindingResult.getObjectName(),
                    "username", "Taki użytkownik nie istnieje!");
            bindingResult.addError(fieldError);
            return "Register";
        }

        if(!userFinded.getPassword().equals(userEntity.getPassword())){
            FieldError fieldError = new FieldError(bindingResult.getObjectName(),
                    "username", "Nazwa użytkownika lub hasło nieprawidłowe");
            bindingResult.addError(fieldError);
            return "Login";
        }

        session.setLogged(true);
        session.setUser(userFinded);

        return "redirect:/index";
    }

    @RequestMapping("/app/login")
    public String login(Model model){

        if (session.isLogged()) return "redirect:/index";
        model.addAttribute("user", new User());

        try{
            String dialog = (String) model.asMap().get("dialog");
            model.addAttribute("dialog", dialog);
        } catch (NullPointerException e){
            //
        }

        return "Login";
    }

    @RequestMapping("/app/register")
    public String register(Model model){

        if (session.isLogged()) return "redirect:/index";
        model.addAttribute("user", new User());

        return "Register";
    }

    @RequestMapping(value = "/app/register", method = RequestMethod.POST)
    public ModelAndView registering(@Valid User userEntity, BindingResult bindingResult, RedirectAttributes
            redirectAttributes){

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()){
            modelAndView.setViewName("Register");
            return modelAndView;
        }

        User userFinded = userService.findByUsername(userEntity.getUsername());

        if (userFinded.getId() != null){
            FieldError fieldError = new FieldError(bindingResult.getObjectName(),
                    "username", "Taki użytkownik juz istnieje!");
            bindingResult.addError(fieldError);
            modelAndView.setViewName("Register");
            return modelAndView;
        }

        if (!userEntity.getPassword().equals(userEntity.getRepassword())){
            FieldError fieldError = new FieldError(bindingResult.getObjectName(),
                    "username", "Hasła nie są takie same!");
            bindingResult.addError(fieldError);
            modelAndView.setViewName("Register");
            return modelAndView;
        }

        User user = new User(userEntity.getUsername(), userEntity.getPassword());
        userService.create(user);

        redirectAttributes.addFlashAttribute("dialog", "Rejestracja zakończona pomyślnie!");

        modelAndView.setViewName("redirect:/app/login");
        return modelAndView;
    }

    @RequestMapping(value = "/app/logout")
    public ModelAndView logout(Model model, RedirectAttributes redirectAttributes){

        ModelAndView modelAndView = new ModelAndView();

        if (!session.isLogged()){
            modelAndView.setViewName("redirect:/index");
            return modelAndView;
        }
        session.setLogged(false);
        session.setUser(new User());

        redirectAttributes.addFlashAttribute("dialog", "Poprawnie wylogowano!");

        modelAndView.setViewName("redirect:/app/login");
        return modelAndView;
    }
}
