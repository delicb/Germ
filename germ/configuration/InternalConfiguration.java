package germ.configuration;



/**
 * Klasa za cuvanje konfiguracionih parametara koji su konfigurabilni uopsteno,
 * ali nije dozvoljeno da ih korisnici programa menjaju i raznih konstanti koje
 * se koriste u celom programu
 * 
 */
public class InternalConfiguration {

	/**
	 * Razlikovanje horizontalnog i vertikalnog scroll-a. Mozda i nije moralo da
	 * stoji ovde...
	 */
	public static final int VERTICAL_SCROLL = 1;
	public static final int HORIZONTAL_SCROLL = 2;
	public static final double ZOOM_SLIDER_DIV_FACTOR = 2000;

	/**
	 * Stepen tolerancije prilikom klika na vezu.
	 */
	public static float LINK_HIT_TOLERANCE = 7.0f;

	/**
	 * Vrednosti koje odredjuju gustinu grida u zavisnosti od zoom faktora.
	 */
	public static final int SCALE_STEP_DETAIL_GRID = 3;

	/**
	 * Gustina mreze na uvecanju.
	 */
	public static final int GRID_ZOOMEDIN_DENSITY = 25;
	/**
	 * Neutralna gustina mreze.
	 */
	public static final int GRID_DEFAULT_DENSITY = 100;
	/**
	 * Gustina mreze pri umanjenju.
	 */
	public static final int GRID_ZOOMEDOUT_DENSITY = 300;
	/**
	 * Gustina mreze pri velikom umanjenju.
	 */
	public static final int GRID_VERY_ZOOMEDOUT_DENSITY = 2 * GRID_ZOOMEDOUT_DENSITY;
	/**
	 * Granica promene gustine mreze 1.
	 */
	public static final float GRID_ZOOM_LEVEL_1 = 0.25f;
	/**
	 * Granica promene gustine mreze 2.
	 */
	public static final float GRID_ZOOM_LEVEL_2 = 0.10f;

	/**
	 * Velicina brakepointa linkova za prikak.
	 */
	public static final float BREAKPOINT_SIZE = 7;

	/**
	 * Sirina pravougaonika oko linka koji hvata mouse click za selekciju.
	 */
	public static final float SELECTION_RECKTANGLE_SIZE = 10;

	/**
	 * Ogranicenje zumiranja kanvasa.
	 */
	public static final float ZOOM_LIMIT = 10f;
	/**
	 * Korak zumiranja kanvasa.
	 */
	public static final float ZOOM_FACTOR = 1.2f;
	/**
	 * Korak translacije kanvasa po X osi.
	 */
	public static final float TRANSLATION_FACTOR_X = 25;
	/**
	 * Korak translacije kanvasa po Y osi.
	 */
	public static final float TRANSLATION_FACTOR_Y = 25;

	/**
	 * Vreme u milisekundama koliko ce scroll thread spavati izmedju pomeranja
	 * canvasa. Sto je manji broj to ce brze raditi scroll, ali ne treba biti
	 * previse mali broj, zbog resursa...
	 */
	public static final int SCROLL_THREAD_SLEEP = 50;
	/**
	 * Faktor kojim se mnozi pomeranje kanvasa u scroll threadu. Ako je ovaj
	 * broj veci dobija se efekat kao da je SCROLL_THREAD_SLEEP manji, ali ako
	 * se pretera sa povecavanjem jedne i druge vrednosti dobije se efekat
	 * skokovitog scroll-a sto bas i ne izgleda lepo.
	 */
	public static final int SCROLL_THREAD_FACTOR = 1;

	/**
	 * Da li je u toku razvoj? Ako jeste neke stvari ce biti iskljucene...
	 */
	public static final boolean DEVELOPMENT_IN_PROGRES = false;

	/**
	 * Extenzija dijagrama.
	 */
	public static final String DIAGRAM_EXTENSION = "drm";
	/**
	 * Extenzija arhive.
	 */
	public static final String ARCHIVE_EXTENSION = "gar";

	/**
	 * Snap tolerancija
	 */
	public static final int SNAP_TOLERANCE = 14;

	/**
	 * Snap Node increment
	 */
	public static final int SNAP_NODE_INC = 6;

	/**
	 * Vreme (u milisekundama) izmedju menjanja stanja progress bara pri
	 * pokretanju programa (samo zbog efekta)
	 */
	public static final int PROGRESS_BAR_SLEEP_TIME = 100;
	
	/**
	 * Preciznost kojom zelimo da element koji se trazi bude centriran
	 */
	public static final int ANIMATE_TRANSLATION_PRECISION = 5;
	/**
	 * Razmak izmedju elemenata kada se radi distribute
	 */
    public static final int DISTRIBUTE_DISTANCE = 30;
    
}
