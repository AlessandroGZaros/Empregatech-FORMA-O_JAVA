import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ConexaoCarro {
    private String DB_URL = "jdbc:mysql://localhost:3306";
    private String DB_USER = "root";
    private String DB_PASSWORD = "senac@02";
    private static final String INSERT_SQL = "INSERT INTO oficina.cadastro_carros(marca, modelo, cor, ano, placa, Id_cliente) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String SELECT_SQL = "SELECT * FROM oficina.cadastro_carros";
    private static final String SELECT_INNER_SQL = "SELECT oficina.cadastro_clientes.id_cliente, oficina.cadastro_clientes.nome, oficina.cadastro_clientes.email, oficina.cadastro_clientes.cpf, oficina.cadastro_carros.marca, oficina.cadastro_carros.modelo, oficina.cadastro_carros.ano, oficina.cadastro_carros.cor, oficina.cadastro_carros.placa "
                                                    + "FROM oficina.cadastro_clientes "
                                                    + "INNER JOIN oficina.cadastro_carros "
                                                    + "ON oficina.cadastro_clientes.id_cliente = oficina.cadastro_carros.id_cliente ";
    private static final String DELETE_SQL = "DELETE FROM oficina.cadastro_carros WHERE id_carro = ?";
    private static final String UPDATE_SQL = "UPDATE oficina.cadastro_carros SET marca = ?, modelo = ?, cor = ?, ano =?, placa = ?, id_cliente = ?, WHERE id_carro = ?";

    //Método para buscar os dados na tabela do BD e armazená-los em um ArrayList.
    public ArrayList<Carro> buscarDados() {

        //Array para armazenar os dados do cliente
        ArrayList<Carro> carros = new ArrayList();


        try {
            //JDBC - java database connection
            Driver driver = new Driver();//Instanciando o driver
            DriverManager.registerDriver(driver); //Gerenciando o driver pelo DriverManager


            Connection c = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);

            PreparedStatement stmt = c.prepareStatement(SELECT_SQL);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id_carro = rs.getInt("id_carro");
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                String cor = rs.getString("cor");
                String ano = rs.getString("ano");
                String placa = rs.getString("placa");
                int id_cliente = rs.getInt("id_cliente");

                //instanciando um novo cliente
                Carro car = new Carro();

                car.setId_carro(id_carro);
                car.setMarca(marca);
                car.setModelo(modelo);
                car.setCor(cor);
                car.setAno(ano);
                car.setPlaca(placa);
                car.setId_cliente(id_cliente);

                //Adicionando o novo cliente no Array
                carros.add(car);


            }

            stmt.close();
            c.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carros;

    }


    public void buscarCarrosPorCliente(){


        try {
            //JDBC - java database connection
            Driver driver = new Driver();//Instanciando o driver
            DriverManager.registerDriver(driver); //Gerenciando o driver pelo DriverManager


            Connection c = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);

            PreparedStatement stmt = c.prepareStatement(SELECT_INNER_SQL);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id_cliente = rs.getInt("id_cliente");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String cpf = rs.getString("cpf");
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                String ano = rs.getString("ano");
                String placa = rs.getString("placa");
                int id_clientec = rs.getInt("id_cliente");

                //instanciando um novo cliente
                Carro car = new Carro();
                Cliente cli = new Cliente();

                cli.setId_cliente(id_cliente);
                cli.setNome(nome);
                cli.setEmail(email);
                cli.setCpf(cpf);
                car.setMarca(marca);
                car.setModelo(modelo);
                car.setAno(ano);
                car.setPlaca(placa);
                car.setId_cliente(id_clientec);

                if(cli.getId_cliente() == car.getId_cliente()){
                    System.out.println();
                    System.out.println("Nome: " + nome);
                    System.out.println("Marca: " + marca);
                    System.out.println("Modelo: " + modelo);
                    System.out.println("Placa: " + placa);
                    System.out.println("===========================");
                    }

            }

            stmt.close();
            c.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //Método para cadastrar um novo cliente
    public boolean inserirCarro(Carro car) {
        Scanner sc = new Scanner(System.in);
        boolean sucesso = false;

        System.out.println("Marca: ");
        car.setMarca(sc.nextLine());
        System.out.println("Modelo: ");
        car.setModelo(sc.nextLine());
        System.out.println("Cor: ");
        car.setCor(sc.nextLine());
        System.out.println("Ano: ");
        car.setAno(sc.nextLine());
        sc.nextLine();
        System.out.println("Placa: ");
        car.setPlaca(sc.nextLine());
        System.out.println("id_cliente: ");
        car.setId_cliente(sc.nextInt());

        try {
            //JDBC - java database connection
            Driver driver = new Driver();//Instanciando o driver
            DriverManager.registerDriver(driver); //Gerenciando o driver pelo DriverManager
            Connection c = DriverManager.getConnection(DB_URL ,DB_USER,DB_PASSWORD);
            PreparedStatement stmt = c.prepareStatement(INSERT_SQL);

            stmt.setString(1, car.getMarca());
            stmt.setString(2, car.getModelo());
            stmt.setString(3, car.getCor());
            stmt.setString(4, car.getAno());
            stmt.setString(5, car.getPlaca());
            stmt.setInt(6, car.getId_cliente());


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
    public boolean deletarCarro(Carro car) {
        Scanner sc = new Scanner(System.in);
        boolean sucesso = false;

        try {
            //JDBC - java database connection
            Driver driver = new Driver();//Instanciando o driver
            DriverManager.registerDriver(driver); //Gerenciando o driver pelo DriverManager
            Connection c = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            PreparedStatement stmt = c.prepareStatement(DELETE_SQL);

            stmt.setInt(1, car.getId_carro());

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
    public boolean atualizarCarro(Carro car) {
        Scanner sc = new Scanner(System.in);
        boolean sucesso = false;
        System.out.println();


        System.out.println("Marca: ");
        car.setMarca(sc.nextLine());
        System.out.println("Modelo: ");
        car.setModelo(sc.nextLine());
        System.out.println("Cor: ");
        car.setCor(sc.nextLine());
        System.out.println("Ano: ");
        car.setAno(sc.nextLine());
        sc.nextLine();
        System.out.println("Placa: ");
        car.setPlaca(sc.nextLine());
        System.out.println("id_cliente: ");
        car.setId_cliente(sc.nextInt());

        try {
            //JDBC - java database connection
            Driver driver = new Driver();//Instanciando o driver
            DriverManager.registerDriver(driver); //Gerenciando o driver pelo DriverManager
            Connection c = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            PreparedStatement stmt = c.prepareStatement(UPDATE_SQL);

            stmt.setString(1, car.getMarca());
            stmt.setString(2, car.getModelo());
            stmt.setString(3, car.getCor());
            stmt.setString(4, car.getAno());
            stmt.setString(5, car.getPlaca());
            stmt.setInt(6, car.getId_cliente());

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
    public void carroDados(Carro car){
        Scanner sc = new Scanner(System.in);
        ArrayList<Carro> carros;


        System.out.println("======= MOSTRAR CADASTRO DO CARRO =======");
        carros = buscarDados();
        for(int i =0;i < carros.size();i++){
            System.out.println("["+carros.get(i).getId_carro()+"]"+ " - " + carros.get(i).getMarca() + " - " + carros.get(i).getModelo());
        }
        System.out.print("Digite o id do cadastro que quer visualizar: ");
        car.setId_carro(sc.nextInt());;
        sc.nextLine();

        for(int i =0;i < carros.size();i++){
            if (carros.get(i).getId_carro() == (car.getId_carro())){
                System.out.println();
                System.out.println("====== DADOS CADASTRAIS DO CARRO ======");
                System.out.println("ID: " + carros.get(i).getId_carro());
                System.out.println("Marca: " + carros.get(i).getMarca());
                System.out.println("Modelo: " + carros.get(i).getModelo());
                System.out.println("Cor: " + carros.get(i).getCor());
                System.out.println("Ano: " + carros.get(i).getAno());
                System.out.println("Placa: " + carros.get(i).getPlaca());
                System.out.println("Cliente: " + carros.get(i).getId_cliente());
                System.out.println("=======================================");
                System.out.println();
            }
        }
    }

    //Método para mostrar uma lista dos clientes cadastrados
    public void carrosDados(Carro car){
        Scanner sc = new Scanner(System.in);
        ArrayList<Carro> carros;


        carros = buscarDados();

        System.out.println("====== DADOS CADASTRAIS CLIENTE ======");
        for(int i =0;i < carros.size();i++){
            System.out.println("ID: " + carros.get(i).getId_carro());
            System.out.println("Marca: " + carros.get(i).getMarca());
            System.out.println("Modelo: " + carros.get(i).getModelo());
            System.out.println("Cliente: " + carros.get(i).getId_cliente());
            System.out.println("=======================================");
        }
    }

    //Método menu cria um menu onde será chamado todos os outros métodos
    public void menu() {
        Carro car = new Carro();
        Menu menu = new Menu();
        Scanner sc = new Scanner(System.in);
        ArrayList<Carro> carros;
        int id;
        System.out.println("========== MENU ===========");
        System.out.println("[1] CADASTRAR CARRO");
        System.out.println("[2] ATUALIZAR CADASTRO CARRO");
        System.out.println("[3] MOSTRAR TODOS OS CARROS CADASTRADOS");
        System.out.println("[4] DADOS DO CARRO");
        System.out.println("[5] DELETAR CARRO CADASTRO");
        System.out.println("[6] BUSCAR CARRO/CLIENTE");
        System.out.println("[7] VOLTAR");
        int me = sc.nextInt();
        char resp= 'N';
        switch (me) {
            case 1:
                inserirCarro(car);
                break;
            case 2:
                System.out.println("======= ATUALIZAR CADASTRO CLIENTE =======");
                carros = buscarDados();
                for(int i =0;i < carros.size();i++){
                    System.out.println("["+carros.get(i).getId_carro()+"]"+ " - " + carros.get(i).getMarca() + " - " + carros.get(i).getModelo());
                }
                System.out.print("Digite o id do cadastro que quer atualizar: ");
                id = sc.nextInt();
                sc.nextLine();

                car.setId_carro(id);
                atualizarCarro(car);
                break;
            case 3:
                carrosDados(car);
                break;
            case 4:
                carroDados(car);
                break;
            case 5:
                System.out.println("======= DELETAR CADASTRO CLIENTE =======");
                carros = buscarDados();
                for(int i =0;i < carros.size();i++){
                    System.out.println("["+carros.get(i).getId_cliente()+"]"+ " - " + carros.get(i).getMarca() + " - " + carros.get(i).getModelo());
                }
                System.out.print("Digite o id do cadastro que quer deletar: ");
                id = sc.nextInt();
                sc.nextLine();

                car.setId_cliente(id);
                deletarCarro(car);
                break;
            case 6:
                buscarCarrosPorCliente();
                break;
            case 7:
                menu.menu();
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