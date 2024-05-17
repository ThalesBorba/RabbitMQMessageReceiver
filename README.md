#MessageReceiver

Exemplo simples de um Microsserviço que recepta uma mensagem do RabbitMQ cujo corpo é uma requisição de produto que será salva no banco de dados

Obs.: Por ser apenas um material de estudo, senhas e variáveis estão expostas apenas como exemplo.
Em ambiente profissional devem ser extraídas por questão de segurança.

Obs2.: Neste exemplo, a configuração do RabbitMQ está baseada em uma imagem padrão do Docker.
A pasta /docker contém a configuração do container com as imagens do RabbitMQ e do banco Postgres.
É possível usar o arquivo init-database.sql para gerar uma massa inicial, mas é necessário adicionar o arquivo ao docker-compose