--create schema loja

create table loja.Cliente
(
	idCliente int primary key identity not null,
	prenome varchar(20) not null,
	sobrenome varchar(30) not null,
	nascimento date not null,
	cpf varchar(15) not null,
	tipo varchar(10) not null,
	logradouro varchar(50) not null,
	numero varchar(6) not null,
	complemento varchar(20) not null,
	cep char(8) not null
)

create table loja.Categoria
(
	idCategoria int identity not null primary key ,
	descricao varchar(20) not null
)

create table loja.Produto
(
	idProduto int not null identity primary key,
	nomeProduto varchar(50) not null,
	idCategoria int not null foreign key references loja.Categoria(idCategoria),
	quantEstoque real not null check (quantEstoque >= 0),
	precoUnitario money not null check (precoUnitario > 0),
)

create table loja.FormaPagamento
(
	idFormaPagamento int identity not null primary key,
	descricao varchar(25) not null
)

create table loja.Venda
(
	idVenda	int identity not null primary key,
	dataVenda datetime not null,
	idCliente int not null foreign key references loja.Cliente(idCliente),
	idFormaPagamento int not null foreign key references loja.FormaPagamento(idFormaPagamento),
	quantasParcelas int not null check (quantasParcelas between 1 and 12)
)

create table loja.ItemVenda
(
	idItemVenda int primary key identity not null,
	idCliente int not null foreign key references loja.Cliente(idCliente),
	idProduto int not null foreign key references loja.Produto(idProduto),
	quantComprada real not null check (quantComprada > 0)
)

create table loja.Cesta
(
	idItemCesta int primary key identity not null,
	idCliente int not null foreign key references loja.Cliente(idCliente),
	idProduto int not null foreign key references loja.Produto(idProduto),
	quantComprada real not null check (quantComprada > 0)
)



create table loja.Parcela
(
	idParcela int identity not null primary key,
	idVenda int not null foreign key references loja.Venda(idVenda),
	qualParcela int not null check (qualParcela between 1 and 12),
	valorPrevisto money not null check (valorPrevisto > 0),
	dataVencimento date not null
)


