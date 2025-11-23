const express = require("express");
const router = express.Router();
const controlePedidos = require("../Controller/controlePedidos.js");

router.post("/venda", controlePedidos.criarPedido);
router.get("/venda/:id", controlePedidos.PedidoPorId);
router.get("/venda/email/:email", controlePedidos.PedidoPorEmail)

module.exports = router;