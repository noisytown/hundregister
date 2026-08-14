import java.util.Comparator;

public class DogSorter {

    //det står i DR16 testen att det inte ska finnas något public i DogSorter 
    // men har legit ingen aning om hur jag skulle göra om denna var private
    public static void sort(
        SortingAlgorithm algorithm, Comparator<Dog> comparator, Dog[] dogs){
        switch(algorithm){
            case BUBBLE_SORT:
                bubbleSort(comparator, dogs);
                break;
            case SELECTION_SORT:
                selectionSort(comparator, dogs);
                break;
        }
    }

    private static void bubbleSort(Comparator<Dog> comparator, Dog[] dogs) {
        int sorted = dogs.length; // tittar var arrayen är sorterad
        boolean changed;
        do{
            changed = false;
            for(int i = 1; i < sorted; i++) {
                if(comparator.compare(dogs[i], dogs[i - 1]) < 0) {
                    Dog temp = dogs[i];
                    dogs[i] = dogs[i - 1];
                    dogs[i - 1] = temp;
                    changed = true;
                }
            }
            sorted--;
        } while(changed);
    
}

    private static void selectionSort(Comparator<Dog> comparator, Dog[] dogs) {
        for(int i = 0; i < dogs.length - 1; i++) {
            int minIndex = i;
            for(int j = i + 1; j < dogs.length; j++) {
                if(comparator.compare(dogs[j], dogs[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            if(minIndex != i) {
                Dog temp = dogs[i];
                dogs[i] = dogs[minIndex];
                dogs[minIndex] = temp;
            }
        }
    }

}
