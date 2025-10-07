const { mssql } = require("../config/db");
const bcrypt = require("bcrypt");

async function buscar(username) {

    const result = await mssql.query(`SELECT * FROM dbo.Usuario WHERE username ='${username}', senha = '${senha}'`);
    return result.recordset[0];
}
async function inserir(usuario) {
    
    const { username, senha, role } = usuario;
    const senhaHash = await bcrypt.hash(senha, 10);
    await mssql.query(`INSERT INTO Usuario (username, senha, role) VALUES ('${username}','${senhaHash}', '${role}')`);
    return { mensagem: "Usuário inserido com sucesso" };
}
module.exports = { buscar, inserir };
