<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<script src="scripts/main.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.15.0/jquery.validate.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css">
<title>Lisää uusi asiakas</title>
</head>
<body>
	<form id="tiedot">
		<table>
			<thead>
				<tr>
					<th colspan="5" class="oikealle"><span id="takaisin">Takaisin
							listaukseen</span></th>
				</tr>
				<tr>
					<th>Etunimi</th>
					<th>Sukunimi</th>
					<th>Puhelin</th>
					<th>Sposti</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><input type="text" name="etunimi" id="etunimi"></td>
					<td><input type="text" name="sukunimi" id="sukunimi"></td>
					<td><input type="text" name="puhelin" id="puhelin"></td>
					<td><input type="text" name="sposti" id="sposti"></td>
					<td><input type="submit" id="tallenna" value="Lisää"></td>
				</tr>
			</tbody>
		</table>
	</form>
	<span id="ilmo"></span>

<script>

$ (document).ready(function() {
	
	$("#takaisin").click(function() {
	document.location="listaaasiakkaat.jsp";	
});
	
//Kohdistuu lomakkeen <form> id:tiedot-kohtaan.Lomakkeen tietojen tarkistus
$("#tiedot").validate({						
	rules: {
		etunimi:  {
			required: true,
			minlength: 2				
		},	
		sukunimi:  {
			required: true,
			minlength: 2				
		},
		puhelin:  {
			required: true,
			number: true,
			minlength: 10,
			maxlength: 20,
		},	
		sposti:  {
			required: true,
			minlength: 9,
			maxlength: 50,
			
		}	
	},
	//Jos joku annetuista arvoista ei toteudu tulee seuraavat viestit
	messages: {
		etunimi: {     
			required: "Etunimi puuttuu",
			minlength: "Liian lyhyt"			
		},
		sukunimi: {
			required: "Sukunimi puuttuu",
			minlength: "Liian lyhyt"
		},
		puhelin: {
			required: "Anna puhelinnumero",
			number: "Pitää olla numero",
			minlength: "Liian lyhyt",
			maxlength: "Liian pitkä"
		},
		sposti: {
			required: "Anna kelvollinen sähköpostiosoite",
			minlength: "Liian lyhyt",
			maxlength: "Liian pitkä"
			
		}
	},	
	
	submitHandler: function(form) {	
		lisaaTiedot();
	}		
}); //validointi loppuu tähän

//Viedään kursori etunimi kenttään sivun latauksen yhteydessä
$("#etunimi").focus();

});

//funktio tietojen lisäämistä varten. Kutsutaan backin POST-metodia ja välitetään kutsun mukana uudet tiedot json-stringinä.
//POST /asiakkaat/
function lisaaTiedot() {	
	var formJsonStr = formDataJsonStr($("#tiedot").serializeArray()); //muutetaan lomakkeen tiedot json-stringiksi
	console.log(formJsonStr);
	//Kutsutaan Asiakkaat-backendia
	$.ajax({url:"Asiakkaat", data:formJsonStr, type:"POST", dataType:"json", success:function(result) { //result on joko {"response:1"} tai {"response:0"}       
		if(result.response==0){
  	$("#ilmo").html("Asiakkaan lisääminen epäonnistui.");
  }else if(result.response==1){			
  	$("#ilmo").html("Asiakkaan lisääminen onnistui.");
  	$("#etunimi", "#sukunimi", "#puhelin", "#sposti").val("");
		}
}});
	
}

</script>
</body>
</html>