package serverftp;

import java.io.IOException;
import java.util.Scanner;

public class ServerFTP {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        Commandos comando = new Commandos();
        String respuesta = "";
        do {
            System.out.println("Introduzca comando: ");
            respuesta = sc.nextLine();
            if (respuesta.startsWith("connect")) {
                String[] partes = respuesta.split(" ", 4);
                comando.connectarse(partes[1], partes[2], partes[3]);
            } else
            if (respuesta.equals("list")) {
                comando.listDirectories();
            } else if (respuesta.startsWith("changePath")) {
                String[] partes = respuesta.split(" ",2);
                comando.changePath(partes[1]);
            } else if (respuesta.startsWith("down")) {
                String[] partes = respuesta.split(" ",2);
                comando.downloadFile(partes[1]);
            } else if (respuesta.startsWith("up")) {
                String[] partes = respuesta.split(" ",2);
                comando.uploadFile(partes[1]);
            } else if (respuesta.equals("disconnect")) {
                comando.disconnect();
            }
        } while (!respuesta.equals("disconnect"));

//        comando.connectarse("localhost", "Maissa", "maissa");
////       comando.listDirectories();
////       comando.changePath("");
//   // .down base2csv.txt
//        comando.changePath("/");
//        //comando.downloadFile("base2csv.txt");
//        comando.mostrarRutaAbsolutaActual();
//        comando.uploadFile("C:/Users/hp/Documents/calculadora.txt");
    }

}
