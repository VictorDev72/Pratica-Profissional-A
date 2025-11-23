const modPedidos = require('../Models/ModeloPedidos');

async function criarPedido(req, res) {
    try{
        const pedido = req.body;
        const resultado = await modPedidos.cadastroPedido(pedido);
        res.status(201).json(resultado);
    }catch(error){
        res.status(400).json({error:"erro na criação de um pedido", error})
    }
}

async function PedidoPorId(req,res) {
    try{
        const id = req.prams.id
        const result = await modPedidos.getPedidoPorId(id)
        res.status(200).json(result)
    }catch(error){
        res.status(400).json({error:"Usuario sem pedidos", error})
    }
    
}
async function PedidoPorEmail(req,res) {
    try{
        const email = req.prams.email
        const result = await modPedidos.getPedidoPorId(email)
        res.status(200).json(result)
    }catch(error){
        res.status(400).json({error:"Usuario sem pedidos", error})
    }
    
}
module.exports = {criarPedido, PedidoPorId, PedidoPorEmail}