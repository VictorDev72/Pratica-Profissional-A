const { Request, Int } = require("mssql");
const { mssql } = require("../config/db");

async function listarProdutos() {
    const produtos = await mssql.query('SELECT * FROM daroca.produtos')
    return produtos.recordset;
}

async function listarCategorias() {
    const categorias = await mssql.query('SELECT * FROM daroca.categorias')
    return categorias.recordset;
}

async function filtrar(value) {
    const produtos = await mssql.query(`SELECT * FROM daroca.produtos WHERE categoria = @value`);

    try{
        const request = new mssql.Request()

        request.input('value', mssql.Int, value)
        return produtos.recordset;
    }catch(error){
        alert("Deletaram a tabela Volte depois :D")
    }
}

module.exports = { listarProdutos, listarCategorias, filtrar};
