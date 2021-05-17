package com.boots.controller

import com.boots.entity.User
import com.boots.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping


@Controller
class RegistrationController {
    @Autowired
    private val userService: UserService? = null
    @GetMapping("/registration")
    fun registration(model: Model): String {
        model.addAttribute("userForm", User())
        return "registration"
    }

    @PostMapping("/registration")
    fun addUser(@ModelAttribute("userForm") userForm: User, bindingResult: BindingResult, model: Model): String {
        if (bindingResult.hasErrors()) {
            return "registration"
        }
        if (userForm.password != userForm.passwordConfirm) {
            model.addAttribute("passwordError", "Пароли не совпадают")
            return "registration"
        }
        if (!userService!!.saveUser(userForm)) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует")
            return "registration"
        }
        return "redirect:/login"
    }
}