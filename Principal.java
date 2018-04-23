package Vigenere;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Informe a chave:");
        String chave = s.nextLine();

        ArrayList<String> frases = lerArquivo(); // retorna array com todas as linhas do arquivo de entrada
        ArrayList<String> frasesCriptografadas = criptografia(chave, frases); // retorna array com frases do aquivo de entrada criptografadas 
        escreverArquivo(frasesCriptografadas);  // envia array de frases croptografadas para ser gravado no arquivo de saída
        System.out.println("Criptografado com suceso! Confira na raiz do projeto.");
    }

    private static ArrayList<String> lerArquivo() {

        ArrayList<String> frases = new ArrayList<>(); //armazena linhas do arquivo
        try {
            BufferedReader br = new BufferedReader(new FileReader("arquivo_texto.txt"));
            String frase;
            while ((frase = br.readLine()) != null) {
                frases.add(frase);
            }
            return frases; // retorna vetor contendo tadas as frases do arquivo. Cada posição do array é uma linha do arquivo.
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    private static ArrayList<String> criptografia(String chave, ArrayList<String> frases) {

        ArrayList<String> frasesCriptografadas = new ArrayList<>();

        for (String frase : frases) {
            String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            StringBuilder fraseCifrada = new StringBuilder();
            String palavraChave = chave;
            char[] vetorChave = new char[frase.length()];

            int contador = 0; // variavel que controla o preenchimento do vetor de caracteres da palavra chave
            int x = 0, y = 0, z; // variaveis que guardarão as posições dos caracteres da frase(x), do vetor chave(y) e do caracter que substituirá o original(z)

            for (int i = 0; i < vetorChave.length; i++) {
                if (frase.charAt(i) == ' ') {
                    i++;
                }
                vetorChave[i] = palavraChave.toUpperCase().charAt(contador);
                contador++;

                if (contador >= palavraChave.length()) {
                    contador = 0;
                }
            }

            for (int i = 0; i < frase.length(); i++) {
                if (frase.charAt(i) == ' ') {
                    fraseCifrada.append(" ");
                    i++;
                }
                for (int j = 0; j < alfabeto.length(); j++) {
                    if (frase.toUpperCase().charAt(i) == alfabeto.charAt(j)) {
                        x = j;
                    }
                    if (vetorChave[i] == alfabeto.charAt(j)) {
                        y = j;
                    }
                }
                z = (x + y) % 26;
                fraseCifrada.append(alfabeto.charAt(z));
            }
            frasesCriptografadas.add(fraseCifrada.toString());
        }

        return frasesCriptografadas;
    }

    private static void escreverArquivo(ArrayList<String> frasesCriptografadas) {

        try {
            PrintWriter pw = new PrintWriter("arquivo_criptografado_texto.txt");
            for (String frase : frasesCriptografadas) {
                pw.println(frase); // escreve frase por frase no arquivo de saída.
            }
            pw.flush();
            pw.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
