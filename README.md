# Market

<p align="center">
<img src = "https://user-images.githubusercontent.com/100395899/187989530-fbde1326-3370-4cfd-9420-0941f3d1a2cd.png" width="1100px" hight="350px">
</p>

🚧 **Em construção!** 🚧

# Pedido *(Order)*

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

- O teste possui cobertura de 70% das regras de negócio (services).

## *Observações*

- Nome do Banco de Dados: “mercado”.

- As datas possuem o formato: *Dia/Mes/Ano Hora:Minuto:Segundo*.

- É utilizado o MySql com a porta: 3306.

- O código é organizado com base nas distinções de responsabilidades.

##

# Pagamentos *(Payments)*

Recebe o id do pedido e o seu total, por meio de uma fila gerada quando um novo pedido é salvo na API Pedido. Os dados recebidos são salvos em uma tabela no BD, com um código único gerado automaticamente e a data do momento do evento. Foi utilizado o RabbitMQ. Ele se comunica com o PB-Bank.

##

# Site

## Entidades: 

**Itens:** id, nome, dataValidade, valor, descricao, estoque, skuid 

**Clientes:**: ID (CPF - não automatico), nome, dataCriacao

**Clientes_Cartoes:** numero, codigo, mesvalidade, anoValidade, marca

**Checkout:** (/api/checkout), é consumida utilizando um payload como exemplo:
~~~

{
    "itens": [
        {
            "skuId": "rcd1234335",
            "qtd": 3
        },
        {
            "skuId": "rcd9999999",
            "qtd": 1
          }
    ],
    "cliente_info": {
        "clientId": "12345678900",
        "cartaoId": 1
    }
}
~~~

Efetua o calculo do total do pedido, verificar estoque, etc, monta as informações e envia a requisição para o serviço de pedidos (/api/pedido) e retorna as informações do pedido, como valor total, número do pedido e status, como exemplo:

~~~
{
    "numeroDoPedido": 10,
    "total": 1234.44,
    "status":"Em processamento",
    "itens": [
        {
            "nome": "ITEM 01",
            "valor": 22.5
        }
    ]
}
~~~
