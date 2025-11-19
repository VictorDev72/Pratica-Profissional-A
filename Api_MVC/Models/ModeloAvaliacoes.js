const {mssql, stringSQL} = require("../config/db.js");
const { MAX, query } = require("mssql");

async function postAval(post) {
    const {idCliente, texto, nota} = post;
    try{
        const sql = "INSERT into daroca.avaliacao (idCliente, texto, nota) VALUES(@idCliente, @texto, @nota)"
        await mssql.connect(stringSQL);
        const req = new mssql.Request();
        req.input('idCliente', mssql.Int, idCliente);
        req.input('texto', mssql.VarChar(100), texto);
        req.input('nota', mssql.Int, nota);

        await req.query(sql)
        return {mensagem: "Avaliação cadastrada com sucesso"}
    }catch(error){
        console.log("Erro na inscerção dos dados: " + error.message);
        throw error;
    }
    
}
async function getAvalId(id) {
    try{

        const query = " SELECT * FROM daroca.Avaliacao WHERE idCliente = @id"
        const request = new mssql.Request();
    
        request.input('id', mssql.Int, id);
    
        result = await request.query(query);
        if (!result){
            return {mensagem: "Usuario nao tem avaliações"}
        }else{
            return result;   
        }
    }catch(erro){
        console.error("Erro na busca de dados: "+ erro.message);
        throw erro;
    }
}

async function get50Aval() {
    try{
        const query = "SELECT TOP(50) avaliacao.*, cliente.* FROM daroca.avaliacao JOIN daroca.Cliente ON avaliacao.idCliente = cliente.idCliente ORDER BY avaliacao.nota DESC"
        result = await mssql.query(query);
        return result;  
    }catch(erro){
        console.error("Erro na busca de dados: "+ erro.message);
        throw erro;
    }           
    
}

module.exports = {postAval, getAvalId, get50Aval}
