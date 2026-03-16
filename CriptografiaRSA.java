import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.*;
import javax.crypto.Cipher;
import java.io.File;

//geração de par de chaves
public class CriptografiaRSA {

    static Scanner scanner = new Scanner(System.in);

    public static KeyPair gerarChaves() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);

        return generator.generateKeyPair();
    }

    public static void salvarChaves(PublicKey publicKey, PrivateKey privateKey) throws Exception {
        new File("Chaves").mkdirs();

        Files.write(Paths.get("chaves/public.key"), publicKey.getEncoded());
        Files.write(Paths.get("chaves/private.key"), privateKey.getEncoded());

        System.out.println("Chaves geradas e salvas!");
    }

    //carregar chave publica
    public static PublicKey carregarChavePublica() throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get("chaves/public.key"));

        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");

        return factory.generatePublic(spec);
    }

    //carregar chave privada
    public static PrivateKey carregarChavePrivada() throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get("chaves/private.key"));

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");

        return factory.generatePrivate(spec);
    }

    //calcular hash
    public static String calcularHash(String caminho) throws Exception {

        byte[] dados = Files.readAllBytes(Paths.get(caminho));

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] hash = md.digest(dados);

        StringBuilder hex = new StringBuilder();

        for (byte b : hash) {
            hex.append(String.format("%02x", b));
        }

        return hex.toString();
    }
    //verificar integridade
    public static void verificarIntegridade(String original, String restaurado) {

        try {

            String hashOriginal = calcularHash(original);
            String hashRestaurado = calcularHash(restaurado);

            System.out.println("\nHash original:   " + hashOriginal);
            System.out.println("Hash restaurado: " + hashRestaurado);

            if (hashOriginal.equals(hashRestaurado)) {

                System.out.println("\n✔ Integridade confirmada!");
                System.out.println("O arquivo restaurado é idêntico ao original.");

            } else {

                System.out.println("\n✖ Arquivos diferentes!");
                System.out.println("A descriptografia pode ter falhado.");

            }

        } catch (Exception e) {

            System.out.println("Erro ao verificar integridade.");

        }

    }

    //criptografar arquivo
    public static void criptografarArquivo() {
        try {

            if (!Files.exists(Paths.get("chaves/public.key"))) {

                System.out.println("⚠ Nenhuma chave encontrada. Gere as chaves primeiro.");
                return;

            }

            System.out.print("Arquivo para criptografar: ");
            String input = scanner.nextLine();

            if (!Files.exists(Paths.get(input))) {

                System.out.println("⚠ Arquivo não encontrado.");
                return;

            }

            byte[] dados = Files.readAllBytes(Paths.get(input));

            if (dados.length > 245) {

                System.out.println("⚠ Arquivo muito grande para RSA.");
                System.out.println("Limite aproximado: 245 bytes.");
                return;

            }

            PublicKey chavePublica = carregarChavePublica();

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, chavePublica);

            byte[] criptografado = cipher.doFinal(dados);

            String output = input + ".enc";

            Files.write(Paths.get(output), criptografado);

            System.out.println("✔ Arquivo criptografado: " + output);

        } catch (Exception e) {

            System.out.println("Erro ao criptografar.");

        }  
    }

    //descriptografar arquivo
    public static void descriptografarArquivo() {
          try {

            if (!Files.exists(Paths.get("chaves/private.key"))) {

                System.out.println("⚠ Nenhuma chave privada encontrada.");
                return;

            }

            System.out.print("Arquivo para descriptografar: ");
            String input = scanner.nextLine();

            if (!Files.exists(Paths.get(input))) {

                System.out.println("⚠ Arquivo não encontrado.");
                return;

            }

            PrivateKey chavePrivada = carregarChavePrivada();

            byte[] dados = Files.readAllBytes(Paths.get(input));

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, chavePrivada);

            byte[] descriptografado = cipher.doFinal(dados);

            String output = "arquivo_restaurado.txt";

            Files.write(Paths.get(output), descriptografado);

            System.out.println("✔ Arquivo descriptografado: " + output);

            System.out.print("Digite o caminho do arquivo original para verificar integridade: ");
            String original = scanner.nextLine();

            verificarIntegridade(original, output);

        } catch (Exception e) {

            System.out.println("Erro ao descriptografar.");

        }
    }

    //menu
    public static void menu() {
        while (true) {
            System.out.println("\n===============================");
            System.out.println(" SISTEMA DE CRIPTOGRAFIA RSA");
            System.out.println("===============================");
            System.out.println("1 - Gerar par de chaves");
            System.out.println("2 - Criptografar arquivo");
            System.out.println("3 - Descriptografar arquivo");
            System.out.println("4 - Sair");

            System.out.print("Escolha: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            try {

                switch (opcao) {

                    case 1:
                        KeyPair pair = gerarChaves();
                        salvarChaves(pair.getPublic(), pair.getPrivate());
                        break;

                    case 2:
                        criptografarArquivo();
                        break;

                    case 3:
                        descriptografarArquivo();
                        break;

                    case 4:
                        System.out.println("Encerrando programa.");
                        return;

                    default:
                        System.out.println("Opção inválida.");
                }

            } catch (Exception e) {

                System.out.println("Erro no sistema.");
            }
        }
    }

    //funçaõ principal
    public static void main(String[] args) {
        menu();
    }

}