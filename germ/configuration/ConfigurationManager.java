/**
 * 
 */
package germ.configuration;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 * Singleton klasa za koja omogucuje eksterna podesavanja. Cita podesavanja iz
 * XML datoteke i ucitava ih u HashMapu.
 */
public class ConfigurationManager {
	/**
	 * Podrazumevana konfiguracija
	 */
	private static String deaultConfig = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
			+ "<germ>\n"
			+ "<configParam name=\"positionSize\">100 100</configParam>\n"
			+ "<configParam name=\"requirementStrokeColor\">0 0 0</configParam>\n"
			+ "<configParam name=\"requirementSize\">80 100</configParam>\n"
			+ "<configParam name=\"logFileName\">germ.log</configParam>\n"
			+ "<configParam name=\"argumentSize\">80 80</configParam>\n"
			+ "<configParam name=\"positionFillPrimColor\">255 255 255</configParam>\n"
			+ "<configParam name=\"decisionStrokeColor\">0 0 0</configParam>\n"
			+ "<configParam name=\"logToFile\">true</configParam>\n"
			+ "<configParam name=\"decisionStrokeThickness\">3.0</configParam>\n"
			+ "<configParam name=\"undoRedoStackSize\">500</configParam>\n"
			+ "<configParam name=\"decisionFillSecColor\">255 255 255</configParam>\n"
			+ "<configParam name=\"stakeholderSize\">80 100</configParam>\n"
			+ "<configParam name=\"assumptionFillSecColor\">255 255 255</configParam>\n"
			+ "<configParam name=\"requirementFillPrimColor\">255 255 255</configParam>\n"
			+ "<configParam name=\"topicStrokeColor\">0 0 0</configParam>\n"
			+ "<configParam name=\"argumentFillPrimColor\">255 255 255</configParam>\n"
			+ "<configParam name=\"positionStrokeThickness\">3.0</configParam>\n"
			+ "<configParam name=\"assumptionSize\">60 60</configParam>\n"
			+ "<configParam name=\"defaultWorspace\">false</configParam>\n"
			+ "<configParam name=\"language\">ENG</configParam>\n"
			+ "<configParam name=\"assumptionStrokeThickness\">3.0</configParam>\n"
			+ "<configParam name=\"topicFillPrimColor\">255 255 255</configParam>\n"
			+ "<configParam name=\"stakeholderFillPrimColor\">255 255 255</configParam>\n"
			+ "<configParam name=\"maxStatusLabel0Length\">80</configParam>\n"
			+ "<configParam name=\"topicSize\">100 50</configParam>\n"
			+ "<configParam name=\"argumentFillSecColor\">255 255 255</configParam>\n"
			+ "<configParam name=\"showGrid\">true</configParam>\n"
			+ "<configParam name=\"topicStrokeThickness\">3.0</configParam>\n"
			+ "<configParam name=\"programName\">GERM</configParam>\n"
			+ "<configParam name=\"theme\">blue/</configParam>\n"
			+ "<configParam name=\"stakeholderStrokeColor\">0 0 0</configParam>\n"
			+ "<configParam name=\"assumptionStrokeColor\">0 0 0</configParam>\n"
			+ "<configParam name=\"positionFillSecColor\">255 255 255</configParam>\n"
			+ "<configParam name=\"argumentStrokeThickness\">3.0</configParam>\n"
			+ "<configParam name=\"decisionFillPrimColor\">255 255 255</configParam>\n"
			+ "<configParam name=\"positionStrokeColor\">0 0 0</configParam>\n"
			+ "<configParam name=\"defaultWorkspace\">false</configParam>\n"
			+ "<configParam name=\"topicFillSecColor\">255 255 255</configParam>\n"
			+ "<configParam name=\"argumentStrokeColor\">0 0 255</configParam>\n"
			+ "<configParam name=\"cursorSize\">24</configParam>\n"
			+ "<configParam name=\"decisionSize\">60 60</configParam>\n"
			+ "<configParam name=\"treeSize\">200</configParam>\n"
			+ "<configParam name=\"stakeholderFillSecColor\">255 255 255</configParam>\n"
			+ "<configParam name=\"stakeholderStrokeThickness\">3.0</configParam>\n"
			+ "<configParam name=\"requirementStrokeThickness\">3.0</configParam>\n"
			+ "<configParam name=\"logToConsole\">true</configParam>\n"
			+ "<configParam name=\"assumptionFillPrimColor\">255 255 255</configParam>\n"
			+ "<configParam name=\"requirementFillSecColor\">255 255 255</configParam>\n"
			+ "<configParam name=\"arrowMoveStep\">10</configParam>\n"
			+ "<configParam name=\"nodeShadow\">true</configParam>\n"
			+ "<configParam name=\"background\">255 255 255</configParam>\n"
			+ "<configParam name=\"gridColor\">230 230 230</configParam>\n"
			+ "<configParam name=\"lasoOverNodeShow\">true</configParam>\n"
			+ "<configParam name=\"animationEnabled\">true</configParam>\n"
			+ "</germ>\n";
	/**
	 * Naziv fajla u koji se cuvaju podesavanja.
	 */
	private static String fileName;
	private static File configFile;
	static {
		fileName = "germconf.xml";
		configFile = new File(System.getProperty("user.home") + File.separator
				+ fileName);
		if (!configFile.exists()) {
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(
						configFile));
				out.write(deaultConfig);
				
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Jedina instanca klase.
	 */
	private static ConfigurationManager instance;

	static {
		if (instance == null)
			instance = new ConfigurationManager();
	}

	private ConfigurationManager() {
		readFile();
	}

	public static ConfigurationManager getInstance() {
		return instance;
	}

	/**
	 * Metoda zapisuje podesavanja iz HeshMape u XML datoteku.
	 */
	public void writeFile() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;

		try {

			db = dbf.newDocumentBuilder();
			Document dom = db.newDocument();

			Element rootEl = dom.createElement("germ");
			dom.appendChild(rootEl);

			for (String s : configurationParameters.keySet()) {
				Element e = dom.createElement("configParam");
				e.setAttribute("name", s);
				Text val = dom.createTextNode((String) configurationParameters
						.get(s));
				e.appendChild(val);
				rootEl.appendChild(e);
			}

			OutputFormat format = new OutputFormat(dom);
			format.setIndenting(true);

			XMLSerializer serializer = new XMLSerializer(new FileOutputStream(
					configFile), format);
			serializer.serialize(dom);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metoda cita podesavanja iz XML datoteke i smesta ih u HeshMapu.
	 */
	private void readFile() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		// Using factory get an instance of document builder
		DocumentBuilder db;
		try {

			db = dbf.newDocumentBuilder();

			// parse using builder to get DOM representation of the XML file
			Document dom = db.parse(configFile);

			Element rootEl = dom.getDocumentElement();
			NodeList nl = rootEl.getElementsByTagName("configParam");
			for (int i = 0; i < nl.getLength(); i++) {
				String key = ((Element) nl.item(i)).getAttribute("name");
				String value = ((Element) nl.item(i)).getTextContent();
				configurationParameters.put(key, value);
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * HeshMapa u kojoj se nalaze sva podesavanja.
	 */
	private Hashtable<String, String> configurationParameters = new Hashtable<String, String>();

	public String getConfigParameter(String s) {
		return configurationParameters.get(s);
	}

	public String getString(String key) {
		return configurationParameters.get(key);
	}

	public void setString(String key, String value) {
		configurationParameters.put(key, value);
	}

	public int getInt(String key) {
		return Integer.parseInt(configurationParameters.get(key));
	}

	public void setInt(String key, Integer value) {
		configurationParameters.put(key, value.toString());
	}

	public Dimension getDimension(String key) {
		String dimension = configurationParameters.get(key);
		String dimensions[] = dimension.split(" ");
		int width = Integer.parseInt(dimensions[0]);
		int height = Integer.parseInt(dimensions[1]);
		return new Dimension(width, height);
	}

	public void setDimention(String key, Dimension value) {
		configurationParameters.put(key, value.width + " " + value.height);
	}

	public Color getColor(String key) {
		String color = configurationParameters.get(key);
		String RGB[] = color.split(" ");
		int R = Integer.parseInt(RGB[0]);
		int G = Integer.parseInt(RGB[1]);
		int B = Integer.parseInt(RGB[2]);
		return new Color(R, G, B);
	}

	public void setColor(String key, Color value) {
		configurationParameters.put(key, value.getRed() + " "
				+ value.getGreen() + " " + value.getBlue());
	}

	public float getFloat(String key) {
		return Float.parseFloat(configurationParameters.get(key));
	}

	public void setFloat(String key, Float value) {
		configurationParameters.put(key, value.toString());
	}

	public boolean getBoolean(String key) {
		return Boolean.parseBoolean(configurationParameters.get(key));
	}

	public void setBoolean(String key, Boolean value) {
		configurationParameters.put(key, value.toString());
	}

	public void setStringArray(String key, ArrayList<String> value) {
		String rez = "";
		for (String v : value) {
			rez = rez + v + " ";
		}
		configurationParameters.put(key, rez);
	}

	public ArrayList<String> getStringArray(String key) {
		String value = configurationParameters.get(key);
		String[] parts = value.split(" ");
		ArrayList<String> rez = new ArrayList<String>();
		for (String s : parts) {
			rez.add(s);
		}
		return rez;
	}

}
