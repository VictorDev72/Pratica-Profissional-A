const express = require("express");
const router = express.Router();
const controleAval = require("../Controller/controleAvaliacoes.js");

router.post("/venda", controleAval.criarAval);
router.get("/venda/:id", controleAval.getAvalPorId)

module.exports = router;