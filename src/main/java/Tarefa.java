public class Tarefa {
    private int id;
    private String descricao;
    private boolean concluida;

    public Tarefa() {
    }

    public Tarefa(String descricao) {
        this.descricao = descricao;
        this.concluida = false;
    }

    public Tarefa(int id, String descricao, boolean concluida) {
        this.id = id;
        this.descricao = descricao;
        this.concluida = concluida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    @Override
    public String toString() {
        return (concluida ? "[✔] " : "[ ] ") + descricao;
    }
}
