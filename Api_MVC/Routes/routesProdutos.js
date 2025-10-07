const express = require("express");
const router = express.Router();
const ProdutoController = require("../controllers/controleProdutos.js");

router.get("/produtos", ProdutoController.listarP)
router.get("/categorias", ProdutoController.listarC)
router.get("/filtrar/:value", ProdutoController.filtro)

module.exports = router;