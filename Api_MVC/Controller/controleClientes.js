const clienteReq = require("../Models/ModeloClientes.js")

async function cadastrado(req, res) {

    try {
        const cliente = req.body
        const result = await clienteReq.cadastroCliente(cliente)
        res.status(201).json(result);

    } catch (error) {
        res.status(400).json({ error: "erro ao inserir aluno" })
    }
}

async function login(req, res) {

    try {
        const registro = req.body
        const result = await clienteReq.loginCliente(registro)
        res.status(201).json(result);

    } catch (error) {
        res.status(400).json({error: "erro ao fazer login"})
    }

}

module.exports = {cadastrado, login}
