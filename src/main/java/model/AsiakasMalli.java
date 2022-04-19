package model;

public class AsiakasMalli {
	private String etunimi, sukunimi, puhelin, sposti;

	//Konstruktorit
	public AsiakasMalli() {
		super();	
	}

	public AsiakasMalli(String etunimi, String sukunimi, String puhelin, String sposti) {
		super();
		this.etunimi = etunimi;
		this.sukunimi = sukunimi;
		this.puhelin = puhelin;
		this.sposti = sposti;
	}

	//Getterit ja setterit
	public String getEtunimi() {
		return etunimi;
	}

	public void setEtunimi(String etunimi) {
		this.etunimi = etunimi;
	}

	public String getSukunimi() {
		return sukunimi;
	}

	public void setSukunimi(String sukunimi) {
		this.sukunimi = sukunimi;
	}

	public String getPuhelin() {
		return puhelin;
	}

	public void setPuhelin(String puhelin) {
		this.puhelin = puhelin;
	}

	public String getSposti() {
		return sposti;
	}

	public void setSposti(String sposti) {
		this.sposti = sposti;
	}

	//toString
	@Override
	public String toString() {
		return "AsiakasMalli [etunimi=" + etunimi + ", sukunimi=" + sukunimi + ", puhelin=" + puhelin + ", sposti="
				+ sposti + "]";
	}
	
	
	
	
	
	
	
	
	

}
