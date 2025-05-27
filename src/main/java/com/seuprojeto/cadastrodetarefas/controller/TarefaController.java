package com.seuprojeto.cadastrodetarefas.controller;

import com.seuprojeto.cadastrodetarefas.model.Tarefa;
import com.seuprojeto.cadastrodetarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/salvar")
    public String salvarTarefa(@ModelAttribute Tarefa tarefa) {
        tarefaRepository.save(tarefa);
        return "redirect:/tarefas/";
    }

    @GetMapping("/deletar/{id}")
    public String deletarTarefa(@PathVariable Long id) {
        tarefaRepository.deleteById(id);
        return "redirect:/tarefas/";
    }

    // Método para concluir tarefa via POST (se quiser manter)
    @PostMapping("/{id}/concluir")
    public String concluirTarefaPost(@PathVariable Long id) {
        Optional<Tarefa> tarefaOpt = tarefaRepository.findById(id);
        if (tarefaOpt.isPresent()) {
            Tarefa tarefa = tarefaOpt.get();
            tarefa.setConcluida(true);
            tarefaRepository.save(tarefa);
        }
        return "redirect:/tarefas/";
    }

    // Método para concluir tarefa via PUT
    @PutMapping("/{id}/concluir")
    public String concluirTarefaPut(@PathVariable Long id) {
        Optional<Tarefa> tarefaOpt = tarefaRepository.findById(id);
        if (tarefaOpt.isPresent()) {
            Tarefa tarefa = tarefaOpt.get();
            tarefa.setConcluida(true);
            tarefaRepository.save(tarefa);
        }
        return "redirect:/tarefas/";
    }
}
