import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {

    public void adicionarTarefa(Tarefa tarefa) {
        String sql = "INSERT INTO tarefas (descricao, concluida) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tarefa.getDescricao());
            stmt.setBoolean(2, tarefa.isConcluida());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Tarefa> listarTarefas() {
        List<Tarefa> tarefas = new ArrayList<>();
        String sql = "SELECT * FROM tarefas";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Tarefa tarefa = new Tarefa();
                tarefa.setId(rs.getInt("id"));
                tarefa.setDescricao(rs.getString("descricao"));
                tarefa.setConcluida(rs.getBoolean("concluida"));
                tarefas.add(tarefa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tarefas;
    }

    public void excluirTarefa(int id) {
        String sql = "DELETE FROM tarefas WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void marcarComoConcluida(int id, boolean concluida) {
        String sql = "UPDATE tarefas SET concluida = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, concluida);
            stmt.setInt(2, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
