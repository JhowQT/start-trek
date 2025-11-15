package fiap.com.br.start_trek.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fiap.com.br.start_trek.entity.*;
import fiap.com.br.start_trek.service.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tipo-usuario")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TipoUsuarioController {
    
    private final TipoUsuarioService tipoUsuarioService;

    @GetMapping
    public ResponseEntity<List<TipoUsuario>> listarTodos(){
        List<TipoUsuario> tipos = tipoUsuarioService.listarTodos();
        if(tipos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        try {
            TipoUsuario tipoUsuario = tipoUsuarioService.buscarPorID(id);
            return ResponseEntity.ok(tipoUsuario); // Aqui vem o 200
        } catch (Exception e) {
            return ResponseEntity
                .status(404)
                .body("Tipo de usuario não foi encontrado com este ID: " + id);
        }
    }
}
