package strumienie;

import org.javatuples.Triplet;

import java.time.LocalDate;

public class Samochód {
    public String getMarka() {
        return marka;
    }

    public String getModel() {
        return model;
    }

    public int getRokProdukcji() {
        return rokProdukcji;
    }

    public int getPojemnośćSilnika() {
        return pojemnośćSilnika;
    }

    public static final int wielkoscPrzedzialu = 1000;

    public int getRocznyPrzebieg(){
        int rocznyPrzebieg =przebieg/((LocalDate.now().getYear()+1) - rokProdukcji);
        return  rocznyPrzebieg;
    }

    public int getPojemnoscWPrzedziale(){
        return  pojemnośćSilnika/Samochód.wielkoscPrzedzialu;
    }

    public double getCena() {
        return cena;
    }

    private String marka;
    private String model;
    private int rokProdukcji;
    private int pojemnośćSilnika;
    private double cena;
    private int przebieg;

    public int getPrzebieg() {
        return przebieg;
    }

    public Samochód(String marka, String model, int rok, int pojemność, double cena ,int przebieg) {
        this.marka = marka;
        this.model = model;
        this.rokProdukcji = rok;
        this.pojemnośćSilnika = pojemność;
        this.cena = cena;
        this.przebieg = przebieg;
    }
    public Para<String, Double> getPara() {
        Para<String, Double> para = new Para(this.toString(), przebieg/(LocalDate.now().getYear()- rokProdukcji+1));
        return para;
    }
    public Para<String, Double> getParaZCeną() {
        Para<String, Double> para = new Para(marka, cena);
        return para;
    }
    public Triplet<String, Integer, Double> getTriplet() {
        Triplet<String, Integer, Double> triplet = new Triplet(this.marka + " " + model, rokProdukcji, przebieg/(LocalDate.now().getYear()- rokProdukcji+1));
        return triplet;
    }
    @Override
    public String toString() {
        return marka + " " + model + " " + rokProdukcji + " cena: " + cena;
    }
}
