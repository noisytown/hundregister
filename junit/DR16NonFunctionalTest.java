import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

// TODO: #13
/**
 * @author Henrik Bergström
 * @version 1.0
 * @apiNote Testen för vilka publika metoder som ska finnas i klasserna bör vara
 *          korrekta, men kan komma att uppdateras om vi tex missat någon
 *          frivillig metod.
 */
@DisplayName("DR16: Grundläggande test för icke-funktionella krav")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class DR16NonFunctionalTest extends DR01Assertions {

	private boolean isCollection(Class<?> type) {
		if (type.isArray())
			return true;

		if (Collection.class.isAssignableFrom(type))
			return true;

		if (Map.class.isAssignableFrom(type))
			return true;

		return false;
	}

	@DisplayName("Samlingar i Owner")
	@Nested
	@Order(10)
	@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	public class CollectionsInOwnerTest {
		@Order(20)
		@Test
		@DisplayName("Array används för att spara hundarna")
		public void arrayUseToStoreDogs() {
			Class<?> clazz = Owner.class;
			Dog[] expected = {};

			for (var field : clazz.getDeclaredFields()) {
				if (field.getType() == expected.getClass())
					return;
			}

			failTest("Kunde inte hitta någon array av hundar i Owner");
		}

		@Order(30)
		@Test
		@DisplayName("Inga andra datasamlingar på klassnivå")
		public void noOtherCollectionsAtClassLevel() {
			Class<?> clazz = Owner.class;

			List<String> problems = new ArrayList<>();

			for (var field : clazz.getDeclaredFields()) {
				if (isCollection(field.getType())) {
					problems.add(field.getName());
				}
			}

			if (problems.size() > 1)
				failTest("Den enda samlingen som förväntas i Owner är arrayen med hundar, men det finns fler: %s",
						problems);
		}

	}

	@DisplayName("Samlingar i OwnerCollection")
	@Nested
	@Order(40)
	@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	public class CollectionsInOwnerCollectionTest {

		@Order(50)
		@Test
		@DisplayName("Lista eller annan samling används för att spara ägarna")
		public void collectionUseToStoreOwners() {
			Class<?> clazz = OwnerCollection.class;

			for (var field : clazz.getDeclaredFields()) {
				if (Collection.class.isAssignableFrom(field.getType()))
					return;
			}

			failTest("Kunde inte hitta någon lista, eller annan samlingsklass, av ägare i OwnerCollection");
		}

		@Order(60)
		@Test
		@DisplayName("Inga andra datasamlingar på klassnivå")
		public void noOtherCollectionsAtClassLevel() {
			Class<?> clazz = OwnerCollection.class;

			List<String> problems = new ArrayList<>();

			for (var field : clazz.getDeclaredFields()) {
				if (isCollection(field.getType())) {
					problems.add(field.getName());
				}
			}

			if (problems.size() > 1)
				failTest(
						"Den enda samlingen som förväntas i OwnerCollection är samlingen med ägare, men det finns fler: %s",
						problems);
		}

	}

	@DisplayName("Samlingar i DogRegister")
	@Nested
	@Order(70)
	@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	public class CollectionsInDogRegisterTest {

		/*
		 * Nedanstående test förutsätter att DogRegister innehåller all funktionalitet
		 * som inte tillhör de övriga klasserna i figur 1.1. Om du delat upp denna
		 * funktionalitet i flera klasser, vilket är helt tillåtet, så kommer det inte
		 * att kunna se om du gjort fel i någon annan klass.
		 */

		@Order(80)
		@Test
		@DisplayName("Inga andra datasamlingar än OwnerCollection på klassnivå")
		public void noOtherCollectionsAtClassLevel() {
			Class<?> clazz = DogRegister.class;

			List<String> problems = new ArrayList<>();

			for (var field : clazz.getDeclaredFields()) {
				if (isCollection(field.getType())) {
					problems.add(field.getName());
				}
			}

			if (problems.size() > 0)
				failTest(
						"Den enda samlingen som förväntas i DogRegister är samlingen med ägare, som ska vara en OwnerCollection, men det finns andra: %s",
						problems);
		}

	}

	@DisplayName("Publika konstruktorer")
	@Nested
	@Order(90)
	@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	public class ExpectedPublicConstructorsTest {

		@Order(100)
		@ParameterizedTest(name = "{0} ska ha {1} publik(a) konstruktor(er)")
		@MethodSource("expectedPublicConstructors")
		@DisplayName("Bara förväntade publika konstruktorer")
		public void expectedPublicConstructorsTest(Class<?> c, int expected) {
			assertEquals(expected, c.getConstructors().length);
		}

		private static Stream<Arguments> expectedPublicConstructors() {
			return Stream.of(Arguments.of(Dog.class, 2), Arguments.of(Owner.class, 1), Arguments.of(DogSorter.class, 0),
					Arguments.of(OwnerCollection.class, 1));
		}
	}

	@DisplayName("Publika metoder")
	@Nested
	@Order(110)
	@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	public class ExpectedPublicMethodsTest {

		@Order(120)
		@ParameterizedTest(name = "{0}")
		@MethodSource("expectedPublicMethods")
		@DisplayName("Bara förväntade publika metoder")
		public void onlyExpectedPublicMethods(Class<?> cut, ExpectedMethod... expectedMethods) {
			Collection<Method> methods = new ArrayList<>(Arrays.asList(cut.getMethods()));
			methods.removeIf(m -> m.getDeclaringClass() != cut);

			for (var expected : expectedMethods) {
				methods.removeIf(expected::matches);
			}

			if (methods.size() > 0) {
				failTest("Metoder som inte förväntades i det publika gränssnittet: %s", methods);
			}

		}

		@Order(130)
		@ParameterizedTest(name = "{0}")
		@MethodSource("expectedPublicMethods")
		@DisplayName("Kunde identifiera alla publika metoder")
		public void onlyOneMatchForEachExpectedPublicMethod(Class<?> cut, ExpectedMethod... expectedMethods) {
			Collection<Method> methods = new ArrayList<>(Arrays.asList(cut.getMethods()));
			methods.removeIf(m -> m.getDeclaringClass() != cut);
			Collection<ExpectedMethod> multipleMatches = new ArrayList<>();

			for (var expected : expectedMethods) {
				if (methods.stream().filter(expected::matches).count() > 1) {
					multipleMatches.add(expected);
				}
			}

			if (multipleMatches.size() > 0) {
				failTest(
						"Flera publika metoder i klassen matchar signaturen för dessa förväntade publika metoder: %s Det borde bara vara en.",
						multipleMatches);
			}

		}

		private static Stream<Arguments> expectedPublicMethods() {
			ExpectedMethod[] expectedMethodsInDog = { new ExpectedMethod( //
					"getAge", new PossibleReturnTypes(int.class), "getAge"), //
					new ExpectedMethod( //
							"getBreed", new PossibleReturnTypes(String.class), "getBreed"), //
					new ExpectedMethod( //
							"getName", new PossibleReturnTypes(String.class), "getName"), //
					new ExpectedMethod( //
							"getOwner", new PossibleReturnTypes(Owner.class, Optional.class), "getOwner"), //
					new ExpectedMethod( //
							"getTailLength", new PossibleReturnTypes(double.class), "getTailLength"), //
					new ExpectedMethod( //
							"getWeight", new PossibleReturnTypes(int.class), "getWeight"), //
					new ExpectedMethod( //
							"metoden för att öka åldern (utan argument)",
							new PossibleReturnTypes(void.class, int.class, boolean.class),
							"^(?!.*\\bgetAge\\b).*[Aa]ge.*"), //
					new ExpectedMethod( //
							"metoden för att öka åldern (med argument)",
							new PossibleReturnTypes(void.class, int.class, boolean.class), ".*[Aa]ge.*", int.class), //
					new ExpectedMethod( //
							"setOwner", new PossibleReturnTypes(boolean.class), "setOwner", Owner.class), //
					new ExpectedMethod( //
							"toString", new PossibleReturnTypes(String.class), "toString"), //

			};
			ExpectedMethod[] expectedMethodsInOwner = { new ExpectedMethod( //
					"getName", new PossibleReturnTypes(String.class), "getName"), //
					new ExpectedMethod( //
							"getDogs", new PossibleReturnTypes((new Dog[] {}).getClass()), "getDogs"), //
					new ExpectedMethod( //
							"addDog", new PossibleReturnTypes(boolean.class), "addDog", Dog.class), //
					new ExpectedMethod( //
							"removeDog(String)", new PossibleReturnTypes(boolean.class), "removeDog", String.class), //
					new ExpectedMethod( //
							"removeDog(Dog)", new PossibleReturnTypes(boolean.class), "removeDog", Dog.class), //
					new ExpectedMethod( //
							"ownsAnyDog", new PossibleReturnTypes(boolean.class), "ownsAnyDog"), //
					new ExpectedMethod( //
							"ownsDog(String)", new PossibleReturnTypes(boolean.class), "ownsDog", Dog.class), //
					new ExpectedMethod( //
							"ownsMaxDogs", new PossibleReturnTypes(boolean.class), "ownsMaxDogs"), //
					new ExpectedMethod( //
							"ownsDog(Dog)", new PossibleReturnTypes(boolean.class), "ownsDog", String.class), //
					new ExpectedMethod( //
							"toString", new PossibleReturnTypes(String.class), "toString"), //
					new ExpectedMethod( //
							"iterator (från Iterable)", new PossibleReturnTypes(java.util.Iterator.class), "iterator"), //
					new ExpectedMethod( //
							"compareTo (från Comparator)", new PossibleReturnTypes(int.class), "compareTo",
							Object.class), //
					new ExpectedMethod( //
							"compareTo (från Comparator)", new PossibleReturnTypes(int.class), "compareTo",
							Owner.class), //
					new ExpectedMethod( //
							"metod för att hämta en hund med ett visst namn (tillåten, men ej i instruktionerna)",
							new PossibleReturnTypes(Dog.class), ".*[dD]og.*", String.class), //
			};

			ExpectedMethod[] expectedMethodsInDogSorter = { new ExpectedMethod( //
					"den publika sorteringsmetoden", new PossibleReturnTypes(void.class), "sort",
					SortingAlgorithm.class, Comparator.class, Dog[].class), //
			};

			ExpectedMethod[] expectedMethodsInOwnerCollection = { new ExpectedMethod( //
					"addOwner", new PossibleReturnTypes(boolean.class), "addOwner", Owner.class), //
					new ExpectedMethod( //
							"containsOwner(Owner)", new PossibleReturnTypes(boolean.class), "containsOwner",
							Owner.class), //
					new ExpectedMethod( //
							"containsOwner(String)", new PossibleReturnTypes(boolean.class), "containsOwner",
							String.class), //
					new ExpectedMethod( //
							"filter (om man implementerat detta)",
							new PossibleReturnTypes(ArrayList.class, List.class, LinkedList.class, Vector.class,
									Collection.class, Set.class, HashSet.class, TreeSet.class, OwnerCollection.class),
							"filter", Predicate.class), //
					new ExpectedMethod( //
							"getAllOwners",
							new PossibleReturnTypes(ArrayList.class, List.class, LinkedList.class, Vector.class,
									Collections.class),
							"getAllOwners"), //
					new ExpectedMethod( //
							"getOwner", new PossibleReturnTypes(Owner.class, Optional.class), "getOwner", String.class), //
					new ExpectedMethod( //
							"iterator (från Iterable)", new PossibleReturnTypes(Iterator.class), "iterator"), //
					new ExpectedMethod( //
							"removeOwner(String)", new PossibleReturnTypes(boolean.class), "removeOwner", String.class), //
					new ExpectedMethod( //
							"removeOwner(Owner)", new PossibleReturnTypes(boolean.class), "removeOwner", Owner.class), //
					new ExpectedMethod( //
							"toString", new PossibleReturnTypes(String.class), "toString"), //
					new ExpectedMethod( //
							"metod för att kontrollera om samlingen är tom (tillåten, men ej i instruktionerna)",
							new PossibleReturnTypes(boolean.class), ".*[eE]mpty.*"), //
					new ExpectedMethod( //
							"metod för att kontrollera storleken på samligen (tillåten, men ej i instruktionerna)",
							new PossibleReturnTypes(int.class), ".*[sS]ize.*"), //
			};

			return Stream.of(//
					Arguments.of(Dog.class, expectedMethodsInDog), //
					Arguments.of(Owner.class, expectedMethodsInOwner), //
					Arguments.of(DogSorter.class, expectedMethodsInDogSorter), //
					Arguments.of(OwnerCollection.class, expectedMethodsInOwnerCollection)//
			);
		}

		private static record PossibleReturnTypes(Class<?>... types) {

			public boolean contains(Class<?> type) {
				for (var possibleType : types) {
					if (possibleType.equals(type))
						return true;
				}

				return false;
			}

		}

		private static record ExpectedMethod(String description, PossibleReturnTypes possibleReturnTypes,
				String namePattern, Class<?>... parameterTypes) {

			public boolean matches(Method method) {
				if (!possibleReturnTypes.contains(method.getReturnType()))
					return false;

				if (!method.getName().matches(namePattern))
					return false;

				return Arrays.equals(parameterTypes, method.getParameterTypes());
			}

			@Override
			public final String toString() {
				return description;
			}

		}
	}

}
