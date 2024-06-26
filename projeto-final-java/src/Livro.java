import java.util.List;
import java.util.Arrays;

public class Livro implements Emprestavel {
    private String titulo; // Título do livro
    private List<Autor> autores; // Lista de autores do livro
    private List<Categoria> categorias; // Lista de categorias do livro
    private boolean disponivel; // Indica se o livro está disponível para empréstimo

    // Construtor da classe Livro
    public Livro(String titulo, List<Autor> autores, List<Categoria> categorias) {
        this.titulo = titulo;
        this.autores = autores;
        this.categorias = categorias;
        this.disponivel = true; // Por padrão, o livro está disponível ao ser criado
    }

    // Métodos getters e setters para acessar e modificar os atributos do livro

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    // Representação em string do objeto Livro
    @Override
    public String toString() {
        return "Livro / " + "titulo:" + titulo + ", autor:" + autores + ", categoria:" + categorias + ", disponivel:" + disponivel;
    }

    // Método estático para criar um objeto Livro a partir de uma string formatada
    public static Livro fromString(String linha) {
        String[] partes = linha.split(", ");
        if (partes.length < 4) {
            throw new IllegalArgumentException("Linha inválida: " + linha);
        }
        String titulo = partes[0]; // Extrai o título do livro
        List<Autor> autores = Arrays.asList(new Autor(partes[1])); // Cria lista de autores a partir da linha
        List<Categoria> categorias = Arrays.asList(new Categoria(partes[2])); // Cria lista de categorias a partir da linha
        return new Livro(titulo, autores, categorias); // Retorna um novo objeto Livro com os dados extraídos
    }
}

