package org.example.clientegraficoapis.service;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.example.clientegraficoapis.session.Session;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FTPService {
        private static final String SERVER = "127.0.0.1";
        private static final int PORT = 21;
        private static final String USER = "user";
        private static final String PASSWORD = "user";
        private static final String REMOTE_DIRECTORY = "/purchases";

        private final FTPClient ftpClient;

        public FTPService() {
            ftpClient = new FTPClient();
        }

        /**
         * Conectar al servidor FTP
         */
        public boolean connect() {
            try {
                ftpClient.connect(SERVER, PORT);
                ftpClient.login(USER, PASSWORD);
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                return true;
            } catch (IOException e) {
                System.out.println("Error al conectar con el servidor FTP" + e.getMessage());
                return false;
            }
        }

        /**
         * Desconectar del servidor FTP
         */
        public void disconnect() {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                System.out.println("Error al desconectar del servidor FTP" + e.getMessage());
            }
        }

        /**
         * Guardar una compra en el archivo FTP correspondiente
         */
        public boolean savePurchase(int numProductos, double total) {
            if (!connect()) {
                System.out.println("Error al conectar con el servidor FTP");
                return false;
            }
            System.out.println("Ha conectado al servidor FTP");

            String fileName = REMOTE_DIRECTORY + "_" + Session.getName() + "_compras.txt";
            String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String registro = fechaHora + " - Productos: " + numProductos + " - Total: " + total + "€\n";

            StringBuilder contenido = new StringBuilder();

            try {
                InputStream inputStream = ftpClient.retrieveFileStream(fileName);
                if (inputStream != null) {
                    System.out.println("El archivo existe, leyendo su contenido...");
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = br.readLine()) != null) {
                        contenido.append(line).append("\n");
                    }
                    br.close();
                    inputStream.close();
                    ftpClient.completePendingCommand();
                } else {
                    System.out.println("El archivo no existe, se creará uno nuevo.");
                }

                contenido.append(registro);

                InputStream newInputStream = new ByteArrayInputStream(contenido.toString().getBytes(StandardCharsets.UTF_8));

                boolean done = ftpClient.storeFile(fileName, newInputStream);
                newInputStream.close();

                if (done) {
                    System.out.println("Archivo actualizado/subido exitosamente.");
                } else {
                    System.out.println("Fallo al subir el archivo.");
                }
                disconnect();
                return done;
            } catch (IOException e) {
                System.out.println("Error al guardar la compra en el servidor FTP" + e.getMessage());
                return false;
            }
        }

}
