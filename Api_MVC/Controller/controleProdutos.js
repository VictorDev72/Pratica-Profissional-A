const produtoReq = require("../Models/ModeloProdutos.js")

async function listarP(req, res) {
    try{
        const prod = await produtoReq.listarProdutos();
        res.json(prod); 
    }catch (error) {
        console.error("Erro ao buscar produtos:", error);
        res.status(500).json({ erro: "Erro ao buscar produtos" });
    }
}

async function listarC(req, res) {
    try{
        const cat = await produtoReq.listarCategorias();
        res.json(cat); 
    }catch (error) {
        console.error("Erro ao buscar categorias:", error);
        res.status(500).json({ erro: "Erro ao buscar categorias" });
    }
}

async function filtro(req, res) {
    try{
        const val = req.prams.value
        const fil = await produtoReq.filtrar(val);
        res.json(fil); 
    }catch (error) {
        console.error("Erro ao buscar produtos filtrados:", error);
        res.status(500).json({ erro: "Erro ao buscar produtos filtrados" });
    }
}


module.exports = { listarP, listarC, filtro };
