/*
    José Vitor Novaes Santos
    Marcus Vinicius Natrielli Garcia
    Victor Fernandes de Oliveira Brayner
*/

package compiler.main;

import compiler.ast.*; 
import java.io.*;

public class Main {
    public static void main( String []args ) {
        File file;
        FileReader stream;
        int numChRead;
        Program program;
        if ( args.length != 2 ) {
            System.out.println("Uso:\n Main input output");
            System.out.println("'input' é o arquivo a ser compilado");
            System.out.println("'output' é o arquivo que guardará o código a ser gerado (ou os erros de compilação)");
        }
        else {
            file = new File(args[0]);
            if ( ! file.exists() || ! file.canRead() ) {
                System.out.println("Arquivo " + args[1] + " não pôde ser aberto para escrita");
                throw new RuntimeException();
            }
            try {
                stream = new FileReader(file);
            } catch ( FileNotFoundException e ) {
                System.out.println("Um problema ocorreu: o arquivo não existe mais");
                throw new RuntimeException();
            }

            char []input = new char[ (int ) file.length() + 1 ];
            try {
                numChRead = stream.read( input, 0, (int ) file.length() );
            } catch ( IOException e ) {
                System.out.println("Erro ao ler o arquivo " + args[0]);
                throw new RuntimeException();
            }
            if ( numChRead != file.length() ) {
                System.out.println("Erro de leitura");
                throw new RuntimeException();
            }
            try {
                stream.close();
            } catch ( IOException e ) {
                System.out.println("Erro ao manipular o arquivo " + args[0]);
                throw new RuntimeException();
            }

            Compiler compiler = new Compiler();
            FileOutputStream outputStream;
            try {
                outputStream = new FileOutputStream(args[1]);
            } catch ( IOException e ) {
                System.out.println("Arquivo " + args[1] + " não pôde ser aberto para escrita");
                throw new RuntimeException();
            }
            PrintWriter printWriter = new PrintWriter(outputStream);
            program = null;

            try {
                program = compiler.compile(input, printWriter, args[0]);
            } catch ( RuntimeException e ) {
                System.out.println(e);
            }
            if ( program != null ) {
                PW pw = new PW();
                pw.set(printWriter);
                program.genC(pw);
                if ( printWriter.checkError() ) {
                    System.out.println("Ocorreu um erro no arquivo de saída");
                }
            }
        }
    }
}
