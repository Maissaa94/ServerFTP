package serverftp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class Commandos {

    FTPClient cliente = new FTPClient();

    public Commandos() {
    }

    public void connectarse(String server, String usuario, String clave) {
        try {
            cliente.connect(server);
            cliente.enterLocalPassiveMode(); //modo pasivo
            boolean login = cliente.login(usuario, clave);
            if (login) {
                System.out.println("Login correcto...");

            } else {
                System.out.println("Login Incorrecto...");
                cliente.disconnect();
                System.exit(1);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerFTP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listDirectories() throws IOException {
        if (cliente.isConnected()) {
            FTPFile[] files = cliente.listFiles();
            for (FTPFile f : files) {
                System.out.println(f.getName());
            }
            mostrarRutaAbsolutaActual();
        } else {
            System.out.println("La conexión no está abierta.");
        }
    }

  public void mostrarRutaAbsolutaActual() throws IOException {
    if (cliente.isConnected()) {
        System.out.println(cliente.printWorkingDirectory());
    } else {
        System.out.println("No estás conectado a un servidor.");
    }
}

  public void changePath(String ruta) throws IOException {
    if (cliente.isConnected()) {
        boolean b = cliente.changeWorkingDirectory(ruta); // devuelve true si se ha cambiado la ruta 
        System.out.println(b);
        if (b) {
            listDirectories();
        } else {
            System.out.println("Error: directorio no encontrado.");
        }
        System.out.println("Estoy ahora en:");
        mostrarRutaAbsolutaActual();
    } else {
        System.out.println("No estás conectado a un servidor.");
    }
}


    public void downloadFile(String archivo) throws IOException {
        if (cliente.isConnected()) {
            try {
                cliente.changeWorkingDirectory(archivo);
                String rutaCompletaLocal = "downloads/" + archivo;
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(rutaCompletaLocal));

                if (cliente.retrieveFile(archivo, out)) {
                    System.out.println("archivo descarcado");
                } else {
                    System.out.println("No se ha podido descargar el fichero");
                }
                mostrarRutaAbsolutaActual();
                out.close();
            } catch (IOException e) {
                System.out.println("Error al subir el fichero: " + e.getMessage());
            }
        } else {
            System.out.println("No estás conectado a un servidor.");
        }
    }

  public void uploadFile(String archivo) throws IOException {
    if (cliente.isConnected()) {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(archivo));
        String[] nombreMiFichero = archivo.split("/");
        
        try {
            if (cliente.storeFile(nombreMiFichero[nombreMiFichero.length - 1], in)) {
                System.out.println("Subido correctamente... ");
            } else {
                System.out.println("No se ha podido subir el fichero... ");
            }
        } finally {
            in.close();
        }
    } else {
        System.out.println("No estás conectado a un servidor.");
    }
}

    public void disconnect() {
        if (cliente.isConnected()) {
            try {
                cliente.logout();
                cliente.disconnect();
                System.out.println("Desconectado.");
                System.exit(0);
            } catch (IOException e) {
                System.out.println("Error al desconectar: " + e.getMessage());
            }
        } else {
            System.out.println("No estás conectado a un servidor.");
            System.exit(0);
        }
    }

}
