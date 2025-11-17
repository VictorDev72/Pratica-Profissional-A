const modAval = require('../Model/ModeloAvaliacoes');

async function criarAval(req, res) {
    try{
        const aval = req.body;
        const resultado = await modAval.PostAval(aval);
        res.status(201).json(resultado);
    }catch(error){
        res.status(400).json({error:"erro na criação de um pedido", error})
    }
}

async function getAvalPorId(req,res) {
    try{
        const id = req.prams.id
        const result = await modAval.getPedidoPorId(id)
        res.status(200).json(result)
    }catch(error){
        res.status(400).json({error:"Usuario sem Avaliaçoes", error})
    }
    
}

async function get50Aval(req,res) {
    try{
        const result = await modAval.get50Aval()
        res.status(200).json(result)
    }catch(error){
        res.status(400).json({error:"Erro ao buscar avaliações", error})
    }
}

module.exports = {criarAval, getAvalPorId, get50Aval}
