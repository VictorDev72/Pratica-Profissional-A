const { mssql } = require("../Config/db");

async function listarProdutos() {
    const produtos = await mssql.query('SELECT * FROM daroca.produtos')
    return produtos.recordset;
}

async function listarCategorias() {
    const categorias = await mssql.query('SELECT * FROM daroca.categorias')
    return categorias.recordset;
}

async function filtrar(value) {
    const produtos = await mssql.query(`SELECT * FROM daroca.produtos WHERE categoria = ${value}`);
    return produtos.recordset;
}

module.exports = { listarProdutos, listarCategorias, filtrar};