import java.util.Comparator;

public class TailNameComparator implements Comparator<Dog> { 
    public int compare(Dog a, Dog b){ 
        if(a.getTailLength() == b.getTailLength()) {
            return a.getName().compareTo(b.getName()); // om length är samma, compare namn istället
        }
        if(a.getTailLength() < b.getTailLength()) {
            return -1;
        }
        if(a.getTailLength() > b.getTailLength()) {
            return 1;
        }
        return 0;
    }
}
