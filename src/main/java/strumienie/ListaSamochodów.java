package strumienie;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.javatuples.Triplet;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListaSamochodów {
    public static final int ulubiona = 17;
    public static String Szyfruj(String napis) {
        String efekt = "";
        for(char c : napis.toCharArray()) {
            String s = Character.toString(c);
            efekt = efekt + (char)(c ^ ulubiona);
        }
        return efekt;
    }
    public static List<Samochód> WczytajJSON() {
        String plik = "samochody.json";
        List<Samochód> lista = new ArrayList<>();
        try {
            FileReader fr = new FileReader(plik);
            BufferedReader br = new BufferedReader(fr);
            String napis = "";
            String linia;
            while((linia = br.readLine())!= null) {
                napis = napis + linia;
            }
            br.close();
            fr.close();
            String szyfr = Szyfruj(napis);
            System.out.println("=== szyfr ====");
            System.out.println(szyfr);
            szyfr = Szyfruj(szyfr);
            System.out.println("=== odszyfrowane ====");
            System.out.println(szyfr);
            Gson json = new Gson();
            lista = Arrays.asList(json.fromJson(napis, Samochód[].class));
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
        return lista;
    }
    public static void ZapiszJSON(List<Samochód> lista) {
        String plik = "samochody.json";
        Gson json = new GsonBuilder().setPrettyPrinting().create();
        String napis = json.toJson(lista);
        System.out.println("\n ==== Zapis do formatu JSON ====");
        //System.out.println(napis);
        try {
            FileWriter fw = new FileWriter(plik);
            fw.write(napis);
            fw.close();
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static List<Samochód> Wczytaj() {
        String plik = "samochody.csv";
        List<Samochód> lista = new ArrayList<>();
        try {
            FileReader fr = new FileReader(plik);
            BufferedReader br = new BufferedReader(fr);
            String linia;
            while((linia = br.readLine())!= null) {
                String[] fragmenty = linia.split(";");
                int rok = Integer.parseInt(fragmenty[2]);
                double cena = Double.parseDouble(fragmenty[3]);
                int poj = Integer.parseInt(fragmenty[4]);
                int przebieg = Integer.parseInt(fragmenty[5]);
                Samochód s = new Samochód(fragmenty[0], fragmenty[1], rok, poj, cena, przebieg);
                lista.add(s);
            }
        }
        catch(IOException ioex) {
            System.out.println(ioex.getMessage());
        }
        return lista;
    }
    public static void Zapisz(List<Samochód> lista) {
        //zapisanie listy samochodów do pliku
        String plik = "samochody.csv";
        try {
            FileWriter fw = new FileWriter(plik);
            BufferedWriter bw = new BufferedWriter(fw);
            for(Samochód sam : lista) {
                bw.write(sam.getMarka() + ";" + sam.getModel() + ";" + sam.getRokProdukcji() + ";" + sam.getCena() + ";" + sam.getPojemnośćSilnika() + ";" + sam.getPrzebieg());
                bw.newLine();
            }
                bw.close();
            fw.close();
        }
        catch (IOException ioex) {
            System.out.println(ioex.getMessage());
        }
    }
    public static void main(String[] args) {
        Samochód[] tablica = new Samochód[]{
                new Samochód("Renault", "Captur", 2017, 1100, 45600, 12000),
                new Samochód("Renault", "Kadjar", 2018, 1499, 84500, 7800),
                new Samochód("Ford", "Focus Plus", 2011, 1560, 21400, 57400),
                new Samochód("Honda", "CRV", 2017, 1670, 63400, 13500),
                new Samochód("Honda", "Jazz", 2009, 1199, 15400, 82000),
                new Samochód("Audi", "A6", 2015, 1850, 98000, 23400),
                new Samochód("Opel", "Astra", 2017, 1300, 56000, 5700),
                new Samochód("Opel", "Mokka", 2019, 1400, 73000, 2300),
                new Samochód("Opel", "Corsa", 2012, 1100, 21000, 79320),
                new Samochód("Citroen", "C4", 2015, 1780, 41900, 23400),
                new Samochód("Hyundai", "Santa Cruz", 2018, 1820, 97500, 11800),
                new Samochód("Hyundai", "ix35", 2014, 1700, 34560, 41600),
                new Samochód("Citroen", "C2", 2014, 1100, 25700, 40500),
                new Samochód("Mitsubishi", "ASX", 2017, 1450, 47500, 14600),
                new Samochód("Nissan", "Quashqai", 2017, 1800, 76000, 17800),
                new Samochód("Renault", "Clio", 2010, 1100, 12300, 87000),
                new Samochód("Fiat", "Panda", 2011, 1200, 13500, 105000),
                new Samochód("Ford", "Puma", 2021, 1800, 96000, 1800),
                new Samochód("Volvo", "S80", 2012, 2100, 32300, 87000),
                new Samochód("Toyota", "Auris", 2016, 3000, 33500, 78000),
                new Samochód("Ford", "Fiesta", 2015, 1100, 29000, 24700),
                new Samochód("Nissan", "Leaf", 2017, 1200, 74000, 11300),
                new Samochód("Volvo", "S90", 2015, 1900, 46700, 34700),
                new Samochód("Volkswagen", "Golf", 2016, 1560, 39500, 24730)
        };
        List<Samochód> samochody = Arrays.asList(tablica);
        // Ctrl + Alt + T dla uzyskania regionu w rozumieniu C#

        //region srednia cena samochodu
        double averagePrice = samochody.stream()
                .filter(p->p.getRokProdukcji()>2015)
                .mapToDouble(p->p.getCena())
                .average().getAsDouble();
        System.out.println("Srednia cena samochodow po 2015 roku wynosi " + averagePrice);

        //endregion
        //region liczba samochodow z lat 2010-2015 o przebiegu mniejszym niz 20000 km
        long numberOfCars1 = samochody.stream()
                .filter(p->p.getRokProdukcji()>=2010 && p.getRokProdukcji()<=2015)
                .filter(p->p.getPrzebieg() > 20000)
                .count();
        System.out.println("Liczba samochodow z lat 2010-2015 z przebiegiem mniejszym niz 20000: " + numberOfCars1);

        //endregion
        //region samochody wyprodukowane po 2014 roku posortowane wg ceny
        List<Samochód> carsList1 = samochody.stream()
                .filter(p->p.getRokProdukcji()>2014)
                .sorted((p1,p2)->(int)(p1.getCena() - p2.getCena()))
                .collect(Collectors.toList());
        System.out.println("Lista samochodow wyprodukowanych po 2014, posortowana wg ceny: ");
                for(Samochód s : carsList1){
                    System.out.println("Marka: " + s.getMarka() + " Cena: " + s.getCena() + " Rok produkcji: " + s.getRokProdukcji());
                }

        //endregion
        //region samochody pogrupowane wg marek a w ramach marki posortowane wg modelu
        Map<String, List<Samochód>> carList2 = samochody.stream()
                .sorted((p1, p2)->(p1.getMarka().compareTo(p2.getMarka())))
                .collect(Collectors.groupingBy(Samochód::getMarka));

        System.out.println("Samochody pogrupowane wg marek i posortowane wg modelu ");
        for(Map.Entry<String, List<Samochód>> entry:carList2.entrySet()) {
            System.out.println("Marka: " + entry.getKey());
            for (Samochód s : entry.getValue()) {
                System.out.println("\t Model: " + s.getModel());
            }
        }

        //endregion
        //region obliczenie średniego wieku pojazdów
        //endregion
        // samochody pogrupowane wg pojemności w przedziałach
        //region średnia cena samochodu o pojemności z przedziału (1500,2000) i roku produkcji > 2010
        //endregion
        // region samochody posortowane wg średniego rocznego przebiegu
        //endregion
        // region samochody posortowane wg ceny podzielonej przez liczbę lat
        //endregion
        //region wydobycie samych cen samochodów
        //endregion
        //TODO: wydobycie pary: marka + cena
        //region wydobycie pary: marka + PRZEBIEG ROCZNY i posortowanie wg przebiegu rocznego
        System.out.println("==== SAMOCHODY POSORTOWANE WG PRZEBIEGU ROCZNEGO ====");
        //endregion
        // region wydobycie tripletu marka+model, rok produkcji, przebieg roczny
        /*
            Pair<A,B>
            Triplet<A,B,C>
            Quartet<A,B,C,D>
            Quintet<A,B,C,D,E>
            Sextet<A,B,C,D,E,F>
            Septet<A,B,C,D,E,F,G>
            Octet<A,B,C,D,E,F,G,H>
            Ennead<A,B,C,D,E,F,G,H,I>
            Decade<A,B,C,D,E,F,G,H,I,J>
         */
        //endregion
        //region lista unikalnych nazw marek samochodów z wykorzystaniem własnego Collectora
        Collector<String, StringJoiner, String> myCollector = Collector.of(
                () -> new StringJoiner(" : "),          // supplier
                (j, p) -> j.add(p.toUpperCase()),       // accumulator
                (j1, j2) -> j1.merge(j2),               // combiner
                StringJoiner::toString);                // finisher

        // tu wykorzystanie kolektora (mycollector)
        //endregion
        //region przykład strumienia z operacją println w pętli for (wylistowanie modeli samochodów
        System.out.println("==== Lista modeli samochodów ==== ");
        //endregion
        //region a teraz ulepszona wersja z interfejsem funkcyjnym Consumer
        System.out.println("==== Wykorzystanie interfejsu funkcyjnego Consumer ==== ");
        //endregion
        List<Samochód> lista = Arrays.stream(tablica)
                .collect(Collectors.toList());
        Zapisz(lista);
        /*
        System.out.println("\n==== wczytane samochody ==== ");
        lista = Wczytaj();
        for (Samochód s : lista)
            System.out.println(s.getMarka() + " " + s.getModel() + " " + s.getCena());
            */
        ZapiszJSON(lista);
        lista = WczytajJSON();
        for (Samochód s : lista)
            System.out.println(s.getMarka() + " " + s.getModel() + " " + s.getCena());
    }
}
