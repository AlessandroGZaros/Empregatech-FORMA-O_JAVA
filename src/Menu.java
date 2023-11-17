import java.util.Scanner;

public class Menu {
    Scanner sc = new Scanner(System.in);
    public void menu(){
        System.out.println("[1] BUSCAR CLIENTE");
        System.out.println("[2] BUSCAR CARRO");
        System.out.println("[3] SAIR");
        int resp = sc.nextInt();
        if(resp == 1){
            menuCliente();
        }
        else if(resp == 2){
            menuCarro();
        }
        else if (resp == 3){
            System.out.println("VOCÊ SAIU!");
        }
    }

    private void menuCliente() {
        ConexaoCliente concli = new ConexaoCliente();
        concli.menu();
    }

    private void menuCarro() {
        ConexaoCarro concar = new ConexaoCarro();
        concar.menu();
    }

}
