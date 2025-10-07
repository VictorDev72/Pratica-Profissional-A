const bcrypt = require("bcrypt");
const {mssql} = require("../Config/db.js")



async function cadastroCliente(cliente) {
    const {prenome, sobrenome, email, celular ,nascimento, cpf, tipo, logradouro, numero, complemento, cep, senha} = cliente
    console.log(cliente)
    const senhaHash = await bcrypt.hash(senha, 10);
    let query = `insert into daroca.cliente (prenome, sobrenome, email, celular ,nascimento, cpf, tipo, logradouro, numero, complemento, cep, senha)
    VALUES ('${prenome}','${sobrenome}','${email}','${celular}','${nascimento}','${cpf}','${tipo}','${logradouro}','${numero}','${complemento}','${cep}','${senhaHash}')`
    await mssql.query(query)

    if (!senha) throw new Error("Senha não fornecida");
    return {mensagem: "Cadastro realizado com exito"}

}

async function buscar(email) {
    const result = await mssql.query(`SELECT * FROM daroca.cliente WHERE email ='${email}'`);
    return result.recordset[0];
}

//async function loginCliente(registro) {
//    const {email, senha} = registro
//    const result = await mssql.query(`Select * FROM daroca.clientes where email = '${email}' AND senha = '${senha}'`)
//    return {mensagem: "Login realizado com exito"}
//}

module.exports = {cadastroCliente, buscar,} //loginCliente
