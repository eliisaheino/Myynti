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

/**
 * Servlet implementation class Asiakkaat
 */
@WebServlet("/Asiakkaat/*")
public class Asiakkaat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public Asiakkaat() {
        super();
        System.out.println("Asiakas.Asiakkaat()");
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doGet()");
		String pathInfo = request.getPathInfo(); //Haetaan kutsun polkutiedot, esim. /asiakkaat/nimi
		System.out.println("polku: "+pathInfo); //Kirjoittaa tietoa konsoliin
		String hakusana = "";
		//pathInfo (hakusanaa ei ole annettu) ei ole:
		if(pathInfo!= null) {
			hakusana = pathInfo.replace("/", "");
		}
		
		Dao dao = new Dao();
		ArrayList<AsiakasMalli> asiakkaat = dao.listaaKaikki(hakusana);//kutsuu parametrillista konstruktoria
		System.out.println(asiakkaat); //Kirjoittaa tietoa konsoliin
		//Laitetaan uusi JSONObjekti nimelt‰ asiakkaat, joka pit‰‰ sis‰ll‰‰n asiakkaat arraylistin
		String strJSON = new JSONObject().put("asiakkaat", asiakkaat).toString();
		//Kirjoitetaan t‰m‰ serveletin http rajapintaan
		//M‰‰ritet‰‰n tulevan kirjoituksen tyypiksi application Json
		response.setContentType("application/json");
		//Tehd‰‰n PrintWriter nimelt‰‰n out
		PrintWriter out = response.getWriter();
		//K‰sket‰‰n out-printteri‰ kirjoittamaan strJSON ulos
		out.println(strJSON); //T‰m‰ kirjoittaa selaimeen
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doPost()");
		//T‰m‰ lˆytyy control luokasta
		JSONObject jsonObj = new JsonStrToObj().convert(request);
		//tehd‰‰n uusi asiakas
		AsiakasMalli asiakas = new AsiakasMalli();
		//Asetetaan arvot
		asiakas.setEtunimi(jsonObj.getString("etunimi"));
		asiakas.setSukunimi(jsonObj.getString("sukunimi"));
		asiakas.setPuhelin(jsonObj.getString("puhelin"));
		asiakas.setSposti(jsonObj.getString("sposti"));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Dao dao = new Dao();
		if(dao.lisaaAsiakas(asiakas)) {
			out.println("{\"response\":1}"); //lis‰‰minen onnistui response:1	
		}else {
			out.println("{\"response\":0}"); //lis‰‰minen ep‰onnistui	response:0
		}
	}
	

	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doPut()");
	}

	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doDelete()");
		String pathInfo = request.getPathInfo(); //Haetaan kutsun polkutiedot
		System.out.println("polku: "+pathInfo);
		String asiakas_id = pathInfo.replace("/", ""); //Poistetaan /polun edest‰
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Dao dao = new Dao();
		if(dao.poistaAsiakas(asiakas_id)) {
			out.println("{\"response\":1}"); //poistaminen onnistui response:1	
		}else {
			out.println("{\"response\":0}"); //poistaminen ep‰onnistui	response:0
		}	
	}

}
