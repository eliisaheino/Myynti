<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css">
<title>Listaa asiakkaat</title>
</head>
<body>
	<table id="listaus">
		<thead>
		      <tr>
		      <th colspan="5"class="oikealle"><span id ="uusiAsiakas">Lis?? uusi asiakas</span></th>
		      </tr>
			<tr>
				<th class="oikealle">Hakusana:</th>
				<th colspan="3"><input type="text" id="hakusana"></th>
				<th><input type="button" value="hae" id="hakunappi"></th>
			</tr>
			<tr>
				<th>Etunimi</th>
				<th>Sukunimi</th>
				<th>Puhelin</th>
				<th>Sposti</th>
				<th>&nbsp;</th>	
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
<script>
$ (document).ready(function() {
	
	//painike uuden asiakkaan lis?ykseen
	$ ("#uusiAsiakas").click(function(){
		document.location="lisaaasiakkaat.jsp";
	});
	
	
	$("#hakunappi").click(function() {
		haeAsiakkaat();
	});
	
	//Funktio n?pp?imist?n painallukselle hakukent?ss?
	$(document.body).on("keydown", function (event){
		if(event.which == 13){ //13= Enteri? painettu, ajetaan haku
			haeAsiakkaat();
		}
	});
	
	//Vied??n kursori hakusana-kentt??n sivun latauksen yhteydess?
	$("#hakusana").focus();
	haeAsiakkaat();

});

//Tehd??n funktio, jolla voidaan hakea asiakkaiden tiedot backendist? ja tuoda ne frontend-puolelle	
function haeAsiakkaat() {
	$("#listaus tbody").empty(); //Listauksen tyhjennys ennen seuraavaa hakua	
	$.getJSON({
		url : "Asiakkaat/"+$("#hakusana").val(), //Kutsuu get-metodia mutta laittaa mukaan my?s hakusanan
		type : "GET",
		dataType : "json",
		success : function(result) {//Funktio palauttaa backend listan asiakkaista(asiakkaat.java)tiedot json-objektina result-kohtaan		
			$.each(result.asiakkaat, function(i, field) {
				var htmlStr;
				htmlStr += "<tr id='rivi_"+ field.asiakas_id+"'>";
				htmlStr += "<td>" + field.etunimi + "</td>"; //sarakkeiden sulkeminen
				htmlStr += "<td>" + field.sukunimi + "</td>";
				htmlStr += "<td>" + field.puhelin + "</td>";
				htmlStr += "<td>" + field.sposti + "</td>";
				htmlStr += "<td><a href= 'muutaasiakas.jsp?asiakas_id="+field.asiakas_id+"'>Muuta</a>&nbsp;";
				htmlStr += "<td><span class= 'poista' onclick=poista("+field.asiakas_id+",'"+field.etunimi+"','"+field.sukunimi+"')>Poista</span></td>";
				htmlStr += "</tr>";
				$("#listaus tbody").append(htmlStr);
			});

		}});
}
//Asiakkaan poistaminen
function poista(asiakas_id, etunimi, sukunimi){
	if(confirm("Poista asiakas " + etunimi +" "+ sukunimi +"?")){
		$.ajax({url:"asiakkaat/"+asiakas_id, type:"DELETE",dataType:"json", success:function(result) { //result on joko {"response:1"} tai {"response:0"}
	        if(result.response==0){
	        	$("#ilmo").html("Asiakkaan poisto ep?onnistui.");
	        }else if(result.response==1){
	        	$("#rivi_"+asiakas_id).css("background-color", "red"); //V?rj?t??n poistettu rivi
	        	alert("Asiakkaan " + etunimi +" "+ sukunimi+ " poisto onnistui.");
				haeAsiakkaat();  //Poistamisen j?lkeen haetaan j?ljelle j??neet asiakkaat    	
			}
	    }});
	}

}
</script>
</body>
</html>