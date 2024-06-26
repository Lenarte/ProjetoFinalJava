import java.io.*;
import java.util.*;

public class Biblioteca implements Persistente {

    // Listas para armazenar livros, usuários e empréstimos
    private List<Livro> livros = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Emprestimo> emprestimos = new ArrayList<>();

    // Método para adicionar um livro à lista de livros
    public void adicionarLivro(Livro livro) {
        livros.add(livro);
    }

    // Método para adicionar um usuário à lista de usuários
    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    // Método para realizar o empréstimo de um livro a um usuário
    public void emprestarLivro(Livro livro, Usuario usuario) throws Exception {
        // Verifica se o livro está disponível para empréstimo
        if (!livro.isDisponivel()) {
            throw new Exception("Livro não disponível para empréstimo.");
        }
        // Cria um novo empréstimo e adiciona à lista de empréstimos
        Emprestimo emprestimo = new Emprestimo(livro, usuario);
        emprestimos.add(emprestimo);
        // Define o livro como não disponível após o empréstimo
        livro.setDisponivel(false);
    }

    // Método para devolver um livro previamente emprestado
    public void devolverLivro(Emprestimo emprestimo) {
        // Define o livro associado ao empréstimo como disponível
        emprestimo.getLivro().setDisponivel(true);
        // Remove o empréstimo da lista de empréstimos
        emprestimos.remove(emprestimo);
    }

    // Métodos para obter listas de livros, usuários e empréstimos
    public List<Livro> getLivros() {
        return livros;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    // Implementação do método de interface para salvar dados em arquivo
    @Override
    public void salvarParaArquivo(String caminho) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            // Salva todos os livros no arquivo
            for (Livro livro : livros) {
                writer.write(livro.toString());
                writer.newLine();
            }
            // Salva todos os usuários no arquivo
            for (Usuario usuario : usuarios) {
                writer.write(usuario.toString());
                writer.newLine();
            }
        }
    }

    // Implementação do método de interface para carregar dados de arquivo
    @Override
    public void carregarDeArquivo(String caminho) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            // Lê cada linha do arquivo
            while ((linha = reader.readLine()) != null) {
                // Verifica se a linha representa um livro
                if (linha.startsWith("Livro")) {
                    try {
                        // Converte a linha em um objeto Livro e adiciona à lista de livros
                        Livro livro = Livro.fromString(linha);
                        livros.add(livro);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erro ao carregar linha de livro: " + e.getMessage());
                    }
                } 
                // Verifica se a linha representa um usuário
                else if (linha.startsWith("Nome")) {
                    try {
                        // Extrai informações de nome, email e telefone e cria um objeto Usuario
                        String[] partes = linha.split(",");
                        String nome = partes[0].substring(partes[0].indexOf(":") + 1).trim();
                        String email = partes[1].substring(partes[1].indexOf(":") + 1).trim();
                        String telefone = partes[2].substring(partes[2].indexOf(":") + 1).trim();
                        Usuario usuario = new Usuario(nome, email, telefone);
                        usuarios.add(usuario);
                    } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
                        System.out.println("Erro ao carregar linha de usuário: " + e.getMessage());
                    }
                }
            }
        }
    }
}
