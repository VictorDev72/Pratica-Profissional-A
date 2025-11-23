require("dotenv").config();
const express = require("express");
const cors = require("cors");
const { conectaBD } = require("./config/db");
const routesClientes = require("./Routes/routesClientes");
const routesProdutos = require("./Routes/routesProdutos");
const routesPedidos = require("./Routes/routesPedidos");
const routesAval = require("./Routes/routesAvaliacoes");

const app = express();
const porta = process.env.PORTA || 8090;

// Middlewares
app.use(cors({ origin: "*" }));
app.use(express.json());

// Rotas
app.use("/", routesClientes);
app.use("/", routesProdutos);
app.use("/", routesPedidos);
app.use("/", routesAval);

// Rota principal
app.get("/", (req, res) => {
    res.json({ mensagem: "Servidor em execução" });
});

// Inicia BD e servidor
conectaBD();
app.listen(porta, () => console.log(`API em execução na porta ${porta}`));