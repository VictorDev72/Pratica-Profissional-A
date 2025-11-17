const express = require("express");
const router = express.Router();
const controleAval = require("../Controller/controleAvaliacoes.js");

router.post("/avaliacao", controleAval.criarAval);
router.get("/avaliacao/:id", controleAval.getAvalPorId)
router.get("/avaliacoes", controleAval.get50Aval)

module.exports = router;
