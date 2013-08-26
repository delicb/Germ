/**
 * 
 */
package germ.util;

/**
 * Klasa se koristi za racunanje trenutnog broja koji se dodaljuje imenu noda prilikom
 * instanciranja novog objekta.
 *
 */
public class Counter {
	private long count = 0;

	/**
	 * Inicijalizuje counter proslednjenim brojem.
	 * @param count
	 */
	public Counter(long count) {
		super();
		this.count = count;
	}

	public Counter() {
	}
	/**
	 * Vraca sledeci broj ovog countera i uvecava svoju
	 * vrednost za jedan.
	 * @return Sledeci broj;
	 */
	public long next(){
		return ++count;
	}
}
