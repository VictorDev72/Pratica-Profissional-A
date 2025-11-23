const modAval = require('../Models/ModeloAvaliacoes');

async function criarAval(req, res) {
    try{
        const aval = req.body;
        // Validação básica
        if (!aval || !aval.idCliente || !aval.texto || typeof aval.nota === 'undefined') {
            return res.status(400).json({error: "Dados de avaliação inválidos"});
        }
        const resultado = await modAval.postAval(aval);
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