const bcrypt = require("bcrypt");
const {mssql} = require("../config/db.js");
const { MAX } = require("mssql");
const stringSQL = process.env.CONNECTION_STRING;



async function cadastroCliente(cliente) {
    const {prenome, sobrenome, nascimento, email,celular, cpf, tipo, logradouro, numero, complemento, cep, senha} = cliente

    if (!senha) throw new Error("Senha não fornecida");

    const senhaHash = await bcrypt.hash(senha, 10);

    try {
        let query = `insert into daroca.cliente (prenome, sobrenome, nascimento, email, celular , cpf, tipo, logradouro, numero, complemento, cep, senha)
        VALUES (@prenome, @sobrenome, @nascimento, @email, @celular, @cpf, @tipo, @logradouro, @numero, @complemento, @cep, @senha)`
        await mssql.connect(stringSQL);
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
        request.input('senha', mssql.VarChar(mssql.MAX), senhaHash)

        await request.query(query)
        return {mensagem: "Cadastro realizado com exito"}
    } catch (error) {
        console.error("Erro na inscerção dos dados: " + error.message);
        throw error;
    }

    

}

async function buscar(email) {
    try{
        let query = `SELECT * FROM daroca.cliente c JOIN daroca.CartaoCliente cc on c.idCliente = cc.idCliente WHERE email = @email`;
        await mssql.connect(stringSQL);
        const request = new mssql.Request();
        request.input("email", mssql.VarChar(50), email);
        const result = await request.query(query);
        return result.recordset[0]; 
    }catch(error){
        console.error("Erro na busca dos dados: " + error.message);
    }
    
}

async function cadastroCartao(info) {
    const {id_cliente, numero_cartao, validade, CVV} = info;
    try {
        let query = `insert into daroca.cartoes (idCliente, numeroCartao, validade, CVV)
        VALUES (@id_cliente, @numero_cartao, @validade, @CVV)`
        await mssql.connect(stringSQL);
        const request = new mssql.Request();
        request.input('id_cliente', mssql.Int, id_cliente)
        request.input('numero_cartao', mssql.VarChar(16), numero_cartao)
        request.input('validade', mssql.VarChar(5), validade)
        request.input('CVV', mssql.VarChar(4), CVV)
        await request.query(query)
        return {mensagem: "Cartão cadastrado com exito"}
    } catch (error) {
        console.error("Erro na inscerção dos dados: " + error.message);
        throw error;
    }
}

//async function loginCliente(registro) {
//    const {email, senha} = registro
//    const result = await mssql.query(`Select * FROM daroca.clientes where email = '${email}' AND senha = '${senha}'`)
//    return {mensagem: "Login realizado com exito"}
//}

module.exports = {cadastroCliente, buscar, cadastroCartao} //loginCliente