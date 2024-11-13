package com.example.demo.service;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;

import com.example.demo.utils.JwtUtilGen;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

    @Autowired
    private JwtUtilGen jwtUtilGen ;

    // Clave secreta para cifrar/descifrar (DES usa claves de 56 bits)
    private static final String DES_ALGORITHM = "DES";
    private static final String SECRET_KEY = "12345678"; // Clave de 8 bytes para DES


    public String  exec() {
        try {
            // Ruta donde se guardará el archivo PDF
            String rutaPDF = "src/main/resources/static/pdf_example.pdf";
            String rutaPDFCifrado = "src/main/resources/static/pdf_encrypted.des";
            String rutaPDFDescifrado = "src/main/resources/static/pdf_decrypted.pdf";
            // Crear PDF
            crearPDFConTextoYUrl(rutaPDF);
            // Cifrar PDF
            cifrarArchivoDES(rutaPDF, rutaPDFCifrado);
            // Descifrar PDF
            descifrarArchivoDES(rutaPDFCifrado, rutaPDFDescifrado);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "GOOD";
    }
    /**
     * Método que crea un PDF con texto que contiene una URL.
     */
    public void crearPDFConTextoYUrl(String rutaPDF) throws Exception {
        PDDocument documento = new PDDocument();
        PDPage pagina = new PDPage();
        documento.addPage(pagina);

        PDPageContentStream contenido = new PDPageContentStream(documento, pagina);

        // Título en color azul y fuente más grande
        contenido.setNonStrokingColor(Color.BLUE);
        contenido.setFont(PDType1Font.HELVETICA_BOLD, 16);
        contenido.beginText();
        contenido.newLineAtOffset(100, 750);
        contenido.showText("Contrato Laboral");
        contenido.endText();

        // Cambiar a negro para el cuerpo del contrato
        contenido.setNonStrokingColor(Color.BLACK);
        contenido.setFont(PDType1Font.HELVETICA, 12);
        contenido.beginText();
        contenido.newLineAtOffset(100, 720);

        // Cuerpo del contrato laboral
        contenido.showText("Entre el Empleador y el Empleado se acuerda lo siguiente:");
        contenido.newLineAtOffset(0, -20);
        contenido.showText("1. El Empleado comenzará sus labores el día 01 de Enero de 2024.");
        contenido.newLineAtOffset(0, -20);
        contenido.showText("2. La jornada laboral será de 8 horas diarias, de lunes a viernes.");
        contenido.newLineAtOffset(0, -20);
        contenido.showText("3. El salario mensual será de acuerdo a lo pactado previamente.");
        contenido.newLineAtOffset(0, -20);
        contenido.showText("4. Ambas partes acuerdan un período de prueba de 3 meses.");

        // URL en rojo y en negrita para destacar como confirmación
        contenido.newLineAtOffset(0, -40);  // Espacio adicional antes de la URL
        contenido.setNonStrokingColor(Color.RED);
        contenido.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contenido.showText("Para confirmar y avanzar con el proceso de contratación, visite:");
        contenido.newLineAtOffset(0, -20);
        contenido.showText("http://localhost:8080/formV2024?ce="+jwtUtilGen.generateToken("754","ejemplo@gmail.com"));

        // Finalizar el flujo de contenido y cerrar el documento
        contenido.endText();
        contenido.close();

        // Guardar el archivo PDF en la ruta especificada
        documento.save(rutaPDF);
        documento.close();

        System.out.println("PDF de contrato laboral creado en: " + rutaPDF);
    }
    /**
     * Método que cifra un archivo usando DES.
     */
    public static void cifrarArchivoDES(String rutaArchivoOriginal, String rutaArchivoCifrado) throws Exception {
        // Leer el archivo PDF en bytes
        FileInputStream archivoOriginal = new FileInputStream(rutaArchivoOriginal);
        byte[] archivoBytes = archivoOriginal.readAllBytes();
        archivoOriginal.close();
        // Generar la clave secreta para DES
        Key clave = new SecretKeySpec(SECRET_KEY.getBytes(), DES_ALGORITHM);
        // Cifrar los bytes del archivo usando DES
        Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, clave);
        byte[] archivoCifrado = cipher.doFinal(archivoBytes);
        // Guardar el archivo cifrado
        FileOutputStream archivoEncriptado = new FileOutputStream(rutaArchivoCifrado);
        archivoEncriptado.write(archivoCifrado);
        archivoEncriptado.close();
        System.out.println("PDF cifrado guardado en: " + rutaArchivoCifrado);
    }
    /**
     * Método que descifra un archivo usando DES.
     */
    public static void descifrarArchivoDES(String rutaArchivoCifrado, String rutaArchivoDescifrado) throws Exception {
        // Leer el archivo cifrado en bytes
        FileInputStream archivoCifrado = new FileInputStream(rutaArchivoCifrado);
        byte[] archivoCifradoBytes = archivoCifrado.readAllBytes();
        archivoCifrado.close();
        // Generar la clave secreta para DES
        Key clave = new SecretKeySpec(SECRET_KEY.getBytes(), DES_ALGORITHM);
        // Descifrar los bytes del archivo usando DES
        Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, clave);
        byte[] archivoDescifrado = cipher.doFinal(archivoCifradoBytes);
        // Guardar el archivo descifrado
        FileOutputStream archivoDescifradoStream = new FileOutputStream(rutaArchivoDescifrado);
        archivoDescifradoStream.write(archivoDescifrado);
        archivoDescifradoStream.close();
        System.out.println("PDF descifrado guardado en: " + rutaArchivoDescifrado);
    }
}
    
