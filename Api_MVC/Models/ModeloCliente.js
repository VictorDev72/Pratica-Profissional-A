const bcrypt = require("bcrypt");
const {mssql} = require("../config/db.js");
const { MAX } = require("mssql");



async function cadastroCliente(cliente) {
    const {prenome, sobrenome, nascimento, email,celular, cpf, tipo, logradouro, numero, complemento, cep, senha} = cliente

    const senhaHash = await bcrypt.hash(senha, 10);

    
    try {
        let query = `insert into daroca.cliente (prenome, sobrenome, nascimento, email, celular , cpf, tipo, logradouro, numero, complemento, cep, senha)
        VALUES (@prenome, @sobrenome, @nascimento, @email, @celular, @cpf, @tipo, @logradouro, @numero, @complemento, @cep, @senhaHash)`

        const request = new mssql.Request();

        request.input('prenome', mssql.VarChar(20), prenome)
        request.input('sobrenome', mssql.VarChar(30), sobrenome)
        request.input('nascimento', mssql.Date, nascimento)
        request.input('email', mssql.VarChar(50), email)
        request.input('celular', mssql.VarChar(14), celular)
        request.input('cpf', mssql.VarChar(15), cpf)
        request.input('tipo', mssql.VarChar(10), tipo)
        request.input('logradouro', mssql.VarChar(50), logradouro)
        request.input('numero', mssql.VarChar(6), numero)
        request.input('complemento', mssql.VarChar(20), complemento)
        request.input('cep', mssql.VarChar(8), cep)
        request.input('senha', mssql.VarChar(MAX), senha)

        await request.query(query)

    } catch (error) {
        alert("Insira os dados de forma correta!!")
    }
    //console.log(cliente)
    

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
