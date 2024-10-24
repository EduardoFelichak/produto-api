package com.api.app.controllers;

import com.api.app.dtos.UserDto;
import com.api.app.models.UserModel;
import com.api.app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
public class UserController {
    final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserModel> saveUser(@RequestBody @Valid UserDto userDto) {
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);

        if (userService.getByEmail(userModel.getEmail()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userModel);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
    }

    @PostMapping("/auth")
    public ResponseEntity<UserModel> signIn(@RequestBody UserDto userDto) {
        var user = userService.signIn(userDto.getEmail(), userDto.getPass());

        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserModel> getUser(@PathVariable UUID id) {
        Optional<UserModel> userModel = userService.findById(id);

        return userModel.map(model -> ResponseEntity.ok().body(model)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable UUID id, @RequestBody UserDto userDto) {
        Optional<UserModel> userModelOpt = userService.findById(id);

        if (userModelOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Usuário não encontrado"
            );
        }

        userDto.setId(id);
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);

        return  ResponseEntity.status(HttpStatus.OK).body(
                userService.save(userModel)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID id) {
        Optional<UserModel> userModel = userService.findById(id);

        if (userModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Usuário não encontrado"
            );
        }

        userService.delete(id);

        return ResponseEntity.status(HttpStatus.OK).body(
                "Usuário deletado com sucesso"
        );
    }

}
