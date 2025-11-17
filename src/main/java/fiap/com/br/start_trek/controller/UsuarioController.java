package fiap.com.br.start_trek.controller;

import fiap.com.br.start_trek.dto.*;
import fiap.com.br.start_trek.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Page<UsuarioResponseDTO>> listarUsuarios(Pageable pageable) {
        Page<UsuarioResponseDTO> pagina = usuarioService.listarUsuariosPaginado(pageable);
        return ResponseEntity.ok(pagina);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody UsuarioCreateDTO dto) {
        try {
            var salvo = usuarioService.cadastraUsuario(dto);
            return ResponseEntity.status(201).body(salvo); 

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            var usuario = usuarioService.buscarUsuarioPorId(id);
            return ResponseEntity.ok(usuario); 
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable Long id,
            @RequestBody UsuarioUpdateDTO dto) {

        try {
            var atualizado = usuarioService.atualizarUsuario(id, dto);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); 
        }
    }

    @PatchMapping("/{id}/foto")
    public ResponseEntity<?> atualizarFoto(
            @PathVariable Long id,
            @ModelAttribute UsuarioFotoDTO dto) {

        try {
            var atualizado = usuarioService.atualizarFoto(id, dto);
            return ResponseEntity.ok(atualizado);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao enviar foto."); 
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            usuarioService.deletarUsuario(id);
            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
