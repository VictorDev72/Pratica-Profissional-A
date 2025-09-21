require("dotenv").config();
const porta = process.env.PORTA;
const stringSQL = process.env.CONNECTION_STRING;
const express = require('express');
const app = express();
const mssql = require('mssql');
const cors = require('cors')

app.use(cors({
    origin:'*'
}))


app.use(express.json())

async function conectaBD(){
    try{
        await mssql.connect(stringSQL);
    }
    catch (error){
        console.log('Algo deu errado na conexao do BD',error)
    }
}
conectaBD();


//http://localhost:8090/produtos
app.get("/produtos" , async (req,res) => {
    try{
        const produtos = await mssql.query('SELECT * FROM daroca2.Produtos')
        res.json(produtos.recordset)
        console.log(produtos.recordset) 
    }catch (error) {
        console.error("Erro ao buscar produtos:", error);
        res.status(400).json({ erro: "Erro ao buscar produtos" });
    }
    
})

app.get("/categorias" , async (req,res) => {
    try{
        const produtos = await mssql.query('SELECT * FROM daroca2.Categorias')
        res.json(produtos.recordset)
        console.log(produtos.recordset) 
    }catch (error) {
        console.error("Erro ao buscar produtos:", error);
        res.status(400).json({ erro: "Erro ao buscar produtos" });
    }
    
})

app.get("/filtro/:value" , async (req,res) => {
    const value = req.params.value;
    const pesquisa = `%${value}%`
    try{
        const produtos = await mssql.query(`SELECT * FROM Produtos WHERE nome LIKE ${pesquisa};`);
        console.log(produtos.recordset)
        res.json(produtos.recordset)
    }
    catch (error){
        console.error("Erro ao buscar produtos:", erro);
        res.status(500).json({ erro: "Erro ao buscar produtos" });
    }
    
})









// rota principal
app.use('/', (req,res) => res.json( {mensagem: 'Servidor em execução'}) )

// iniciar o servidor
app.listen(porta, () => console.log("API funcionando!"))
