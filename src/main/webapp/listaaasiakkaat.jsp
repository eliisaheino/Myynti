<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css">
<title>Listaa asiakkaat</title>
<style>
.oikealle {
	text-align: right;
}
</style>
</head>
<body>
	<table id="listaus">
		<thead>
		      <tr>
		      <th colspan="5"class="oikealle"><span id ="uusiAsiakas">Lisää uusi asiakas</span></th>
		      </tr>
			<tr>
				<th class="oikealle">Hakusana:</th>
				<th colspan="2"><input type="text" id="hakusana"></th>
				<th><input type="button" value="hae" id="hakunappi"></th>
			</tr>
			<tr>
				<th>Etunimi</th>
				<th>Sukunimi</th>
				<th>Puhelin</th>
				<th>Sähköposti</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
<script>
$ (document).ready(function() {
	
	//painike uuden asiakkaan lisäykseen
	$ ("#uusiAsiakas").click(function(){
		document.location="lisaaasiakkaat.jsp";
	});
	
	//Tehdään hakunapille click-funktio tähän
	haeAsiakkaat();
	$("#hakunappi").click(function() {
		haeAsiakkaat();
	});
	//Funktio näppäimistön painallukselle hakukentässä
	$(document.body).on("keydown", function (event){
		if(event.which == 13){ //13= Enteriä painettu, ajetaan haku
			haeAsiakkaat();
		}
	});
	//Viedään kursori hakusana-kenttään sivun latauksen yhteydessä
	$("#hakusana").focus();

});

//Tehdään funktio, jolla voidaan hakea asiakkaiden tiedot backendistä ja tuoda ne frontend-puolelle	
function haeAsiakkaat() {
	$("#listaus tbody").empty(); //Listauksen tyhjennys ennen seuraavaa hakua	
	$.getJSON({
		url : "asiakkaat/"+$("#hakusana").val(), //Kutsuu get-metodia mutta laittaa mukaan myös hakusanan
		type : "GET",
		dataType : "json",
		success : function(result) {//Funktio palauttaa backend listan asiakkaista(asiakkaat.java)tiedot json-objektina result-kohtaan		
			$.each(result.asiakkaat, function(i, field) {
				var htmlStr;
				htmlStr += "<tr>";
				htmlStr += "<td>" + field.etunimi + "</td>"; //sarakkeiden sulkeminen
				htmlStr += "<td>" + field.sukunimi + "</td>";
				htmlStr += "<td>" + field.puhelin + "</td>";
				htmlStr += "<td>" + field.sposti + "</td>";
				htmlStr += "<td><span class= 'poista'onclick = poista ('"+field.etunimi+sukunimi+"')>Poista</span></td>";
				htmlStr += "</tr>";//rivin sulkeminen
				$("#listaus tbody").append(htmlStr);
			});

		}});
}
//Asiakkaan poistaminen
function poista(asiakas_id){
	if(confirm("Poista asiakas " + asiakas_id + "?")){
		$.ajax({url:"asiakkaat/"+asiakas_id, type:"DELETE",dataType:"json", success:function(result) { //result on joko {"response:1"} tai {"response:0"}
	        if(result.response==0){
	        	$("#ilmo").html("Asiakkaan poisto epäonnistui.");
	        }else if(result.response==1){
	        	$("#rivi_"+asiakas_id).css("background-color", "red"); //Värjätään poistettu rivi
	        	alert("Asiakkaan " + asiakas_id + " poisto onnistui.");
				haeAsiakkaat();  //Poistamisen jälkeen haetaan jäljelle jääneet asiakkaat    	
			}
	    }});
	}
}

</script>
</body>
</html>