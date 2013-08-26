/**
 * 
 */
package germ.util;

import germ.configuration.InternalConfiguration;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

/**
 * Pri prvom pozivu učitava sve kursore i smešta ih u HashTabelu sa ključem koji
 * je isti kao ime fajla koji sadrći kursor. Tom prilikom ih skalira u
 * zavisnosti od sistema na kome se radi. Moguće je doći do svih kursora na
 * osnovu imena kursora, kao i do trenutnog kursora.
 * 
 */
public class Cursors {
	/**
	 * Poslednji kursor za koji je pozvana metoda getCursor. Potrebno je radi
	 * vraćanja kursora na staro nakon menjanja u strelica pri prelasku preko
	 * handlova za resize.
	 */
	public static Cursor currentCursor;

	/**
	 * HashTabela svih kursora koje program sadrži. Puni se prilikom prvog
	 * obraćanja klasi. Ključ je ime kursora (isto kao ime fajla), a podatak je 
	 * instanca klase Cursos.
	 */
	private static Hashtable<String, Cursor> cursors = new Hashtable<String, Cursor>();
	
	/**
	 * Inicijalizacija kursora iz foldera germ/actions/cursors koji je deo germa. 
	 * Čita svaki kursor, zatim ga skalira na određenu veličinu (zbog različitog prizaka
	 * u Windows-u i Linuxu (na MaxOS X-u nismo testiali :) ). Zatim tako skaliran kursor smešta
	 * u {@link #cursors [cursors]}.
	 */
	static {
		if (!InternalConfiguration.DEVELOPMENT_IN_PROGRES) {
			try {
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				File f = new File("germ/actions/cursors/");
				for (String cursorImage : f.list()) {
					File newCursor = new File("germ/actions/cursors/"
							+ cursorImage);
					if (!newCursor.isDirectory()) {
						BufferedImage rawCursor = ImageIO.read(newCursor);

						float scaleFactor;

						if (System.getProperty("os.name").indexOf("Linux") != -1)
							scaleFactor = 0.4f;
						else
							scaleFactor = 0.7f;

						BufferedImage result = new BufferedImage(rawCursor
								.getWidth(), rawCursor.getHeight(),
								BufferedImage.TYPE_4BYTE_ABGR);
						result.createGraphics();
						Graphics2D g = (Graphics2D) result.getGraphics();
						g.drawRenderedImage(rawCursor, AffineTransform
								.getScaleInstance(scaleFactor, scaleFactor));
						g.dispose();

						Cursor customCursor = toolkit.createCustomCursor(
								result, new Point(0, 0), cursorImage);
						cursors.put(cursorImage.split("\\.")[0], customCursor);
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Vraća kursor na osnovu imena kursora.
	 * @param cursor Ime kursora
	 * @return Kursor sa traćenim imenom.
	 */
	public static Cursor getCursor(String cursor) {
		if (InternalConfiguration.DEVELOPMENT_IN_PROGRES)
			return Cursor.getDefaultCursor();
		if (cursors.containsKey(cursor)) {
			currentCursor = cursors.get(cursor);
			return currentCursor;
		} else {
			return Cursor.getDefaultCursor();
		}
	}

	/**
	 * Vraca poslednji vracen kursor iz metode {@link #getCursor(String) getCursor}
	 * @return
	 */
	public static Cursor getCurrentCursor() {
		return currentCursor;
	}
}
