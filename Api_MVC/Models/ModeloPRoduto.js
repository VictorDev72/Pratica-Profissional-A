//AQUI COMEÇA OS MODELOS DE PRODUTOS

let url = "http://localhost:8090/produtos";
let url2 = "http://localhost:8090/categorias";
let totalCarrinho = 0;
let todosProdutos = [];

fetch(url)
    .then(resposta => resposta.json())
    .then(data => {
        console.log("Dados recebidos com sucesso")
        todosProdutos = data
        console.log(todosProdutos)
        exibeDados(data)
    })
    .catch(erro => console.error("Erro ao buscar dados:", erro));

fetch(url2)
    .then(resposta => resposta.json())
    .then(data => {
        console.log("Dados recebidos com sucesso")
        opcoes(data)
    })
    .catch(erro => console.error("Erro ao buscar dados:", erro));

function opcoes(dados) {

    let options = '<option value = 0>Todos</option>'; // opção para resetar filtro
    dados.forEach(element => {
        options += `<option value='${element.id}''>${element.nome}</option>`
    });
    document.querySelector('select').innerHTML = options
}

function exibeDados(dados) {
    let produtos = "";
    for (let i = 0; i < dados.length; i++) {
        produtos += `
                    <div class="card">
                        <img src="${dados[i].imagem}">
                        <h4>${dados[i].nome}</h4>
                        <p>${dados[i].descricao}</p>
                        <p>Valor: ${dados[i].valor} R$</p>
                        <p>Unidade: ${dados[i].unidade}</p>
                        <button onclick="addCarrinho('${dados[i].nome}', ${dados[i].valor})">Add a cesta</button>
                    </div>
                `;//add <p>categoria, add <p>unidade, add <p>descrição 
    }
    document.getElementById("cards").innerHTML = produtos;
}

let itemId = 0
function addCarrinho(nome, valor) {
    itemId++
    const id = `item_${itemId}`

    lista = `
                <div class="card_carrinho" id='${id}'>
                    <p>${nome} - R$ ${valor.toFixed(2)} -- <span class="qtd">1</span>x</p>
                    <button onclick="add('${id}', ${valor})">+</button>
                    <button onclick="sub('${id}', ${valor})">-</button>
                    <button onclick="removerDaLista('${id}', ${valor})">remover</button>
                </div>
            `;
    document.getElementById("lista_carrinho").innerHTML += lista;
    preco_carrinho_add(valor)
}

function add(id, valor) {
    let qtdSpan = document.getElementById(id).querySelector(".qtd");
    let qtd = parseInt(qtdSpan.innerText) || 0;
    qtd++;
    qtdSpan.innerText = qtd;
    preco_carrinho_add(valor);
}

function sub(id, valor) {
    let qtdSpan = document.getElementById(id).querySelector(".qtd");
    let qtd = parseInt(qtdSpan.innerText) || 0; //Do java pega um tipo de dado e tenta transformar em inteiro, se nao conseguir transforma em 0 
    qtd--;
    if (qtd <= 0) {
        removerDaLista(id, valor)
    } else {
        qtdSpan.innerText = qtd;
        preco_carrinho_subtract(valor);
    }
}

function removerDaLista(id, valor) {
    let qtdSpan = document.getElementById(id).querySelector(".qtd");
    let qtd = parseInt(qtdSpan.innerText) || 1;
    document.getElementById(id).remove();
    preco_carrinho_subtract(valor * qtd);
}

function filtrar(value) {
    //console.log(value)
    if (value == 0) {
        exibeDados(todosProdutos)
    }
    else {
        const url = `http://localhost:8090/filtrar/${value}`
        fetch(url)
            .then(resposta => resposta.json())
            .then(data => {
                console.log("Dados recebidos com sucesso")
                console.log(data)
                exibeDados(data)
            })
    }


}

function preco_carrinho_add(valor) {
    totalCarrinho += valor;
    document.getElementById("soma_carrinho").innerText =
        `Total: R$ ${totalCarrinho.toFixed(2)}`;
}

function preco_carrinho_subtract(valor) {
    totalCarrinho -= valor;
    document.getElementById("soma_carrinho").innerText =
        `Total: R$ ${totalCarrinho.toFixed(2)}`;
}


document.querySelector("#filtro input").addEventListener("input", (e) => {
    const termo = e.target.value.toLowerCase();
    const filtrados = todosProdutos.filter(p =>
        p.nome.toLowerCase().includes(termo) ||
        p.descricao.toLowerCase().includes(termo));
    exibeDados(filtrados);
});

module.exports = {opcoes, exibeDados, sub, preco_carrinho_add, preco_carrinho_subtract, filtrar, removerDaLista, add, addCarrinho};