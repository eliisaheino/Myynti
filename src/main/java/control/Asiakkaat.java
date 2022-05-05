package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import model.AsiakasMalli;
import model.dao.Dao;

//REST-metodeja asiakastietojen hallintaan
@WebServlet("/Asiakkaat/*")
public class Asiakkaat extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Asiakkaat() {
		super();
		System.out.println("Asiakas.Asiakkaat()");
	}

	//Haetaan asiakkaat
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Asiakkaat.doGet()");
		String pathInfo = request.getPathInfo(); // Haetaan kutsun polkutiedot, esim. /asiakkaat/nimi
		System.out.println("polku: " + pathInfo); // Kirjoittaa tietoa konsoliin
		Dao dao = new Dao();
		ArrayList<AsiakasMalli> asiakkaat;
		String strJSON = "";
		if (pathInfo == null) {
			asiakkaat = dao.listaaKaikki();
			strJSON = new JSONObject().put("asiakkaat", asiakkaat).toString();
		} else if (pathInfo.indexOf("haeyksi") != -1) { 
			int asiakas_id = pathInfo.replace("/haeyksi/", ""); 
			AsiakasMalli asiakas = dao.etsiAsiakas(asiakas_id);
			if (asiakas == null) {
				strJSON = "{}";
			} else {
				JSONObject JSON = new JSONObject();
				JSON.put("etunimi", asiakas.getEtunimi());
				JSON.put("sukunimi", asiakas.getSukunimi());
				JSON.put("puhelin", asiakas.getPuhelin());
				JSON.put("sposti", asiakas.getSposti());
				strJSON = JSON.toString();
			}
		} else {
			String hakusana = pathInfo.replace("/", "");
			asiakkaat = dao.listaaKaikki(hakusana);
			strJSON = new JSONObject().put("asiakkaat", asiakkaat).toString();
		}
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.println(strJSON);
	}

	//Lis‰t‰‰n uusi asiakas
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Asiakkaat.doPost()");
		// T‰m‰ lˆytyy control luokasta
		JSONObject jsonObj = new JsonStrToObj().convert(request); // Muutetaan kutsun mukana tuleva json-string
																	// json-objektiksi
		// tehd‰‰n uusi asiakas
		AsiakasMalli asiakas = new AsiakasMalli();
		// Asetetaan arvot
		asiakas.setEtunimi(jsonObj.getString("etunimi"));
		asiakas.setSukunimi(jsonObj.getString("sukunimi"));
		asiakas.setPuhelin(jsonObj.getString("puhelin"));
		asiakas.setSposti(jsonObj.getString("sposti"));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Dao dao = new Dao();
		if (dao.lisaaAsiakas(asiakas)) {
			out.println("{\"response\":1}"); // lis‰‰minen onnistui response:1
		} else {
			out.println("{\"response\":0}"); // lis‰‰minen ep‰onnistui response:0
		}
	}

	//Muutetaan asiakkaan tiedot
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Asiakkaat.doPut()");
		JSONObject jsonObj = new JsonStrToObj().convert(request); 
		int vanhaasiakas_id = jsonObj.getInt("vanhaasiakas_id");
		AsiakasMalli asiakas = new AsiakasMalli();
		asiakas.setEtunimi(jsonObj.getString("etunimi"));
		asiakas.setSukunimi(jsonObj.getString("sukunimi"));
		asiakas.setPuhelin(jsonObj.getString("puhelin"));
		asiakas.setSposti(jsonObj.getString("sposti"));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Dao dao = new Dao();
		if (dao.muutaAsiakas(asiakas, vanhaasiakas_id)) { // metodi palauttaa true/false
			out.println("{\"response\":1}"); //muuttaminen onnistui {"response":1}
		} else {
			out.println("{\"response\":0}"); //muuttaminen ep‰onnistui {"response":0}
		}
	}

	//Poistetaan asiakas
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Asiakkaat.doDelete()");
		String pathInfo = request.getPathInfo(); // Haetaan kutsun polkutiedot
		System.out.println("polku: " + pathInfo);
		int asiakas_id = Integer.parseInt(pathInfo.replace("/", "")); // Poistetaan /polun edest‰
		Dao dao = new Dao();
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		if (dao.poistaAsiakas(asiakas_id)) {
			out.println("{\"response\":1}"); // poistaminen onnistui response:1
		} else {
			out.println("{\"response\":0}"); // poistaminen ep‰onnistui response:0
		}
	}

}
