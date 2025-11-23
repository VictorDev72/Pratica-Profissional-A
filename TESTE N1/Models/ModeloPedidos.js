
const {mssql, stringSQL} = require("../config/db.js");
const { MAX } = require("mssql");


async function cadastroPedido(pedido) {
    const {dataVenda, idCliente, formaPagamento, parcelamento, total, frequencia, dataEntrega, catao} = pedido;
    try {
        const query = `INSERT INTO daroca.Venda (dataVenda ,idCliente ,idFormaPagamento ,quantasParcelas, referencia_frequencia, venda_cartaocliente , dataEntrega, totalVenda)
        VALUES (@dataVenda, @idCliente, @formaPagamento, @parcelamento, @frequencia, @cartao, @dataEntrega, @total)`
        await mssql.connect(stringSQL);
        const request = new mssql.Request();    
        request.input('dataVenda', mssql.Date, dataVenda)
        request.input('idCliente', mssql.Int, idCliente)
        request.input('formaPagamento', mssql.Int, formaPagamento)
        request.input('parcelamento', mssql.Int, parcelamento)
        request.input('frequencia', mssql.Int, frequencia)
        request.input('cartao', mssql.Int, catao)
        request.input('dataEntrega', mssql.Date, dataEntrega)
        request.input('total', mssql.Decimal(10,2), total)

        await request.query(query)

        return {mensagem: "Pedido realizado com exito"}
    }
    catch (error) {
        console.error("Erro na inscerção dos dados: " + error.message);
        throw error;
    }
}

async function getPedidoPorId(id) {
    try {
        const query = "SELECT TOP(10) * FROM daroca.Venda WHERE idCliente = @id ORDER BY dataVenda DESC";
        await mssql.connect(stringSQL);
        const request = new mssql.Request();
        request.input('id', mssql.Int, id);
        const res = await request.query(query);
        return res.recordset || [];
    } catch (erro) {
        console.error("Erro na busca de pedidos por id: " + erro.message);
        throw erro;
    }
}

async function getPedidoPorEmail(email) {
    try {
        const query = `
            SELECT TOP(10) v.*
            FROM daroca.Venda v
            JOIN daroca.cliente c ON v.idCliente = c.idCliente
            WHERE c.email = @email
            ORDER BY v.dataVenda DESC
        `;
        await mssql.connect(stringSQL);
        const request = new mssql.Request();
        request.input('email', mssql.VarChar(50), email);
        const res = await request.query(query);
        return res.recordset || [];
    } catch (erro) {
        console.error("Erro na busca de pedidos por email: " + erro.message);
        throw erro;
    }
}

module.exports = {cadastroPedido, getPedidoPorId, getPedidoPorEmail}
