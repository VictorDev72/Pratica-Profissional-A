require("dotenv").config();
const mssql = require('mssql');

const stringSQL = process.env.CONNECTION_STRING;

async function conectaBD() {
    try {
        await mssql.connect(stringSQL);
    }
    catch (error) {
        console.log('Algo deu errado na conexao do BD', error)
    }
}

module.exports = {mssql, conectaBD }