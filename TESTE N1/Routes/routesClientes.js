const express = require("express");
const router = express.Router();
const controleClientes = require("../Controller/controleClientes");



router.post("/cadastro", controleClientes.cadastrado);
router.post("/login", controleClientes.login);
router.post("/cartao", controleClientes.criaCartao);

module.exports = router;
