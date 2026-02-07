function validar(){
	let nome = frmContato.nome.value;
	let fone = frmContato.fone.value;
	let email = frmContato.email.value;
	
	 if(nome == ""){
		alert("Preencha o campo nome!")
		frmContato.nome.focus()
		} else if(fone == ""){
			alert("Preencha o campo telefone!")
			frmContato.fone.focus()
			} else if(email == ""){
				alert("Preencha o campo email!")
				frmContato.email.focus()
				}
				else{
					document.forms["frmContato"].submit()
					}
				
	
	
}