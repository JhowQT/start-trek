package fiap.com.br.start_trek.controller;

import fiap.com.br.start_trek.dto.LoginCreateDTO;
import fiap.com.br.start_trek.service.LoginService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginCreateDTO loginDTO) {
        try {
            var response = loginService.autenticar(loginDTO);

            Map<String, Object> body = new LinkedHashMap<>();
            body.put("data", response);

            return ResponseEntity.ok(body);

        } catch (RuntimeException e) {
            Map<String, Object> erro = new LinkedHashMap<>();
            erro.put("message", e.getMessage());
            return ResponseEntity.status(401).body(erro);

        } catch (Exception e) {
            Map<String, Object> erro = new LinkedHashMap<>();
            erro.put("message", "Erro interno ao processar login.");
            return ResponseEntity.status(500).body(erro);
        }
    }
}
