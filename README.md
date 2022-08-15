# API Pedido

## Entidades:

-  **Pedido:** Id, Cpf, Itens, Total.

    *Obs.: Um pedido preenche um ou mais itens*

- **Item:** Id, Nome, Data de Criação, Data de Validade, Valor, Descricao, Ofertas.

    *Obs.:Um item vai preenche uma ou mais ofertas*

- **Oferta:** Id, Nome, Data de Criação, Data de Validade, Desconto, Descrição.

## Endpoints:

- getAll-/api/pedido/

- Campo “sort” para asc ou desc

  *Obs: Valor Total do pedido.*

- Campo “cpf” para filter.

- getById-/api/pedido/{id}

- patch-/api/pedido/{id}

- patch-api/item/{id}  

- post-/api/pedido/ 

- delete-/api/pedido/{id}

## Validações

- O id é gerado automaticamente.

- O CPF deve ser digitado sem ponto ou traço, e a quantidade de números do CPF é validada.

- A data de criação do pedido não pode ser posterior a data de validade do pedido.

- A data de criação da oferta não pode ser posterior a data de validade da oferta.

- Um item não pode ter uma oferta com data de validade vencida.

- Os tratamentos de resposta ou exceção são proporcionais a resposta ou exceção.

- A API utiliza handler.

- Quando um elemento requerido faltar, a mensagem diz qual falta.

## Testes 

- O teste possui cobertura de 70% das regras de negócio.

## *Observações*

- Nome do Banco de Dados: “mercado”.

- As datas possuem o formato: *Dia/Mes/Ano Hora:Minuto:Segundo*.

- É utilizado o MySql com a porta: 3306.

- O código é organizado com base nas distinções de responsabilidades.

##

# Microsserviço Pagamentos

Recebe o id do pedido e o seu total, por meio de uma fila gerada quando um novo pedido é salvo na API Pedido. Os dados recebidos são salvos em uma tabela no BD, com um código único gerado automaticamente e a data do momento do evento. Foi utilizado o RabbitMQ.



