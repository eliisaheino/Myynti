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
        System.out.println("Asiakkaat.Asiakkaat()");
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doGet()");
		
		String pathInfo = request.getPathInfo(); //Haetaan kutsun polkutiedot, esim. /asiakkaat/nimi
		System.out.println("polku: "+pathInfo);
		String hakusana = pathInfo.replace("/", "");
		
		Dao dao = new Dao();
		ArrayList<AsiakasMalli> asiakkaat = dao.listaaKaikki();
		//Laitetaan uusi JSONObjekti nimelt‰ asiakkaat, joka pit‰‰ sis‰ll‰‰n asiakkaat arraylistin
		String strJSON = new JSONObject().put("asiakkaat", asiakkaat).toString();
		
		//Kirjoitetaan t‰m‰ serveletin http rajapintaan
		//M‰‰ritet‰‰n tulevan kirjoituksen tyypiksi application Json
		response.setContentType("application/json");
		//Tehd‰‰n PrintWriter nimelt‰‰n out
		PrintWriter out = response.getWriter();
		//K‰sket‰‰n out-printteri‰ kirjoittamaan strJSON ulos
		out.println(strJSON);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doPost()");
	}

	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doPut()");
	}

	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doDelete()");

}
}