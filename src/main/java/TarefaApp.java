import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TarefaApp extends JFrame {

    private final DefaultListModel<Tarefa> listaModel;
    private JList<Tarefa> listaTarefas;
    private JTextField campoTarefa;
    private TarefaDAO tarefaDAO;

    public TarefaApp() {
        super("Cadastro de Tarefas");

        tarefaDAO = new TarefaDAO();
        listaModel = new DefaultListModel<>();
        listaTarefas = new JList<>(listaModel);
        campoTarefa = new JTextField(25);

        JButton botaoAdicionar = new JButton("Adicionar");
        JButton botaoExcluir = new JButton("Excluir");
        JButton botaoConcluir = new JButton("Marcar como Concluída");

        // Layout
        JPanel painelInput = new JPanel();
        painelInput.add(campoTarefa);
        painelInput.add(botaoAdicionar);

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(botaoConcluir);
        painelBotoes.add(botaoExcluir);

        add(painelInput, BorderLayout.NORTH);
        add(new JScrollPane(listaTarefas), BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        // Ações
        botaoAdicionar.addActionListener(e -> {
            String texto = campoTarefa.getText().trim();
            if (!texto.isEmpty()) {
                Tarefa nova = new Tarefa(texto);
                tarefaDAO.adicionarTarefa(nova);
                campoTarefa.setText("");
                atualizarLista();
            }
        });

        botaoExcluir.addActionListener(e -> {
            Tarefa selecionada = listaTarefas.getSelectedValue();
            if (selecionada != null) {
                tarefaDAO.excluirTarefa(selecionada.getId());
                atualizarLista();
            }
        });

        botaoConcluir.addActionListener(e -> {
            Tarefa selecionada = listaTarefas.getSelectedValue();
            if (selecionada != null) {
                boolean novaSituacao = !selecionada.isConcluida();
                tarefaDAO.marcarComoConcluida(selecionada.getId(), novaSituacao);
                atualizarLista();
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);

        atualizarLista();
    }

    private void atualizarLista() {
        listaModel.clear();
        List<Tarefa> tarefas = tarefaDAO.listarTarefas();
        for (Tarefa t : tarefas) {
            listaModel.addElement(t);
        }
    }

    // Método main para iniciar o aplicativo
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TarefaApp());
    }
}
