function validar() {
    let nome = frmContato.nome.value;
    let fone = frmContato.fone.value;
    let email = frmContato.email.value;

    if (nome == "") {
        alert("Preencha o campo nome!")
        frmContato.nome.focus()
    } else if (fone == "") {
        alert("Preencha o campo telefone!")
        frmContato.fone.focus()
    } else if (email == "") {
        alert("Preencha o campo email!")
        frmContato.email.focus()
    }
    else {
        document.forms["frmContato"].submit()
    }



}

function validar2() {

    let nome = frmCadastroUsuario.nome.value;
    let fone = frmCadastroUsuario.email.value;
    let senha = frmCadastroUsuario.senha.value;
    if (nome == "") {
        alert("Preencha o campo nome!")
        frmCadastroUsuario.nome.focus()
    } else if (fone == "") {
        alert("Preencha o campo telefone!")
        frmCadastroUsuario.fone.focus()
    } else if (senha == "") {
        alert("Preencha o campo senha!")
        frmCadastroUsuario.senha.focus()
    }
    else {
        document.forms["frmCadastroUsuario"].submit()
    }

}

function validar3() {


    let email = frmLogarUsuario.email.value;
    let senha = frmLogarUsuario.senha.value;
    if (email == "") {
        alert("Preencha o campo email!")
        frmLogarUsuario.email.focus()
    } else if (senha == "") {
        alert("Preencha o campo senha!")
        frmLogarUsuario.senha.focus()
    }
    else {
        document.forms["frmLogarUsuario"].submit()
    }

}