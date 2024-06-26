import java.io.IOException;

public interface Persistente {
    void salvarParaArquivo(String caminho) throws IOException;
    void carregarDeArquivo(String caminho) throws IOException;
}
