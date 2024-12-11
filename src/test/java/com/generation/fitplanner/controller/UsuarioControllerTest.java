package com.generation.fitplanner.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.fitplanner.model.Usuario;
import com.generation.fitplanner.repository.UsuarioRepository;
import com.generation.fitplanner.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeAll
    void start() {
        usuarioRepository.deleteAll();
        usuarioService.cadastrarUsuario(new Usuario(0L, "Root", "root@root.com", "rootroot", "-", "111", 180, 60, "Professor"));
    }

    @Test
    @DisplayName("Cadastrar um Usuário")
    public void deveCriarumUsuario() {
        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<>(new Usuario(0L, "Sonia", "sonia@root.com", "soniaroot", "-", "222", 170, 70, "Aluno"));
        ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);
        assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
    }

    @Test
    @DisplayName("Não deve permitir duplicação do Usuário")
    public void naoDeveDuplicarUsuario() {
        usuarioService.cadastrarUsuario(new Usuario(0L, "Bruna", "bruna@root.com", "brunaroot", "-", "333", 160, 50, "Professor"));
        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<>(new Usuario(0L, "Bruna", "bruna@root.com", "brunaroot", "-", "333", 160, 50, "Professor"));
        ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);
        assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
    }

    @Test
    @DisplayName("Atualizar um Usuário")
    public void deveAtualizarUmUsuario() {
        Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(new Usuario(0L, "Camila", "camila@root.com", "camilaroot", "-", "444", 170, 60, "Aluno"));
        Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(), "Camila", "camila@root.com", "camilaroot", "-", "444", 170, 70, "Aluno");
        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<>(usuarioUpdate);
        ResponseEntity<Usuario> corpoResposta = testRestTemplate.withBasicAuth("root@root.com", "rootroot").exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);
        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
    }

    @Test
    @DisplayName("Listar todos os Usuários")
    public void deveMostrarTodosUsuarios() {
        usuarioService.cadastrarUsuario(new Usuario(0L, "Pedro", "pedro@root.com", "pedroroot", "-", "555", 160, 60, "Aluno"));
        usuarioService.cadastrarUsuario(new Usuario(0L, "Vitor", "vitor@root.com", "vitorroot", "-", "777", 170, 80, "Professor"));
        ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("root@root.com", "rootroot").exchange("/usuarios/all", HttpMethod.GET, null, String.class);
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    @DisplayName("Deletar um Usuário")
    public void deveDeletarUmUsuario() {
        
        Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(new Usuario(0L, "Ana", "ana@root.com", "anaroot", "-", "888", 165, 55, "Aluno"));

        Long usuarioId = usuarioCadastrado.get().getId();
        ResponseEntity<Void> corpoResposta = testRestTemplate.withBasicAuth("root@root.com", "rootroot").exchange("/usuarios/" + usuarioId, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, corpoResposta.getStatusCode());

        ResponseEntity<Usuario> usuarioDeletado = testRestTemplate.withBasicAuth("root@root.com", "rootroot").exchange("/usuarios/" + usuarioId, HttpMethod.GET, null, Usuario.class);
        assertEquals(HttpStatus.NOT_FOUND, usuarioDeletado.getStatusCode());
    }
}


