const { mssql } = require("../config/db");
const stringSQL = process.env.CONNECTION_STRING;

async function listarProdutos() {
    await mssql.connect(stringSQL);
    const produtos = await mssql.query('SELECT * FROM daroca.produtos');
    return produtos.recordset;
}

async function listarCategorias() {
    await mssql.connect(stringSQL);
    const categorias = await mssql.query('SELECT * FROM daroca.categorias');
    return categorias.recordset;
}

async function filtrar(value) {
    let query = `SELECT * FROM daroca.produtos WHERE categoria = @value`;
    try{
        await mssql.connect(stringSQL);
        const request = new mssql.Request();
        request.input('value', mssql.Int, value);
        const produtos = await request.query(query);
        return produtos.recordset;
    }catch(error){
        console.error("Erro na busca de dados: " + error.message);
        throw error;
    }
}

module.exports = { listarProdutos, listarCategorias, filtrar};
