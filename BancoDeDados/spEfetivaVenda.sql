create procedure loja.spEfetivaVenda @pIdCliente int, @pQtasParcelas int, @pidFormaPagamento int
as
begin
	Declare @IdItemCesta int,		-- variáveis usadas para
			@IdCliente int,			-- armazenar os valores lidos
			@DataVenda Datetime,	-- do cursor (das tabelas)
			@QuantComprada int,
			@IdProduto int,
			@QuantEstoque int,
			@precoUnitario Money,
			@idVenda int,
			@ValorTotal Money,
			@numParcela int,
			@valorParcela Money,
			@DataVencimento DateTime,
			@erro int,
			@numItem int

	-- cursor que busca e armazena os registros da cesta de compras do cliente
	-- cujo idCliente foi passado como parâmetro
	Declare cCestaCompra Cursor for
		Select
			CC.idItemCesta, V.idCliente, V.dataVenda,
			CC.quantComprada, P.idProduto, P.quantEstoque, P.precoUnitario
		from
			Loja.Cesta CC Inner JOIN Loja.Produto P 
			on P.idProduto = CC.idProduto
				inner join loja.Venda V
				on cc.idVenda = v.idVenda
		where
			idCliente = @pIdCliente
		order by
			dataVenda
	set @DataVenda = GetDate() -- data atual, para, junto ao Id_Cliente,
-- identificarmos esta venda, pois o IdVenda dessa 
-- tabela não é devolvido após o Insert
	Begin Tran
	 Insert
		into loja.Venda (IdCliente, DataVenda, idFormaPagamento, QuantasParcelas)
		values (@pIdCliente, @DataVenda, @pidFormaPagamento, @pQtasParcelas)
	-- busca o IdVenda do registro recém-inserido no insert acima
	Select @idVenda = idVenda 
		from loja.Venda 
		where idCliente = @pIdCliente and  -- usamos estas variáveis para
			  dataVenda = @DataVenda       -- buscar o IdVenda recém-gravado
	Open cCestaCompra -- abre o cursor e traz os dados resultantes do where
	Fetch cCestaCompra -- lê um registro e armazena os campos nas variáveis
		into @IdItemCesta, @IdCliente, @DataVenda, @QuantComprada, @IdProduto,
		@QuantEstoque, @precoUnitario 
	 set @ValorTotal = 0
	 set @numItem = 0
	 set @erro = 0
	 while @@Fetch_Status = 0 -- @@Fetch_Status é função que devolve o resultado
	 begin                    -- do Fetch anterior; 0 indica que foi lido um registro
                              -- processa o registro atual
                              -- inclui um registro na tabela ItemVenda desta venda, com o produto atual
		set @NumItem += 1
		if @quantComprada <= @QuantEstoque -- estoque atende o pedido totalmente
		begin
			Insert into Loja.ItemVenda (IdVenda, NumItem, DataLiberacao, 
									idProduto, quantComprada) 
			values (@IdVenda, @NumItem, @DataVenda, 
					@idProduto, @quantComprada)
			Update Loja.Produto set quantEstoque = @QuantEstoque - @QuantComprada
			Where idProduto = @idProduto
			Set @ValorTotal += @quantComprada * @precoUnitario
		end
		else -- lote de estoque atual não atende totalmente o pedido
		begin
			Rollback Tran	-- desfaz as atualizações ocorridas no BD desde o begin tran
			set @erro = 1
			Break			-- sai do while, pois deu erro dentro da transação
		end
		Fetch cCestaCompra -- lê novo registro da cesta e armazena os campos nas variáveis
		into @IdItemCesta, @IdCliente, @DataVenda, @QuantComprada, @IdProduto,
			@QuantEstoque, @precoUnitario
	End
	close cCestaCompra -- fecha o cursor
	Deallocate cCestaCompra
	if @erro = 0 -- não houve erros
	begin
		set @ValorParcela = @ValorTotal / @pQtasParcelas
		set @NumParcela = 1
		set @DataVencimento = @DataVenda
		while @NumParcela <= @pQtasParcelas 
		begin
			 Insert into Loja.Parcela 
			(IdVenda, QualParcela, DataVencimento, ValorPrevisto)
			 values (@IdVenda, @NumParcela, @DataVencimento, @ValorParcela)
			 set @NumParcela += 1
			 set @DataVencimento += 30 -- 30 dias depois vence outra parcela
		end
		Delete from Loja.Cesta 
		Where idCliente = @pIdCliente -- apaga a cesta desse cliente
		
		Commit Tran  -- registra no BD, definitivamente, todas as transações ocorridas
	end
End
