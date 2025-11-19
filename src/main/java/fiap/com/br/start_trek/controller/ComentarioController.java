package fiap.com.br.start_trek.controller;

import fiap.com.br.start_trek.dto.ComentarioCreateDTO;
import fiap.com.br.start_trek.dto.ComentarioResponseDTO;
import fiap.com.br.start_trek.service.ComentarioService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comentarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ComentarioController {

    private final ComentarioService comentarioService;

    @GetMapping
    public ResponseEntity<?> listarTodos(Pageable pageable) {
        try {
            List<ComentarioResponseDTO> lista = comentarioService.listarTodos();

            int start = (int) pageable.getOffset();
            int end = Math.min(start + pageable.getPageSize(), lista.size());

            Page<ComentarioResponseDTO> pagina =
                    new PageImpl<>(lista.subList(start, end), pageable, lista.size());

            return ResponseEntity.ok(pagina);

        } catch (Exception e) {
            Map<String, Object> erro = new LinkedHashMap<>();
            erro.put("message", "Erro ao listar comentários.");
            return ResponseEntity.status(500).body(erro);
        }
    }

    @GetMapping("/trabalho/{idTrabalho}")
    public ResponseEntity<?> listarPorTrabalho(@PathVariable Long idTrabalho) {
        try {
            List<ComentarioResponseDTO> lista = comentarioService.listarPorTrabalho(idTrabalho);

            return ResponseEntity.ok(lista);

        } catch (RuntimeException e) {
            Map<String, Object> erro = new LinkedHashMap<>();
            erro.put("message", e.getMessage());
            return ResponseEntity.status(404).body(erro);

        } catch (Exception e) {
            Map<String, Object> erro = new LinkedHashMap<>();
            erro.put("message", "Erro ao buscar comentários por trabalho.");
            return ResponseEntity.status(500).body(erro);
        }
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody ComentarioCreateDTO dto) {
        try {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String emailUsuarioLogado = auth.getName();  // <-- vem do token JWT

            ComentarioResponseDTO salvo = comentarioService.criarComentario(dto, emailUsuarioLogado);

            return ResponseEntity.status(201).body(salvo);

        } catch (RuntimeException e) {
            Map<String, Object> erro = new LinkedHashMap<>();
            erro.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(erro);

        } catch (Exception e) {
            Map<String, Object> erro = new LinkedHashMap<>();
            erro.put("message", "Erro interno ao criar comentário.");
            return ResponseEntity.status(500).body(erro);
        }
    }
}
