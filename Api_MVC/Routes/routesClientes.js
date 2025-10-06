const express = require("express");
const router = express.Router();
const controleClientes = require("Controller/controleClientes");
const { addBD, login } = require("../Controller/controleClientes");


router.post("/cadastro", addBD.inserir);
router.post("/login", login.verificar);

module.exports = router;