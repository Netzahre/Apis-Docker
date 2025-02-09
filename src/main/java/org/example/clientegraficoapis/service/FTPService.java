package org.example.clientegraficoapis.service;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.example.clientegraficoapis.session.Session;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FTPService {
        private static final String SERVER = "localhost";  // Cambia esto si tu servidor FTP está en otra dirección
        private static final int PORT = 21;
        private static final String USER = "user";
        private static final String PASSWORD = "user";
        private static final String REMOTE_DIRECTORY = "/clientsPurchases/";

        private FTPClient ftpClient;

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
                e.printStackTrace();
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
                e.printStackTrace();
            }
        }

        /**
         * Guardar una compra en el archivo FTP correspondiente
         */
        public boolean savePurchase( int numProductos, double total) {
            if (!connect()) {
                System.out.println("Error al conectar con el servidor FTP");
                return false;
            }
            String fileName = REMOTE_DIRECTORY + Session.getName() + "_compras.txt";
            String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String registro = fechaHora + " - Productos: " + numProductos + " - Total: " + total + "€\n";

            try {
                // Descargar archivo si existe
                InputStream inputStream = ftpClient.retrieveFileStream(fileName);
                StringBuilder contenido = new StringBuilder();
                if (inputStream != null) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        contenido.append(line).append("\n");
                    }
                    reader.close();
                    inputStream.close();
                    ftpClient.completePendingCommand(); // Importante para cerrar la conexión de datos
                }

                // Añadir nueva compra al contenido existente
                contenido.append(registro);

                // Subir archivo actualizado al servidor
                InputStream newInputStream = new ByteArrayInputStream(contenido.toString().getBytes());
                boolean done = ftpClient.storeFile(fileName, newInputStream);
                newInputStream.close();

                disconnect();
                return done;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }
