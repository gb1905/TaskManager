
package br.com.taskmanager.service;

import br.com.taskmanager.exception.TarefaNaoEncontradaException;
import br.com.taskmanager.model.Tarefa;
import br.com.taskmanager.repository.TarefaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {

    private static final Logger log =
            LoggerFactory.getLogger(TarefaService.class);

    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public Tarefa salvar(Tarefa tarefa) {
        Tarefa tarefaSalva = tarefaRepository.save(tarefa);
        log.info("Tarefa cadastrada com ID: {}", tarefaSalva.getId());
        return tarefaSalva;
    }

    public List<Tarefa> listarTodas() {
        List<Tarefa> tarefas = tarefaRepository.findAll();
        log.info("Listagem de tarefas realizada. Total: {}", tarefas.size());
        return tarefas;
    }

    public Tarefa buscarPorId(Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Tarefa não encontrada. ID: {}", id);
                    return new TarefaNaoEncontradaException(
                            "Tarefa não encontrada com o ID: " + id);
                });
    }

    public void excluir(Long id) {
        Tarefa tarefa = buscarPorId(id);
        tarefaRepository.delete(tarefa);
        log.info("Tarefa excluída com ID: {}", id);
    }
}
