const express = require("express");
const router = express.Router();
const controlePedidos = require("../Controller/controlePedidos.js");

router.post("/venda", controlePedidos.criarPedido);

module.exports = router;