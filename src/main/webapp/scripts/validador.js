function validar(){
	let nome = frmContato.nome.value;
	let fone = frmContato.fone.value;
	let email = frmContato.email.value;
	
	if(nome != "" && fone != "" && email != ""){
		document.forms["frmContato"].submit()
	}
	
	
}