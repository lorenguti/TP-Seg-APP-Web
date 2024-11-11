import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.security.Key;
import java.util.Base64;

public class decrypt {

    private static final String DES_ALGORITHM = "DES";

    public static void descifrarArchivoDES(String rutaArchivoCifrado, String rutaArchivoDescifrado, String clave) throws Exception {
        // Leer el archivo cifrado en bytes
        FileInputStream archivoCifrado = new FileInputStream(rutaArchivoCifrado);
        byte[] archivoCifradoBytes = archivoCifrado.readAllBytes();
        archivoCifrado.close();

        // Generar la clave secreta para DES
        Key secretKey = new SecretKeySpec(clave.getBytes(), DES_ALGORITHM);

        // Descifrar los bytes del archivo usando DES
        Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] archivoDescifrado = cipher.doFinal(archivoCifradoBytes);

        // Guardar el archivo descifrado
        FileOutputStream archivoDescifradoStream = new FileOutputStream(rutaArchivoDescifrado);
        archivoDescifradoStream.write(archivoDescifrado);
        archivoDescifradoStream.close();

        System.out.println("PDF descifrado guardado en: " + rutaArchivoDescifrado);
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Uso: java decrypt <rutaArchivoCifrado> <rutaArchivoDescifrado> <rutaArchivoClaves>");
            return;
        }

        String rutaArchivoCifrado = args[0];
        String rutaArchivoDescifrado = args[1];
        String rutaArchivoClaves = args[2];

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivoClaves))) {
            String clave;
            while ((clave = br.readLine()) != null) {
                try {
                    descifrarArchivoDES(rutaArchivoCifrado, rutaArchivoDescifrado, clave);
                    System.out.println("Desencriptado exitoso con la clave: " + clave);
                    break;
                } catch (Exception e) {
                    //System.out.println("Clave incorrecta: " + clave);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}