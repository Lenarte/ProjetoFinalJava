import java.io.IOException;
import java.util.Arrays;

public class Sistema {

    private static Biblioteca biblioteca = new Biblioteca(); // Instância da Biblioteca para gerenciar livros, usuários e empréstimos

    // Método para exibir o menu principal do sistema
    private static void exibirMenu() {
        System.out.println("\nSistema de Biblioteca");
        System.out.println("1) Cadastrar Livro");
        System.out.println("2) Buscar Livro");
        System.out.println("3) Atualizar Dados do Livro");
        System.out.println("4) Apagar Livro");
        System.out.println("5) Listar Todos os Livros");
        System.out.println("6) Emprestar Livro");
        System.out.println("7) Listar Livros Emprestados ");
        System.out.println("8) Cadastrar Usuário");
        System.out.println("9) Listar Usuários");
        System.out.println("10) Atualizar Dados do Usuário");
        System.out.println("11) Devolver Livro ");
        System.out.println("0) Sair");
    }

    // Método para cadastrar um novo livro na biblioteca
    private static void cadastrarLivro() {
        System.out.println("\nNovo Livro:");
        String titulo = Console.lerString("Título do livro:");
        String autorNome = Console.lerString("Nome do autor:");
        String categoriaNome = Console.lerString("Categoria do livro:");

        Autor autor = new Autor(autorNome);
        Categoria categoria = new Categoria(categoriaNome);

        Livro livro = new Livro(titulo, Arrays.asList(autor), Arrays.asList(categoria)); // Criação do livro com autor e categoria
        biblioteca.adicionarLivro(livro);

        try {
            biblioteca.salvarParaArquivo("biblioteca.txt"); // Salva as alterações no arquivo
            System.out.println("\nLivro foi salvo com sucesso!");
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    // Método para cadastrar um novo usuário na biblioteca
    private static void cadastrarUsuario() {
        System.out.println("\nNovo Usuário:");
        String nome = Console.lerString("Nome do usuário:");
        String email = Console.lerString("Email do usuário:");
        String telefone = Console.lerString("Telefone do usuário:");

        Usuario usuario = new Usuario(nome, email, telefone); // Criação do usuário
        biblioteca.adicionarUsuario(usuario);

        try {
            biblioteca.salvarParaArquivo("biblioteca.txt"); // Salva as alterações no arquivo
            System.out.println("\nUsuário foi salvo com sucesso!");
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    // Método para buscar, editar ou apagar um livro na biblioteca
    private static void buscarLivro(int modo) {
        try {
            if (biblioteca.getLivros().isEmpty()) {
                throw new Exception("Nenhum livro cadastrado.");
            }

            String titulo = Console.lerString("Informe o título:");
            final String tituloBusca = titulo;

            Livro busca = biblioteca.getLivros().stream()
                    .filter(livro -> livro.getTitulo().equalsIgnoreCase(tituloBusca))
                    .findFirst()
                    .orElseThrow(() -> new Exception("Livro não encontrado."));

            switch (modo) {
                case 3: // Apagar livro
                    biblioteca.getLivros().remove(busca);
                    biblioteca.salvarParaArquivo("biblioteca.txt");
                    System.out.println("\nLivro " + busca.getTitulo() + " excluído com sucesso!");
                    break;

                case 2: // Editar dados do livro
                    System.out.println("\nInforme os novos dados:");

                    String novoTitulo = Console.lerString("Novo título do livro:");
                    String novoAutorNome = Console.lerString("Novo nome do autor:");
                    String novaCategoriaNome = Console.lerString("Nova categoria do livro:");

                    busca.setTitulo(novoTitulo);
                    busca.setAutores(Arrays.asList(new Autor(novoAutorNome)));
                    busca.setCategorias(Arrays.asList(new Categoria(novaCategoriaNome)));

                    biblioteca.salvarParaArquivo("biblioteca.txt");
                    System.out.println("\nLivro atualizado: " + busca);
                    break;

                case 1: // Exibir dados do livro
                default:
                    System.out.println("\nLivro localizado: " + busca);
                    break;
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    // Método para listar todos os livros cadastrados na biblioteca
    private static void listarTodos() {
        try {
            if (biblioteca.getLivros().isEmpty()) {
                throw new Exception("Nenhum livro cadastrado.");
            }

            System.out.println("\nLivros Cadastrados:");
            for (Livro livro : biblioteca.getLivros()) {
                System.out.println(livro);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    // Método para realizar o empréstimo de um livro para um usuário
    private static void emprestarLivro() {
        try {
            if (biblioteca.getLivros().isEmpty()) {
                throw new Exception("Nenhum livro cadastrado.");
            }

            String titulo = Console.lerString("Informe o título do livro:");
            String nomeUsuario = Console.lerString("Nome do usuário:");

            Livro livro = biblioteca.getLivros().stream()
                    .filter(l -> l.getTitulo().equalsIgnoreCase(titulo))
                    .findFirst()
                    .orElseThrow(() -> new Exception("Livro não encontrado."));

            Usuario usuario = biblioteca.getUsuarios().stream()
                    .filter(u -> u.getNome().equalsIgnoreCase(nomeUsuario))
                    .findFirst()
                    .orElseThrow(() -> new Exception("Usuário não encontrado."));

            biblioteca.emprestarLivro(livro, usuario);
            biblioteca.salvarParaArquivo("biblioteca.txt");
            System.out.println("\nLivro emprestado com sucesso!");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    // Método para listar todos os livros atualmente emprestados
    private static void listarLivrosEmprestados() {
        try {
            if (biblioteca.getEmprestimos().isEmpty()) {
                throw new Exception("Nenhum livro emprestado.");
            }
    
            System.out.println("\nLivros Emprestados:");
            for (Emprestimo emprestimo : biblioteca.getEmprestimos()) {
                System.out.println("Livro: " + emprestimo.getLivro().getTitulo() +
                                   ", Usuário: " + emprestimo.getUsuario().getNome() +
                                   ", Data de Empréstimo: " + emprestimo.getDataEmprestimo());
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    // Método para devolver um livro emprestado à biblioteca
    private static void devolverLivro() {
        try {
            if (biblioteca.getEmprestimos().isEmpty()) {
                throw new Exception("Nenhum empréstimo registrado.");
            }

            String titulo = Console.lerString("Informe o título do livro:");
            String nomeUsuario = Console.lerString("Nome do usuário:");

            Emprestimo emprestimo = biblioteca.getEmprestimos().stream()
                    .filter(e -> e.getLivro().getTitulo().equalsIgnoreCase(titulo) &&
                            e.getUsuario().getNome().equalsIgnoreCase(nomeUsuario))
                    .findFirst()
                    .orElseThrow(() -> new Exception("Empréstimo não encontrado."));

            biblioteca.devolverLivro(emprestimo);
            biblioteca.salvarParaArquivo("biblioteca.txt");
            System.out.println("\nLivro devolvido com sucesso!");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    // Método para listar todos os usuários cadastrados na biblioteca
    private static void listarUsuarios() {
        try {
            if (biblioteca.getUsuarios().isEmpty()) {
                throw new Exception("Nenhum usuário cadastrado.");
            }

            System.out.println("\nUsuários Cadastrados:");
            for (Usuario usuario : biblioteca.getUsuarios()) {
                System.out.println(usuario);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    // Método para atualizar os dados de um usuário já cadastrado na biblioteca
    private static void atualizarDadosUsuario() {
        try {
            if (biblioteca.getUsuarios().isEmpty()) {
                throw new Exception("Nenhum usuário cadastrado.");
            }

            String nomeUsuario = Console.lerString("Informe o nome do usuário:");
            Usuario usuario = biblioteca.getUsuarios().stream()
                    .filter(u -> u.getNome().equalsIgnoreCase(nomeUsuario))
                    .findFirst()
                    .orElseThrow(() -> new Exception("Usuário não encontrado."));

            System.out.println("\nInforme os novos dados:");
            String novoNome = Console.lerString("Novo nome do usuário:");
            String novoEmail = Console.lerString("Novo email do usuário:");
            String novoTelefone = Console.lerString("Novo telefone do usuário:");

            usuario.setNome(novoNome);
            usuario.setEmail(novoEmail);
            usuario.setTelefone(novoTelefone);

            biblioteca.salvarParaArquivo("biblioteca.txt");
            System.out.println("\nDados do usuário atualizados com sucesso!");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    // Método para verificar a opção escolhida pelo usuário e chamar o método correspondente
    private static void verificarOpcaoMenu(int opcao) {
        switch (opcao) {
            case 1:
                cadastrarLivro();
                break;
            case 2:
                buscarLivro(1); // Exibir livro
                break;
            case 3:
                buscarLivro(2); // Editar livro
                break;
            case 4:
                buscarLivro(3); // Apagar livro
                break;
            case 5:
                listarTodos();
                break;
            case 6:
                emprestarLivro();
                break;
            case 7:
                listarLivrosEmprestados();
                break;
            case 8:
                cadastrarUsuario();
                break;
            case 9:
                listarUsuarios();
                break;
            case 10:
                atualizarDadosUsuario();
                break;
            case 11:
                devolverLivro();
                break;
            case 0:
                System.out.println("\nSistema finalizado...\n");
                System.exit(0);
                break;
            default:
                System.out.println("\nOpção inválida. Digite novamente.");
                break;
        }
    }

    // Método principal que executa o sistema de biblioteca
    public static void executar() {
        try {
            biblioteca.carregarDeArquivo("biblioteca.txt"); // Carrega dados salvos anteriormente
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        // Loop principal que mantém o sistema em execução até que o usuário escolha sair
        while (true) {
            exibirMenu(); // Mostra o menu de opções
            int opcao = Console.lerInt("Informe uma opção:"); // Solicita uma opção do usuário
            verificarOpcaoMenu(opcao); // Verifica a opção e executa a função correspondente
        }
    }
}
