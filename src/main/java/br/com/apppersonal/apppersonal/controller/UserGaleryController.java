package br.com.apppersonal.apppersonal.controller;

import br.com.apppersonal.apppersonal.model.entitys.UserGaleryEntity;
import br.com.apppersonal.apppersonal.service.UserGaleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/user-galery")
public class UserGaleryController {
    private final UserGaleryService userGaleryService;

    @Autowired
    public UserGaleryController(UserGaleryService userGaleryService) {
        this.userGaleryService = userGaleryService;
    }

    @PostMapping("/{userId}/save")
    @ResponseStatus(HttpStatus.OK)
    public void saveFotoGalery(@PathVariable Long userId, @RequestBody String urlFoto) {
        userGaleryService.postFoto(userId, urlFoto);
    }

    @GetMapping("/get-fotos/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserGaleryEntity> getFotos(@PathVariable Long userId) {
      return userGaleryService.getFotos(userId);
    }
}
