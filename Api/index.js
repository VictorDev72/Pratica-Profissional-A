

require("dotenv").config();
const porta = process.env.PORTA;
const stringSQL = process.env.CONNECTION_STRING;
const express = require('express');
const app = express();
const mssql = require('mssql');
const cors = require('cors')

app.use(cors({
    origin: '*'
}))


app.use(express.json())

async function conectaBD() {
    try {
        await mssql.connect(stringSQL);
    }
    catch (error) {
        console.log('Algo deu errado na conexao do BD', error)
    }
}
conectaBD();

app.post('/cadastro', async (req, res) => {
    
    const nome = req.body.nome
    const email = req.body.email
    const celular = req.body.celular
    const senha = req.body.senha
    
    try{
        let query = `insert into daroca.clientes (nome,email,celular,senha) VALUES ('${nome}','${email}','${celular}','${senha}')`
        await mssql.query(query)
        res.status(201).json({'message':'cadastro concluido'}) 
    }
    catch{
        res.status(500).json({'message':'Erro na inclusão dos dados'}) 
    }
})


app.post('/login', async (req, res) => {
    
     
    
    try {
        const nome = req.body.nome;
        const email = req.body.email;
        const celular = req.body.celular;
        const senha = req.body.senha;
        console.log(nome, email, celular, senha)
        const result = await mssql.query(`Select * FROM daroca.clientes where nome = '${nome}' AND senha = '${senha}'`)
        if(result.recordset.length > 0){
            
            res.status(200).json({ message: "Login Valido" })
        }
        else{
            res.status(404).json({message : "Invalido"})
        }
    }
    catch (erro) {
        console.log("Erro na validação de cadastro", erro)
        res.status(500).json({message : "Erro na verificação"})
    }
})




//http://localhost:8090/produtos
app.get("/produtos", async (req, res) => {
    try {
        const produtos = await mssql.query('SELECT * FROM daroca.produtos')
        res.json(produtos.recordset)
        console.log(produtos.recordset)
    } catch (error) {
        console.error("Erro ao buscar produtos:", error);
        res.status(400).json({ erro: "Erro ao buscar produtos" });
    }

})

app.get("/categorias", async (req, res) => {
    try {
        const categorias = await mssql.query('SELECT * FROM daroca.categorias')
        res.json(categorias.recordset)
        console.log(categorias.recordset)
    } catch (error) {
        console.error("Erro ao buscar produtos:", error);
        res.status(400).json({ erro: "Erro ao buscar produtos" });
    }

})

app.get("/filtrar/:value", async (req, res) => {
    const value = req.params.value;
    console.log(value)
    try {
        const produtos = await mssql.query(`SELECT * FROM daroca.produtos WHERE categoria = ${value}`);
        console.log(produtos.recordset)
        res.json(produtos.recordset)
    }
    catch (error) {
        console.error("Erro ao buscar produtos:", erro);
        res.status(500).json({ erro: "Erro ao buscar produtos" });
    }

})


// rota principal
app.use('/', (req, res) => res.json({ mensagem: 'Servidor em execução' }))

// iniciar o servidor
app.listen(porta, () => console.log("API funcionando!"))
