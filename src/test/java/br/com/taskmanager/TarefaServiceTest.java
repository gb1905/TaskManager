
package br.com.taskmanager;

import br.com.taskmanager.exception.TarefaNaoEncontradaException;
import br.com.taskmanager.model.Tarefa;
import br.com.taskmanager.repository.TarefaRepository;
import br.com.taskmanager.service.TarefaService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @InjectMocks
    private TarefaService tarefaService;

    @Test
    void deveSalvarTarefaComSucesso() {

        // Arrange: prepara a tarefa e configura o comportamento do Mock.
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Estudar Java");

        when(tarefaRepository.save(tarefa)).thenReturn(tarefa);

        // Act: executa o método de cadastro do Service.
        Tarefa resultado = tarefaService.salvar(tarefa);

        // Assert: verifica o retorno e a chamada ao Repository.
        assertSame(tarefa, resultado);
        verify(tarefaRepository).save(tarefa);
    }

    @Test
    void deveBuscarTarefaPorIdComSucesso() {

        // Arrange: prepara uma tarefa e simula sua localização pelo ID.
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Estudar Java");

        when(tarefaRepository.findById(1L))
                .thenReturn(Optional.of(tarefa));

        // Act: executa a busca pelo identificador.
        Tarefa resultado = tarefaService.buscarPorId(1L);

        // Assert: verifica o objeto retornado e a consulta ao Repository.
        assertSame(tarefa, resultado);
        verify(tarefaRepository).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoTarefaNaoForEncontrada() {

        // Arrange: configura o Mock para simular um ID inexistente.
        when(tarefaRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act e Assert: executa a busca e verifica a exceção esperada.
        assertThrows(
                TarefaNaoEncontradaException.class,
                () -> tarefaService.buscarPorId(99L)
        );

        verify(tarefaRepository).findById(99L);
    }

    @Test
    void naoDeveExcluirTarefaInexistente() {

        // Arrange: simula que a tarefa não existe no Repository.
        when(tarefaRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act e Assert: verifica a exceção e que a exclusão não aconteceu.
        assertThrows(
                TarefaNaoEncontradaException.class,
                () -> tarefaService.excluir(99L)
        );

        verify(tarefaRepository, never()).delete(any(Tarefa.class));
    }
}
