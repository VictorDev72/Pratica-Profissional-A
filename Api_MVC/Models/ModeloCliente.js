const {mssql} = require("../Config/db.js")



async function cadastroCliente(cliente) {
    const {prenome, sobrenome, email, celular ,nascimento, cpf, tipo, logradouro, numero, complemento, cep, senha} = cliente
    let query = `insert into daroca.clientes (prenome, sobrenome, email, celular ,nascimento, cpf, tipo, logradouro, numero, complemento, cep, senha)
    VALUES ('${prenome}','${sobrenome}','${email}','${celular}','${nascimento},${cpf}','${tipo}','${logradouro}','${numero}','${complemento}','${cep}','${senha}')`
    await mssql.query(query)

    return {mensagem: "Cadastro realizado com exito"}

}

async function loginCliente(registro) {
    const {email, senha} = registro
    const result = await mssql.query(`Select * FROM daroca.clientes where email = '${email}' AND senha = '${senha}'`)
    return {mensagem: "Login realizado com exito"}
}

module.exports = {cadastroCliente, loginCliente}
