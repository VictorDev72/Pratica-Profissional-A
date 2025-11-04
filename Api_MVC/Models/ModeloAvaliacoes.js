const {mssql} = require("../config/db.js");
const { MAX, query } = require("mssql");

async function postAval(post) {
    const {idCliente, texto, nota} = post;
    try{
        const query = "INSERT into daroca.avaliacao (idCliente, texto, nota) VALUES(@idCliente, @texto, @nota)"
        const req = await mssql.Request();
        req.input('idCliente', mssql.Int, idCliente);
        req.input('texto', mssql.VarChar(100), texto);
        req.input('nota', mssql.Int, nota);

        await req.query(query)
        return {mensagem: "Obrigado por avaliar"}
    }catch(error){
        console.error("Erro na inscerção dos dados: " + error.message);
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

module.exports = {postAval, getAvalId}