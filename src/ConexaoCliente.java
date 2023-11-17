import java.sql.*;

import java.util.ArrayList;
import java.util.Scanner;
import com.mysql.cj.jdbc.Driver;



public class ConexaoCliente {
    private String DB_URL = "jdbc:mysql://localhost:3306";
    private String DB_USER = "root";
    private String DB_PASSWORD = "senac@02";
    private  static final String INSERT_SQL = "INSERT INTO oficina.cadastro_clientes(nome,email,cpf,endereco,numero,cidade,estado) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_SQL = "SELECT * FROM oficina.cadastro_clientes";
    private static final String DELETE_SQL = "DELETE FROM oficina.cadastro_clientes WHERE id_cliente = ?";
    private static final String UPDATE_SQL = "UPDATE oficina.cadastro_clientes SET nome = ?, email = ?, cpf = ?, endereco =?, numero = ?, cidade = ?, estado = ? WHERE id_cliente = ?";

    //Método para buscar os dados na tabela do BD e armazená-los em um ArrayList.
    public ArrayList<Cliente> buscarDados() {

        //Array para armazenar os dados do cliente
        ArrayList<Cliente> cliente = new ArrayList();


        try {
            //JDBC - java database connection
            Driver driver = new Driver();//Instanciando o driver
            DriverManager.registerDriver(driver); //Gerenciando o driver pelo DriverManager


            Connection c = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);

            PreparedStatement stmt = c.prepareStatement(SELECT_SQL);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id_cliente = rs.getInt("id_cliente");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String cpf = rs.getString("cpf");
                String endereco = rs.getString("endereco");
                int numero = rs.getInt("numero");
                String cidade = rs.getString("cidade");
                String estado = rs.getString("estado");

                //instanciando um novo cliente
                Cliente cli = new Cliente();

                cli.setId_cliente(id_cliente);
                cli.setNome(nome);
                cli.setEmail(email);
                cli.setCpf(cpf);
                cli.setEndereco(endereco);
                cli.setNumero(numero);
                cli.setCidade(cidade);
                cli.setEstado(estado);

                //Adicionando o novo cliente no Array
                cliente.add(cli);


            }

            stmt.close();
            c.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;//retorna o Array cliente
    }

    //Método para cadastrar um novo cliente
    public boolean inserirDados(Cliente cli) {
        Scanner sc = new Scanner(System.in);
        boolean sucesso = false;

        System.out.println("Nome: ");
        cli.setNome(sc.nextLine());
        System.out.println("email: ");
        cli.setEmail(sc.nextLine());
        System.out.println("CPF: ");
        cli.setCpf(sc.nextLine());
        System.out.println("Endereço: ");
        cli.setEndereco(sc.nextLine());
        System.out.println("Numero: ");
        cli.setNumero(sc.nextInt());
        sc.nextLine();
        System.out.println("Cidade: ");
        cli.setCidade(sc.nextLine());
        System.out.println("Estado: ");
        cli.setEstado(sc.nextLine());

        try {
            //JDBC - java database connection
            Driver driver = new Driver();//Instanciando o driver
            DriverManager.registerDriver(driver); //Gerenciando o driver pelo DriverManager
            Connection c = DriverManager.getConnection(DB_URL ,DB_USER,DB_PASSWORD);
            PreparedStatement stmt = c.prepareStatement(INSERT_SQL);

            stmt.setString(1, cli.getNome());
            stmt.setString(2, cli.getEmail());
            stmt.setString(3, cli.getCpf());
            stmt.setString(4, cli.getEndereco());
            stmt.setInt(5, cli.getNumero());
            stmt.setString(6, cli.getCidade());
            stmt.setString(7, cli.getEstado());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                sucesso = true;
            }

            stmt.close();
            c.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sucesso;
    }

    //Método para deletar determinado cadastro
    public boolean deletarDados(Cliente cli) {
        Scanner sc = new Scanner(System.in);
        boolean sucesso = false;

        try {
            //JDBC - java database connection
            Driver driver = new Driver();//Instanciando o driver
            DriverManager.registerDriver(driver); //Gerenciando o driver pelo DriverManager
            Connection c = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            PreparedStatement stmt = c.prepareStatement(DELETE_SQL);

            stmt.setInt(1, cli.getId_cliente());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                sucesso = true;
            }
            stmt.executeUpdate();


            stmt.close();
            c.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  sucesso;
    }

    //método para atualizar os dados cadastrais de um cliente
    public boolean atualizarDados(Cliente cli) {
        Scanner sc = new Scanner(System.in);
        boolean sucesso = false;
        System.out.println();


        System.out.println("Nome: ");
        cli.setNome(sc.nextLine());
        System.out.println("email: ");
        cli.setEmail(sc.nextLine());
        System.out.println("CPF: ");
        cli.setCpf(sc.nextLine());
        System.out.println("Endereço: ");
        cli.setEndereco(sc.nextLine());
        System.out.println("Numero: ");
        cli.setNumero(sc.nextInt());
        sc.nextLine();
        System.out.println("Cidade: ");
        cli.setCidade(sc.nextLine());
        System.out.println("Estado: ");
        cli.setEstado(sc.nextLine());
        System.out.println();

        try {
            //JDBC - java database connection
            Driver driver = new Driver();//Instanciando o driver
            DriverManager.registerDriver(driver); //Gerenciando o driver pelo DriverManager
            Connection c = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            PreparedStatement stmt = c.prepareStatement(UPDATE_SQL);

            stmt.setString(1, cli.getNome());
            stmt.setString(2, cli.getEmail());
            stmt.setString(3, cli.getCpf());
            stmt.setString(4, cli.getEndereco());
            stmt.setInt(5, cli.getNumero());
            stmt.setString(6, cli.getCidade());
            stmt.setString(7, cli.getEstado());
            stmt.setInt(8, cli.getId_cliente());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                sucesso = true;
                System.out.println("dados alterados");
            }


            stmt.close();
            c.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sucesso;
    }

    //Método para mostrar o cadastro completo de determinado cliente
    public void clienteDados(Cliente cli){
        Scanner sc = new Scanner(System.in);
        ArrayList<Cliente> cliente;

        System.out.println("======= MOSTRAR CADASTRO CLIENTE =======");
        cliente = buscarDados();
        for(int i =0;i < cliente.size();i++){
            System.out.println("["+cliente.get(i).getId_cliente()+"]"+ " - " + cliente.get(i).getNome());
        }
        System.out.print("Digite o id do cadastro que quer visualizar: ");
        cli.setId_cliente(sc.nextInt());;
        sc.nextLine();

        for(int i =0;i < cliente.size();i++){
            if (cliente.get(i).getId_cliente() == (cli.getId_cliente())){
                System.out.println();
                System.out.println("====== DADOS CADASTRAIS CLIENTE ======");
                System.out.println("ID: " + cliente.get(i).getId_cliente());
                System.out.println("Nome: " + cliente.get(i).getNome());
                System.out.println("email: " + cliente.get(i).getEmail());
                System.out.println("CPF " + cliente.get(i).getCpf());
                System.out.println("Endereço: " + cliente.get(i).getEndereco());
                System.out.println("Numero: " + cliente.get(i).getNumero());
                System.out.println("Cidade: " + cliente.get(i).getCidade());
                System.out.println("Estado: " + cliente.get(i).getEstado());
                System.out.println("=======================================");
                System.out.println();
            }
        }
    }

    //Método para mostrar uma lista dos clientes cadastrados
    public void clientesDados(Cliente cli){
        Scanner sc = new Scanner(System.in);
        ArrayList<Cliente> cliente;


        cliente = buscarDados();

        System.out.println("====== DADOS CADASTRAIS CLIENTE ======");
        for(int i =0;i < cliente.size();i++){
                System.out.println("ID: " + cliente.get(i).getId_cliente());
                System.out.println("Nome: " + cliente.get(i).getNome());
                System.out.println("=======================================");
        }
    }

    //Método menu cria um menu onde será chamado todos os outros métodos
    public void menu() {
        Cliente cli = new Cliente();
        Menu menu = new Menu();
        Scanner sc = new Scanner(System.in);
        ArrayList<Cliente> cliente;
        int id;
            System.out.println("========== MENU ===========");
            System.out.println("[1] CADASTRAR CLIENTE");
            System.out.println("[2] ATUALIZAR CADASTRO");
            System.out.println("[3] MOSTRAR TODOS OS CLIENTES");
            System.out.println("[4] DADOS DO CLIENTE");
            System.out.println("[5] DELETAR CADASTRO");
            System.out.println("[6] VOLTAR");
            int me = sc.nextInt();
            char resp= 'N';
            switch (me) {
                case 1:
                    inserirDados(cli);
                    break;
                case 2:
                    System.out.println("======= ATUALIZAR CADASTRO CLIENTE =======");
                    cliente = buscarDados();
                    for(int i =0;i < cliente.size();i++){
                        System.out.println("["+cliente.get(i).getId_cliente()+"]"+ " - " + cliente.get(i).getNome());
                    }
                    System.out.print("Digite o id do cadastro que quer atualizar: ");
                    id = sc.nextInt();
                    sc.nextLine();

                    cli.setId_cliente(id);
                    atualizarDados(cli);
                    break;
                case 3:
                    clientesDados(cli);
                    break;
                case 4:
                    clienteDados(cli);
                    break;
                case 5:
                    System.out.println("======= DELETAR CADASTRO CLIENTE =======");
                    cliente = buscarDados();
                    for(int i =0;i < cliente.size();i++){
                        System.out.println("["+cliente.get(i).getId_cliente()+"]"+ " - " + cliente.get(i).getNome());
                    }
                    System.out.print("Digite o id do cadastro que quer deletar: ");
                    id = sc.nextInt();
                    sc.nextLine();

                    cli.setId_cliente(id);
                    deletarDados(cli);
                    break;
                case 6:
                    menu.menu();
                    System.out.println("VOCÊ SAIU.");

                    break;
                default:
                    System.out.println("OPÇÃO INVÁLIDA. TENTE NOVAMENTE.");
            }

            System.out.print("Voltar ao MENU [S/N]");
            resp = sc.next().charAt(0);
            if(resp == 'S' || resp == 's'){
                menu();
            } else if (resp == 'N' || resp == 'n') {
                System.out.println("VOCÊ SAIU");
            }

    }
}

