import org.junit.jupiter.api.*;

/**
 * @author Henrik Bergström
 * @version 1.0
 * @apiNote Testen i denna klass är stabila, och det finns i skrivandets stund
 *          inga kända saker som behöver kompletteras.
 */
@DisplayName("DR12 extra: Grundläggande test för ägare med en naturlig sorteringsordning")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DR12AltComparableOwnerTest extends DR01Assertions {

	private static final Owner BEATRICE = new Owner("Beatrice");
	private static final Owner JOHN = new Owner("John");
	private static final Owner JOHAN = new Owner("Johan");

	@Order(10)
	@Test
	@DisplayName("Rätt ordning")
	public void correctOrder() {
		assertTestTimesOut(() -> {
			int actual = BEATRICE.compareTo(JOHN);
			assertThat(actual).isLessThan(0)
					.onErrorReport("Fel resultat, borde vara <0 eftersom Beatrice kommer före John");
		});
	}

	@Order(20)
	@Test
	@DisplayName("Lika")
	public void same() {
		assertTestTimesOut(() -> {
			int actual = BEATRICE.compareTo(BEATRICE);
			assertThat(actual).is(0).onErrorReport("Fel resultat, borde vara 0 eftersom bägge heter Beatrice");
		});
	}

	@Order(30)
	@Test
	@DisplayName("Omvänd ordning")
	public void reverseOrder() {
		assertTestTimesOut(() -> {
			int actual = JOHN.compareTo(BEATRICE);
			assertThat(actual).isGreaterThan(0)
					.onErrorReport("Fel resultat, borde vara >0 eftersom Beatrice kommer före John");
		});
	}

	@Order(40)
	@Test
	@DisplayName("Gemensamt prefix, rätt ordning")
	public void correctOrderCommonPrefix() {
		assertTestTimesOut(() -> {
			int actual = JOHAN.compareTo(JOHN);
			assertThat(actual).isLessThan(0)
					.onErrorReport("Fel resultat, borde vara <0 eftersom Johan kommer före John");
		});
	}

	@Order(50)
	@Test
	@DisplayName("Gemensamt prefix, omvänd ordning")
	public void reverseOrderCommonPrefix() {
		assertTestTimesOut(() -> {
			int actual = JOHN.compareTo(JOHAN);
			assertThat(actual).isGreaterThan(0)
					.onErrorReport("Fel resultat, borde vara >0 eftersom Johan kommer före John");
		});
	}

}
