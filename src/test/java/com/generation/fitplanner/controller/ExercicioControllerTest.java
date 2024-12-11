package com.generation.fitplanner.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.generation.fitplanner.model.Exercicio;
import com.generation.fitplanner.repository.ExercicioRepository;
import com.generation.fitplanner.service.UsuarioService;

public class ExercicioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ExercicioRepository exercicioRepository;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private ExercicioController exercicioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(exercicioController).build();
    }

    @Test
    @DisplayName("Cadastrar um Exercicio")
    public void deveCriarUmExercicio() throws Exception {
        Exercicio novoExercicio = new Exercicio(0L, "Agachamento", 15, 4, "Instrução para agachamento");
        Exercicio exercicioSalvo = new Exercicio(1L, "Agachamento", 15, 4, "Instrução para agachamento");

        when(exercicioRepository.save(any(Exercicio.class))).thenReturn(exercicioSalvo);

        mockMvc.perform(post("/exercicios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nomeExercicio\": \"Agachamento\", \"repeticoes\": 15, \"series\": 4, \"instrucao\": \"Instrução para agachamento\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nomeExercicio").value("Agachamento"));
    }

    @Test
    @DisplayName("Listar todos os Exercicios")
    public void deveMostrarTodosExercicios() throws Exception {
        when(exercicioRepository.findAll()).thenReturn(Arrays.asList(
                new Exercicio(1L, "Agachamento", 15, 4, "Instrução para agachamento"),
                new Exercicio(2L, "Supino", 12, 3, "Instrução para supino")
        ));

        mockMvc.perform(get("/exercicios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomeExercicio").value("Agachamento"))
                .andExpect(jsonPath("$[1].nomeExercicio").value("Supino"));
    }

    @Test
    @DisplayName("Buscar Exercicio por ID")
    public void deveBuscarExercicioPorId() throws Exception {
        Exercicio exercicio = new Exercicio(1L, "Agachamento", 15, 4, "Instrução para agachamento");

        when(exercicioRepository.findById(1L)).thenReturn(Optional.of(exercicio));

        mockMvc.perform(get("/exercicios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeExercicio").value("Agachamento"));
    }

    @Test
    @DisplayName("Atualizar um Exercicio")
    public void deveAtualizarUmExercicio() throws Exception {
        Exercicio exercicioExistente = new Exercicio(1L, "Supino", 12, 3, "Instrução para supino");
        Exercicio exercicioAtualizado = new Exercicio(1L, "Supino Inclinado", 15, 4, "Instrução para supino inclinado");

        when(exercicioRepository.findById(1L)).thenReturn(Optional.of(exercicioExistente));
        when(exercicioRepository.save(any(Exercicio.class))).thenReturn(exercicioAtualizado);

        mockMvc.perform(put("/exercicios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1, \"nomeExercicio\": \"Supino Inclinado\", \"repeticoes\": 15, \"series\": 4, \"instrucao\": \"Instrução para supino inclinado\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeExercicio").value("Supino Inclinado"));
    }

    @Test
    @DisplayName("Deletar um Exercicio")
    public void deveDeletarUmExercicio() throws Exception {
        Exercicio exercicio = new Exercicio(1L, "Leg Press", 15, 4, "Instrução para leg press");

        when(exercicioRepository.findById(1L)).thenReturn(Optional.of(exercicio));
        doNothing().when(exercicioRepository).deleteById(1L);

        mockMvc.perform(delete("/exercicios/1"))
                .andExpect(status().isNoContent());
    }
}
