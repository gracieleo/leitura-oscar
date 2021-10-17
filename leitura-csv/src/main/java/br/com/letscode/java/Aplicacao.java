package br.com.letscode.java;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Aplicacao {

    private List<Oscar> ator;
    private List<Oscar> atriz;

    public static void main(String[] args)  {

        Aplicacao app = new Aplicacao();

        app.testeLeituraArquivoMale();
        app.testeLeituraArquivoFemale();
        app.getAtorMaisJovem();// questão 1
        app.getAtrizMaisPremiada();//questão 2
        app.maisVezesVencedora();//questão 3
        app.recebeuMaisDeUmOscar();//questão 4
        app.resumoQuantosPremios("Katharine Hepburn");//questão 5 (atriz)
        app.resumoQuantosPremios("Tom Hanks");//questão 5 (ator)

    }


   private void getAtorMaisJovem()  {

       System.out.println("O ator mais jovem ganhador do oscar: ");
        this.ator.stream()
                .min(Comparator.comparingInt(Oscar::getAge))
                .ifPresent(System.out::println);
    }

    private void getAtrizMaisPremiada() {
        System.out.println("A atriz que mais vezes foi premiada: ");
        Map<String, Long> nome = this.atriz.stream()
                .map(Oscar::getName)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        nome.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .ifPresent(m -> System.out.println(m.getKey()));
    }


    private void maisVezesVencedora(){
        System.out.println("A atriz entre 20 e 30 anos que mais vezes venceu: ");
        Map<String, Long> vencedora = this.atriz.stream()
                .filter(a -> a.getAge() > 20 && a.getAge() < 30)
                .map(Oscar::getName)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        vencedora.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .ifPresent(m -> System.out.println(m.getKey()));


    }

    private void recebeuMaisDeUmOscar (){
        System.out.println("Lista de atores/atrizes que receberam mais de um Oscar: ");
        System.out.println("Atrizes: ");
        Map<String, Long> nomeAtriz = this.atriz.stream()
                .map(Oscar::getName)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        nomeAtriz.entrySet().stream()
                        .filter(n -> n.getValue() > 1)
                        .forEach(System.out::println);

        System.out.println("Atores: ");
        Map<String, Long> nomeAtor = this.ator.stream()
                .map(Oscar::getName)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        nomeAtor.entrySet().stream()
                .filter(n -> n.getValue() > 1)
                .forEach(System.out::println);
    }

    private void resumoQuantosPremios(String name) {
        System.out.println("Resumo das informações sobre premios recebidos do ator/atriz escolhido: ");
        this.atriz.stream()
                .filter(f -> Objects.equals(f.getName(), name))
                .forEach(System.out::println);
        this.ator.stream()
                .filter(m -> Objects.equals(m.getName(), name))
                .forEach(System.out::println);
    }


    private void testeLeituraArquivoMale()  {
        String filepath = getFilepathFromResourceAsStream("oscar_age_male.csv");
        try(Stream <String> lines = Files.lines(Path.of(filepath))){
            this.ator = lines.skip(1)
                    .map(Oscar::fromLine)
                    .collect(Collectors.toList());
        }catch(IOException e){
            e.printStackTrace();
        }
    }

        private void testeLeituraArquivoFemale()  {
            String filepath = getFilepathFromResourceAsStream("oscar_age_female.csv");
            try(Stream <String> lines = Files.lines(Path.of(filepath))){
                this.atriz = lines.skip(1)
                        .map(Oscar::fromLine)
                        .collect(Collectors.toList());
            }catch(IOException e){
                e.printStackTrace();
            }
        }

    private String getFilepathFromResourceAsStream(String fileName) {
        URL url = getClass().getClassLoader().getResource(fileName);
        File file = new File(url.getFile());
        return file.getPath();
    }

}









