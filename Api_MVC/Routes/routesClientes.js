const express = require("express");
const router = express.Router();
const controleClientes = require("Controller/controleClientes");



router.post("/cadastro", controleClientes.cadastro);
router.post("/login", controleClientes.login);

module.exports = router;
