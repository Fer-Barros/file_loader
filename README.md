# file_loader
Automação de Scripts com Eclipse IDE

Visando aumentar a produtivdade em um ambiente acadêmico, o file_loader atua como uma ferramenta com a função de automatizar a criação de Scripts e também possibilita o usuário a executar seu próprio código através dele.


# Como funciona?
file_loader dá ao usuário 3 opções de funcionamento, sendo elas:
1 - Criar arquivos 
2 - Executar arquivo
3 - Desligar (encerrar execução do file_loader)


# Configuração 
Para que o file_loader funcione corretamente, é necessário definir o pacote sendo utilizado pela variável "usr_package" para o pacote de seu projeto atual.
Por padrão, o pacote sempre será "file_loader".


# Criar arquivos
Ao criar um arquivo, o programa solicitará a quantidade de arquivos a serem criados (Ex.: 5, 10, 50, 100...). Cada arquivo gerado, terá um ID numérico atribuído ao seu nome, começando de 1 até a quantidade de arquivos requisitada pelo usuário.
A seguir, o usuário poderá definir se irá criar predefinições de classes ou não, caso opte criar, deverá informar apenas os nomes das classes que deseja e os arquivos dos quais deseja aplicar as predefinições.

Exemplo: Scanner, IOException -> 1,2,3 (Irá aplicar as classes Scanner e IOException para arquivos 1,2,3).
É possível indicar quaisquer IDs (desde que estejam dentro do limite fornecido pelo usuário), ou seja: 1,2,10; 3,7,4...

Após a criação da predefinição, o programa irá retornar indefinidamente para a mesma solicitação (caso o usuário queira definir mais predefinições).
Caso opte por não criar mais predefinições, o file_loader irá então criar os arquivos de acordo com as solicitações do usuário, dentro do caminho especificado.


# Executar arquivos
Após ter criados seus arquivos com file_loader, é possível executá-los através de uma nova tela de terminal e interagir com o script sendo executado.
O usuário terá que escolher a opção 2 no programa e então deverá selecionar o arquivo desejado através de seu ID numérico (1,2,3...).


# Desligar
Encerra o processo atual do file_loader.


