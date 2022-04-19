<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<title>Asiakkaat</title>
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
				<th class="oikealle">Hakusana:</th>
				<th colspan="2"><input type="text" id="hakusana"></th>
				<th><input type="button" value="hae" id="hakunappi"></th>
			</tr>
			<tr>
				<th>Etunimi</th>
				<th>Sukunimi</th>
				<th>Puhelin</th>
				<th>S�hk�posti</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
<script>
$ (document).ready(function()){
	haeAsiakkaat();
	//Tehd��n hakunapille click-funktio t�h�n
	$("#hakunappi").click(function() {
		haeAsiakkaat();
	});
	//Funktio n�pp�imist�n painallukselle hakukent�ss�
	$(document.body).on("keydown", function (event){
		if(event.which == 13){ //13= Enteri� painettu, ajetaan haku
			haeAsiakkaat();
		}
	});
	//Vied��n kursori hakusana-kentt��n sivun latauksen yhteydess�
	$("#hakusana").focus();

});

//Tehd��n funktio, jolla voidaan hakea asiakkaiden tiedot backendist� ja tuoda ne frontend-puolelle	
function haeAsiakkaat() {
	$("#listaus tbody").empty(); //Listauksen tyhjennys ennen seuraavaa hakua	
	$.ajax({
		url : "asiakkaat/"+$("#hakusana").val(), //Kutsuu get-metodia mutta laittaa mukaan my�s hakusanan
		type : "GET",
		dataType : "json",
		success : function(result) {//Funktio palauttaa tiedot json-objektina result-kohtaan		
			$.each(result.autot, function(i, field) {
				var htmlStr;
				htmlStr += "<tr>";
				htmlStr += "<td>" + field.etunimi + "</td>"; //sarakkeiden sulkeminen
				htmlStr += "<td>" + field.sukunimi + "</td>";
				htmlStr += "<td>" + field.puhelin + "</td>";
				htmlStr += "<td>" + field.sposti + "</td>";
				htmlStr += "</tr>";//rivin sulkeminen
				$("#listaus tbody").append(htmlStr);
			});

		}
	});
}
</script>

</body>
</html>