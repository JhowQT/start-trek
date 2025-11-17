package fiap.com.br.start_trek.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fiap.com.br.start_trek.entity.TipoUsuario;
import fiap.com.br.start_trek.service.TipoUsuarioService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tipo-usuario")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TipoUsuarioController {
    
    private final TipoUsuarioService tipoUsuarioService;

    @GetMapping
    public ResponseEntity<Page<TipoUsuario>> listarTodos(Pageable pageable) {
        Page<TipoUsuario> pagina = tipoUsuarioService.listarTodos(pageable);
        
        if (pagina.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/todos-tipos")
    public ResponseEntity<?> listarTodosSemPaginacao() {
        var todos = tipoUsuarioService.listarTodos();
        
        if (todos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            TipoUsuario tipoUsuario = tipoUsuarioService.buscarPorID(id);
            return ResponseEntity.ok(tipoUsuario);
        } catch (Exception e) {
            return ResponseEntity
                .status(404)
                .body("Tipo de usuario não foi encontrado com este ID: " + id);
        }
    }
}