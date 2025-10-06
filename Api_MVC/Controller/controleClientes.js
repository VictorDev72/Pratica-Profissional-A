//FUNÇÃO DE CADASTRAR NO BANCO( OU SEJA CADASTRO)
const { mssql } = require("config/db");
const { console } = require("inspector");


async function addBD(event) {
    event.preventDefault();
    const url = 'http://localhost:8090/cadastro'

    const nome = document.getElementById('nome').value;
    const email = document.getElementById('email').value;
    const celular = document.getElementById('celular').value;
    const senha = document.getElementById('senha').value;
    const resp = document.getElementById('resp');
    console.log(nome, email, celular, senha)
    const options = {
        method: "POST",
        body: JSON.stringify({
            nome: nome,
            email: email,
            celular: celular,
            senha: senha
        }),
        headers: { 'Content-type': 'application/json' },
    }

    try {
        const result = await fetch(url, options)
        if (result.ok) {
            console.log("Cadastro realizado com sucesso");
            resp.innerHTML = ('Cadastro realizado com sucesso');
            resp.style.color = 'green'

        }
        else {
            console.log("Erro de cadastro não valido ");
            resp.innerHTML = ('Erro de cadastro não valido');
            resp.style.color = 'red'
        }
    }
    catch (error) {
        console.error("Erro na inserção", error.message);
        resp.innerHTML = ("Erro na inserção");
        resp.style.color = 'red'


    }

    //FUNÇÃO DE LOGIN
}

function login() {
    const nome = document.getElementById('nome').value.trim();
    const email = document.getElementById('email').value.trim();
    const celular = document.getElementById('celular').value.trim();
    const senha = document.getElementById('senha').value.trim();
    const resp = document.getElementById('resp');

    if (!nome || !email || !celular || !senha) {
        resp.innerText = "Preencha todos os campos.";
        resp.style.color = "red";
        return;
    }

    if (!email.includes("@") || !email.includes(".") || email.length < 5) {
        resp.innerText = "Email inválido.";
        resp.style.color = "red";
        return;
    }

    if (celular.length < 10 || celular.length > 11) {
        resp.innerText = "Celular inválido. Deve conter 10 ou 11 dígitos numéricos.";
        resp.style.color = "red";
        return;
    }
    const url = 'http://localhost:8090/login';
    const cliente = {
        nome: nome,
        email: email,
        celular: celular,
        senha: senha
    };

    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(cliente)
    })
        .then(dados => {
            if (dados.ok) {
                resp.innerText = "Login efetuado com sucesso!";
                resp.style.color = "green";
            } else {
                resp.innerText = "Login inválido";
                resp.style.color = "red";
            }
        });

    //tem um possivel erro de indentação schmas NÂO CORRIGIDO tentei porem acho que fracassei      
    try {
        (error)
        resp.innerText = "Erro ao conectar com o servidor.";
        resp.style.color = "red";
        console.error("Erro:", error);
    }
    catch {
        console.log("fds")
    };
}

module.exports = {addBD, login};