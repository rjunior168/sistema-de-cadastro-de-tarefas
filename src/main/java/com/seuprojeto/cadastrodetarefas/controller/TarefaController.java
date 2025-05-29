package com.seuprojeto.cadastrodetarefas.controller;

import com.seuprojeto.cadastrodetarefas.model.Status;
import com.seuprojeto.cadastrodetarefas.model.Tarefa;
import com.seuprojeto.cadastrodetarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;

    @GetMapping("/")
    public String listarTarefas(Model model) {
        model.addAttribute("tarefas", tarefaRepository.findAll());
        return "index";
    }

    @GetMapping("/editar/{id}")
    public String editarTarefa(@PathVariable Long id, Model model) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        if (tarefa.isPresent()) {
            model.addAttribute("tarefa", tarefa.get());
            model.addAttribute("statuses", Status.values());
            return "formulario";
        } else {
            return "redirect:/tarefas/";
        }
    }

    @PostMapping("/salvar")
    public String salvarTarefa(@ModelAttribute Tarefa tarefa) {
        if (tarefa.getStatus() == null) {
            tarefa.setStatus(Status.PENDENTE);
        }
        tarefaRepository.save(tarefa);
        return "redirect:/tarefas/";
    }

    @GetMapping("/deletar/{id}")
    public String deletarTarefa(@PathVariable Long id) {
        tarefaRepository.deleteById(id);
        return "redirect:/tarefas/";
    }

    @PostMapping("/{id}/concluir")
    public String concluirTarefaPost(@PathVariable Long id) {
        Optional<Tarefa> tarefaOpt = tarefaRepository.findById(id);
        if (tarefaOpt.isPresent()) {
            Tarefa tarefa = tarefaOpt.get();
            tarefa.setConcluida(true);
            tarefa.setStatus(Status.CONCLUIDA);
            tarefaRepository.save(tarefa);
        }
        return "redirect:/tarefas/";
    }

    @PutMapping("/{id}/concluir")
    public String concluirTarefaPut(@PathVariable Long id) {
        Optional<Tarefa> tarefaOpt = tarefaRepository.findById(id);
        if (tarefaOpt.isPresent()) {
            Tarefa tarefa = tarefaOpt.get();
            tarefa.setConcluida(true);
            tarefa.setStatus(Status.CONCLUIDA);
            tarefaRepository.save(tarefa);
        }
        return "redirect:/tarefas/";
    }

    // ✅ NOVO ENDPOINT para atualizar status via JSON
    @PutMapping("/{id}/status")
    @ResponseBody
    public String atualizarStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        Optional<Tarefa> tarefaOpt = tarefaRepository.findById(id);
        if (tarefaOpt.isPresent()) {
            Tarefa tarefa = tarefaOpt.get();
            try {
                Status novoStatus = Status.valueOf(payload.get("status"));
                tarefa.setStatus(novoStatus);
                tarefaRepository.save(tarefa);
                return "Status atualizado com sucesso";
            } catch (IllegalArgumentException e) {
                return "Status inválido";
            }
        }
        return "Tarefa não encontrada";
    }
}
