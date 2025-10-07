const jwt = require("jsonwebtoken");
const bcrypt = require("bcrypt");
const clienteReq = require("../models/ModeloClientes.js")

require("dotenv").config();
const SECRET = process.env.SECRET

async function cadastrado(req, res) {

    try {
        const cliente = req.body
        const result = await clienteReq.cadastroCliente(cliente)
        res.status(201).json(result);

    } catch (error) {
        res.status(400).json({ error: "erro ao inserir cliente",error })
    }
}

async function login(req, res) {

    try {
        const { email, senha } = req.body;
        const cliente = await clienteReq.buscar(email);

        if (!cliente) return res.status(401).json({ msg: "Credenciais inválidas" });

        const senhaValida = await bcrypt.compare(senha, cliente.senha);
        if (!senhaValida) return res.status(401).json({ msg: "Credenciais inválidas" });

        const token = jwt.sign({ id: cliente.id, email: cliente.email, role: cliente.role }, SECRET, { expiresIn: "2h" });
        res.json({ token });
    } catch (err) {
        res.status(500).json({ erro: err.message });
    }

}

module.exports = {cadastrado, login}
