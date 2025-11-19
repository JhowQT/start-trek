package fiap.com.br.start_trek.controller;

import fiap.com.br.start_trek.entity.Categoria;
import fiap.com.br.start_trek.service.CategoriaService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<?> listar(Pageable pageable) {
        try {
            List<Categoria> lista = categoriaService.listarTodos();

            int start = (int) pageable.getOffset();
            int end = Math.min(start + pageable.getPageSize(), lista.size());

            Page<Categoria> pagina =
                    new PageImpl<>(lista.subList(start, end), pageable, lista.size());

            return ResponseEntity.ok(pagina); 

        } catch (Exception e) {
            Map<String, Object> erro = new LinkedHashMap<>();
            erro.put("message", "Erro ao listar categorias.");
            return ResponseEntity.status(500).body(erro);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Categoria categoria = categoriaService.buscarPorId(id);
            return ResponseEntity.ok(categoria); 

        } catch (RuntimeException e) {
            Map<String, Object> erro = new LinkedHashMap<>();
            erro.put("message", e.getMessage());
            return ResponseEntity.status(404).body(erro);
        }
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Categoria categoria) {
        try {
            Categoria salvo = categoriaService.criarCategoria(categoria);
            return ResponseEntity.status(201).body(salvo); 

        } catch (RuntimeException e) {
            Map<String, Object> erro = new LinkedHashMap<>();
            erro.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(erro); 

        } catch (Exception e) {
            Map<String, Object> erro = new LinkedHashMap<>();
            erro.put("message", "Erro interno ao criar categoria.");
            return ResponseEntity.status(500).body(erro); 
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable Long id,
            @RequestBody Categoria categoria) {

        try {
            Categoria atualizado = categoriaService.atualizarCategoria(id, categoria);
            return ResponseEntity.ok(atualizado); 

        } catch (RuntimeException e) {
            Map<String, Object> erro = new LinkedHashMap<>();
            erro.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(erro); 

        } catch (Exception e) {
            Map<String, Object> erro = new LinkedHashMap<>();
            erro.put("message", "Erro interno ao atualizar categoria.");
            return ResponseEntity.status(500).body(erro);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        try {
            categoriaService.excluir(id);
            return ResponseEntity.noContent().build(); 

        } catch (RuntimeException e) {
            Map<String, Object> erro = new LinkedHashMap<>();
            erro.put("message", e.getMessage());
            return ResponseEntity.status(404).body(erro); 
        }
    }
}
