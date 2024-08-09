package br.com.loja.loja.controller;

import br.com.loja.loja.entity.Usuario;
import br.com.loja.loja.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")  // Permite todas as origens
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<Usuario> getUsuario() {
        return service.todos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioId(@PathVariable Long id) {
        Usuario usuario = service.pegarId(id).orElseThrow(() -> new IllegalArgumentException("nao cadastrado"));
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<Usuario> postUsuario(@RequestBody Usuario usuario) {
        Usuario novoUsuario = service.cadastrar(usuario);
        return ResponseEntity.ok(novoUsuario); // Registro bem-sucedido
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario usuario) {
        Usuario authenticatedUser = service.autenticar(usuario.getEmail(), usuario.getSenha());
        if (authenticatedUser != null) {
            return ResponseEntity.ok(authenticatedUser); // Login bem-sucedido
        } else {
            return ResponseEntity.status(401).body(null); // Falha no login
        }
    }

    @PostMapping("/google-login")
    public ResponseEntity<Usuario> googleLogin(@RequestBody Map<String, String> payload) {
        String token = payload.get("token");
        Usuario authenticatedUser = service.autenticarComGoogle(token);
        if (authenticatedUser != null) {
            return ResponseEntity.ok(authenticatedUser); // Login bem-sucedido
        } else {
            return ResponseEntity.status(401).body(null); // Falha no login
        }
    }

}
