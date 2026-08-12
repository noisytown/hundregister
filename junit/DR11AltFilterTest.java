import java.util.List;

import org.junit.jupiter.api.*;

/**
 * @author Henrik Bergström
 * @version 1.0
 * @apiNote Testen i denna klass är stabila, och det finns i skrivandets stund
 *          inga kända saker som behöver kompletteras.
 */
@DisplayName("DR11 extra: filter-metod")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DR11AltFilterTest extends DR01Assertions {
	@Order(10)
	@Test
	@DisplayName("En tom samling kan filtreras")
	public void emptyCollection() {
		assertTestTimesOut(() -> {
			OwnerCollection owners = new OwnerCollection();
			List<Owner> actual = owners.filter(Owner::ownsAnyDog);
			assertThat(actual).isEmpty().onErrorReport("Fel ägare returnerades");
		});
	}

	@Order(20)
	@Test
	@DisplayName("Flera ägare matchar predikatet")
	public void multipleMatches() {
		assertTestTimesOut(() -> {
			OwnerCollection owners = new OwnerCollection();

			Owner adam = new Owner("Adam");
			Owner beata = new Owner("Beata");
			Owner caesar = new Owner("Caesar", new Dog("Max", "Tax", 1, 2));
			Owner daniella = new Owner("Daniealla", new Dog("Snobben", "Beagle", 1, 2));
			Owner einar = new Owner("Einar");

			owners.addOwner(adam);
			owners.addOwner(beata);
			owners.addOwner(caesar);
			owners.addOwner(daniella);
			owners.addOwner(einar);

			List<Owner> actual = owners.filter(Owner::ownsAnyDog);
			assertThat(actual).contains(caesar, daniella).inAnyOrder().onErrorReport("Fel ägare returnerades");
		});
	}

	@Order(30)
	@Test
	@DisplayName("Ingen ägare matchar predikatet")
	public void noMatches() {
		assertTestTimesOut(() -> {
			OwnerCollection owners = new OwnerCollection();

			Owner adam = new Owner("Adam");
			Owner beata = new Owner("Beata");
			Owner caesar = new Owner("Caesar");

			owners.addOwner(adam);
			owners.addOwner(beata);
			owners.addOwner(caesar);

			List<Owner> actual = owners.filter(Owner::ownsAnyDog);
			assertThat(actual).isEmpty().onErrorReport("Fel ägare returnerades");
		});
	}

	@Order(40)
	@Test
	@DisplayName("Olika predikat fungerar")
	public void differentPredicates() {
		assertTestTimesOut(() -> {
			OwnerCollection owners = new OwnerCollection();

			Owner adam = new Owner("Adam");
			Owner beata = new Owner("Beata");
			Owner caesar = new Owner("Caesar", new Dog("Max", "Tax", 1, 2));
			Owner daniella = new Owner("Daniealla");
			Owner einar = new Owner("Einar", new Dog("Max", "Beagle", 1, 2));

			owners.addOwner(adam);
			owners.addOwner(beata);
			owners.addOwner(caesar);
			owners.addOwner(daniella);
			owners.addOwner(einar);

			List<Owner> ownsMax = owners.filter(o -> o.ownsDog("Max"));
			assertThat(ownsMax).contains(caesar, einar).inAnyOrder()
					.onErrorReport("Fel ägare returnerades när ägare av hundar som heter Max skulle identifieras");
			List<Owner> hasShortName = owners.filter(o -> o.getName().length() < 5);
			assertThat(hasShortName).containsOnly(adam)
					.onErrorReport("Fel ägare returnerades när ägare med korta namn (<5 tecken) skulle identifieras");
		});
	}

}
