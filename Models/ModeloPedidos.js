
const {mssql, stringSQL} = require("../config/db.js");
const { MAX } = require("mssql");


async function cadastroPedido(pedido) {
    const {data_pedido, id_cliente, id_forma_de_pagamento, parcela, total, id_freq, data_entrega} = pedido;
    try {
        const query = `INSERT INTO daroca.Venda (dataVenda ,idCliente ,idFormaPagamento ,quantasParcelas, referencia_frequencia, vanda_cartaocliente, dataEntrega, totalVenda)
        VALUES (@data_pedido, @id_cliente, @id_forma_de_pagamento, @parcela, @id_freq, NULL, @data_entrega, @total)`
        await mssql.connect(stringSQL);
        const request = new mssql.Request();    
        request.input('data_pedido', mssql.Date, data_pedido)
        request.input('id_cliente', mssql.Int, id_cliente)
        request.input('id_forma_de_pagamento', mssql.Int, id_forma_de_pagamento)
        request.input('qtd_parcela', mssql.Int, qtd_parcela)

        await request.query(query)

        return {mensagem: "Pedido realizado com exito"}
    }
    catch (error) {
        console.error("Erro na inscerção dos dados: " + error.message);
        throw error;
    }
}

async function getPedidoPorId(id){
    try{

        const query = " SELECT TOP(10) cliente.* FROM daroca.Venda WHERE idCliente = @id"
        await mssql.connect(stringSQL);
        const request = new mssql.Request();
        request.input('id', mssql.Int, id);
    
        result = await request.query(query);
        if (!result){
            return {mensagem: "Usuario nao tem pedidos"}
        }else{
            return result;   
        }
    }catch(erro){
        console.error("Erro na busca de dados: "+ erro.message);
        throw erro;
    }
}  
async function getPedidoPorEmail(email){
    try{

        const query = " SELECT TOP(10) cliente.* FROM daroca.Venda WHERE email = @email"
        await mssql.connect(stringSQL);
        const request = new mssql.Request();
        request.input('email', mssql.VarChar(50), email);
    
        result = await request.query(query);
        if (!result){
            return {mensagem: "Usuario nao tem pedidos"}
        }else{
            return result;   
        }
    }catch(erro){
        console.error("Erro na busca de dados: "+ erro.message);
        throw erro;
    }

}


module.exports = {cadastroPedido, getPedidoPorId, getPedidoPorEmail}