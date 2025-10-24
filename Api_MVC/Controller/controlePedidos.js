const modPedidos = require('../Model/modPedidos');

async function criarPedido(req, res) {
    try{
        const pedido = req.body;
        const resultado = await modPedidos.cadastroPedido(pedido);
        res.status(201).json(resultado);
    }catch(error){
        res.status(400).json({error:"erro na criação de um pedido", error})
    }
}

module.exports = {criarPedido}