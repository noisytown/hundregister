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
@DisplayName("DR11 extra: itererbar samling")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DR11AltIterableCollectionTest extends DR01Assertions {

	@Order(10)
	@Test
	@DisplayName("Det går att iterera över samlingen")
	public void possibleToIterateOverCollection() {
		assertTestTimesOut(() -> {
			OwnerCollection owners = new OwnerCollection();

			Owner adam = new Owner("Adam");
			Owner beata = new Owner("Beata");
			Owner caesar = new Owner("Caesar");

			owners.addOwner(adam);
			owners.addOwner(beata);
			owners.addOwner(caesar);

			ArrayList<Owner> actual = new ArrayList<>();
			for (Owner owner : owners) {
				actual.add(owner);
			}
			assertThat(actual).contains(adam, beata, caesar).inAnyOrder()
					.onErrorReport("Fel ägare returneras av iteratorn");
		});
	}

}
