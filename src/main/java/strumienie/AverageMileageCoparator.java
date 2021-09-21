package strumienie;

import java.util.Comparator;

public class AverageMileageCoparator implements Comparator<Samochód> {

    @Override
    public int compare(Samochód o1, Samochód o2) {
        return o1.getRocznyPrzebieg() - o2.getRocznyPrzebieg();
    }
}
