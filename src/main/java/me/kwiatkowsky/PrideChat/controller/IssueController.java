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

import javax.validation.Valid;
import java.util.List;

@Controller
public class IssueController {

    @Autowired
    UserService userService;

    @Autowired
    Session session;

    @RequestMapping("/users")
    public String getUsers(Model model){

        List<User> userList = userService.getList();
        model.addAttribute("userList", userList);

        return "UserList";
    }


    @RequestMapping({"/", "/index", "/index.html"})
    public String home(Model model){

        if (session.isLogged()){

            model.addAttribute("user", session.getUser());
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
                    "username", "This user is not exist!");
            bindingResult.addError(fieldError);
            return "Login";
        }

        if(!userFinded.getPassword().equals(userEntity.getPassword())){
            FieldError fieldError = new FieldError(bindingResult.getObjectName(),
                    "username", "Username or password is incorrect!");
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

        return "Login";
    }

    @RequestMapping(value = "/app/logout")
    public String logout(Model model){

        if (!session.isLogged()) return "redicted:/index";
        session.setLogged(false);
        session.setUser(new User());

        return "redirect:/index";
    }
}
