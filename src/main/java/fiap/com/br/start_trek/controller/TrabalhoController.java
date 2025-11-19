package fiap.com.br.start_trek.controller;

import fiap.com.br.start_trek.entity.Trabalho;
import fiap.com.br.start_trek.service.TrabalhoService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trabalhos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TrabalhoController {

    private final TrabalhoService trabalhoService;

    @GetMapping
    public ResponseEntity<?> listar(Pageable pageable) {
        try {
            List<Trabalho> lista = trabalhoService.listarTodos();

            int start = (int) pageable.getOffset();
            int end = Math.min(start + pageable.getPageSize(), lista.size());
            Page<Trabalho> pagina = new PageImpl<>(lista.subList(start, end), pageable, lista.size());

            return ResponseEntity.ok(pagina);

        } catch (Exception e) {
            Map<String, Object> erro = new LinkedHashMap<>();
            erro.put("message", "Erro ao listar trabalhos.");
            return ResponseEntity.status(500).body(erro);
        }
    }

    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<?> listarPorCategoria(@PathVariable Long idCategoria) {
        try {
            List<Trabalho> lista = trabalhoService.listarPorCategoria(idCategoria);
            return ResponseEntity.ok(lista);

        } catch (RuntimeException e) {
            Map<String, Object> erro = new LinkedHashMap<>();
            erro.put("message", e.getMessage());
            return ResponseEntity.status(404).body(erro);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Trabalho trabalho = trabalhoService.buscarPorID(id);
            return ResponseEntity.ok(trabalho);

        } catch (RuntimeException e) {
            Map<String, Object> erro = new LinkedHashMap<>();
            erro.put("message", e.getMessage());
            return ResponseEntity.status(404).body(erro);
        }
    }

    @PostMapping("/categoria/{idCategoria}")
    public ResponseEntity<?> cadastrar(
            @PathVariable Long idCategoria,
            @RequestBody Trabalho trabalho) {
        try {
            Trabalho salvo = trabalhoService.cadastrar(idCategoria, trabalho);
            return ResponseEntity.status(201).body(salvo);

        } catch (RuntimeException e) {
            Map<String, Object> erro = new LinkedHashMap<>();
            erro.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Trabalho trabalho) {
        try {
            Trabalho atualizado = trabalhoService.atualizar(id, trabalho);
            return ResponseEntity.ok(atualizado);

        } catch (RuntimeException e) {
            Map<String, Object> erro = new LinkedHashMap<>();
            erro.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            trabalhoService.excluir(id);
            return ResponseEntity.noContent().build(); 

        } catch (RuntimeException e) {
            Map<String, Object> erro = new LinkedHashMap<>();
            erro.put("message", e.getMessage());
            return ResponseEntity.status(404).body(erro);
        }
    }
}
