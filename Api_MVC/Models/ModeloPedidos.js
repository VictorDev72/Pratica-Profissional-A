
const {mssql} = require("../config/db.js");
const { MAX } = require("mssql");


async function cadastroPedido(pedido) {
    const {data_pedido, id_cliente, id_forma_de_pagamento, qtd_parcela} = pedido;
    try {
        const query = `INSERT INTO daroca.Venda (dataVenda ,idCliente ,idFormaPagamento ,quantasParcelas)
        VALUES (@data_pedido, @id_cliente, @id_forma_de_pagamento, @qtd_parcela)`
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


module.exports = {cadastroPedido}