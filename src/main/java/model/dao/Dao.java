package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.AsiakasMalli;





public class Dao {
	private Connection con=null;
	private ResultSet rs = null;
	private PreparedStatement stmtPrep=null; 
	private String sql;
	private String db ="Myynti.sqlite";
	
	private Connection yhdista(){
    	Connection con = null;    	
    	String path = System.getProperty("catalina.base");    	
    	path = path.substring(0, path.indexOf(".metadata")).replace("\\", "/"); //Eclipsessa
    	//path += "/webapps/"; //Tuotannossa. Laita tietokanta webapps-kansioon
    	String url = "jdbc:sqlite:"+path+db;    	
    	try {	       
    		Class.forName("org.sqlite.JDBC");
	        con = DriverManager.getConnection(url);	
	        System.out.println("Yhteys avattu.");
	     }catch (Exception e){	
	    	 System.out.println("Yhteyden avaus epäonnistui.");
	        e.printStackTrace();	         
	     }
	     return con;
	}
	
	public ArrayList<AsiakasMalli> listaaKaikki(){
		ArrayList<AsiakasMalli> asiakkaat = new ArrayList<AsiakasMalli>();
		sql = "SELECT * FROM asiakkaat";       
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql);        		
        		rs = stmtPrep.executeQuery();   
				if(rs!=null){ //jos kysely onnistui
					//con.close();	
					//Jos tietoa löytyi tehdään ensin asiakas-olio
					//Annetaan sille arvot
					//Laitetaan luotu Asiakas add-metodilla arraylistiin
					while(rs.next()){
						AsiakasMalli asiakas = new AsiakasMalli();
						asiakas.setEtunimi(rs.getString(1)); //Hae taulun ensimmäisestä sarakkeesta arvo ja aseta se reknro paikalle
						asiakas.setSukunimi(rs.getString(2)); //Hae taulun toisesta sarakkeesta arvo ja aseta se merkin paikalle
						asiakas.setPuhelin(rs.getString(3));	 //Hae taulun kolmannesta sarakkeesta arvo ja aseta se mallin paikalle
						asiakas.setSposti(rs.getString(4));	//Hae taulun neljännestä sarakkeesta arvo ja aseta se vuoden paikalle
						asiakkaat.add(asiakas);
					}					
				}				
			}	
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return asiakkaat;
	}
	
	public ArrayList<AsiakasMalli> listaaKaikki(String hakusana){
		ArrayList<AsiakasMalli> asiakkaat = new ArrayList<AsiakasMalli>();
		sql = "SELECT * FROM asiakkaat WHERE etunimi LIKE ? or sukunimi LIKE ? or sposti LIKE ? or puhelin LIKE ?";       
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql);
				stmtPrep.setString(1, "%" + hakusana + "%");
				stmtPrep.setString(2, "%" + hakusana + "%");
				stmtPrep.setString(3, "%" + hakusana + "%");
        		rs = stmtPrep.executeQuery();   
				if(rs!=null){ //jos kysely onnistui
					//con.close();	
					//Jos tietoa löytyi tehdään ensin asiakas-olio
					//Annetaan sille arvot
					//Laitetaan luotu Asiakas add-metodilla arraylistiin
					while(rs.next()){
						AsiakasMalli asiakas = new AsiakasMalli();
						asiakas.setAsiakas_id(rs.getInt(1));
						asiakas.setEtunimi(rs.getString(2)); //Hae taulun ensimmäisestä sarakkeesta arvo ja aseta se reknro paikalle
						asiakas.setSukunimi(rs.getString(3)); //Hae taulun toisesta sarakkeesta arvo ja aseta se merkin paikalle
						asiakas.setPuhelin(rs.getString(4));	 //Hae taulun kolmannesta sarakkeesta arvo ja aseta se mallin paikalle
						asiakas.setSposti(rs.getString(5));	//Hae taulun neljännestä sarakkeesta arvo ja aseta se vuoden paikalle
						asiakkaat.add(asiakas);
						
					}					
				}				
			}	
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return asiakkaat;
	}
	
	//Metodi uuden asiakkaan lisäämistä varten
	public boolean lisaaAsiakas(AsiakasMalli asiakas){
		boolean paluuArvo=true;
		sql="INSERT INTO asiakkaat (etunimi,sukunimi,puhelin,sposti) VALUES (?,?,?,?)";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setString(1, asiakas.getEtunimi());
			stmtPrep.setString(2, asiakas.getSukunimi());
			stmtPrep.setString(3, asiakas.getPuhelin());
			stmtPrep.setString(4, asiakas.getSposti());
			stmtPrep.executeUpdate();
			//System.out.println("Uusin id on:" + stmtPrep.getGeneratedKeys().getInt(1);
	        con.close();
		} catch (Exception e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
	//Metodi asiakkaan poistamiseen
	public boolean poistaAsiakas(int asiakas_id){
		boolean paluuArvo=true;
		sql="DELETE FROM asiakkaat WHERE asiakas_id=?";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setInt(1, asiakas_id);	
			stmtPrep.executeUpdate();
	        con.close();
		} catch (Exception e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	//Metodi asiakkaan etsimiseen
	public AsiakasMalli etsiAsiakas(int asiakas_id) {
		AsiakasMalli asiakas = null;
		sql = "SELECT * FROM asiakkaat WHERE asiakas_id=?";       
		try {
			con=yhdista();
			if(con!=null){ 
				stmtPrep = con.prepareStatement(sql); 
				stmtPrep.setInt(1, asiakas_id);
        		rs = stmtPrep.executeQuery();  
        		if(rs.isBeforeFirst()){ 
        			rs.next();
        			asiakas = new AsiakasMalli();        			
        			asiakas.setEtunimi(rs.getString(1));
					asiakas.setSukunimi(rs.getString(2));
					asiakas.setPuhelin(rs.getString(2));
					asiakas.setSposti(rs.getString(2));
				}        		
			}	
			con.close();  
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return asiakas;		
	}
	
	public boolean muutaAsiakas(AsiakasMalli asiakas, int asiakas_id){
		boolean paluuArvo=true;
		sql="UPDATE asiakkaat SET etunimi=?, sukunimi=?, puhelin=?, sposti=? WHERE asiakas_id=?";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setString(1, asiakas.getEtunimi());
			stmtPrep.setString(2, asiakas.getSukunimi());
			stmtPrep.setString(3, asiakas.getPuhelin());
			stmtPrep.setString(4, asiakas.getSposti());
			stmtPrep.setInt(5, asiakas_id);
			stmtPrep.executeUpdate();
	        con.close();
		} catch (Exception e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
}
