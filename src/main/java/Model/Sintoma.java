package Model;

public class Sintoma {

    private int idSintoma;
    private String descricao;
    private ClassificacaoRisco classificacao;

    public Sintoma() {}

    public int getIdSintoma() {
        return this.idSintoma;
    }

    public String getDescricao() {
        return descricao;
    }

    public ClassificacaoRisco getClassificacao() {
        return classificacao;
    }

    public void setIdSintoma(int idSintoma) {
        this.idSintoma = idSintoma;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setClassificacao(ClassificacaoRisco classificacao) {
        this.classificacao = classificacao;
    }
}