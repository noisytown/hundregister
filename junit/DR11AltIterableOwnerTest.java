import java.util.ArrayList;

import org.junit.jupiter.api.*;

/**
 * @author Henrik Bergström
 * @version 1.0
 * @apiNote Testen i denna klass är stabila, och det finns i skrivandets stund
 *          inga kända saker som behöver kompletteras. Det ska dock sägas att
 *          testet är väldigt enkelt, och bara kontrollerar att iterationen
 *          fungerar. En verklig implementation skulle behöva kontrollera
 *          betydligt mer.
 */
@DisplayName("DR11 extra: itererbar ägare")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DR11AltIterableOwnerTest extends DR01Assertions {
	
	@Order(10)
	@Test
	@DisplayName("Det går att iterera över ägarens hundar")
	public void possibleToIterateOverCollection() {
		assertTestTimesOut(() -> {
			Dog nova = new Dog("Nova", "Boxer", 7, 6);
			Dog ronja = new Dog("Ronja", "Jack russell terrier", 8, 5);
			Dog saga = new Dog("Saga", "Bulldogg", 8, 5);

			Owner owner = new Owner("Henrik", nova, ronja, saga);

			ArrayList<Dog> actual = new ArrayList<>();
			for (Dog dog : owner) {
				actual.add(dog);
			}
			assertThat(actual).contains(nova, ronja, saga).inAnyOrder()
					.onErrorReport("Fel hundar returneras av iteratorn");
		});
	}

}
