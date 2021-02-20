# Projeto final - Compiladores

## Sobre este pojeto

Este projeto foi desenvolvido durante a disciplina de Compiladores, no curso de Ciência da Computação da UFSCar - Sorocaba. Foram implementadas as etapas de análise sintática, léxica e semântica da compilação de uma linguagem de programação fictícia chamada K para a linguagem C. Todo o código-fonte do projeto foi escrito em Java.

## Especificações

### Linguagem K

Diante do fato deste projeto ter sido feito em uma disciplina ofertada em ambiente acadêmico, não é possível fornecer detalhes específicos da linguagem utilizada como código de entrada do compilador. Esta linguagem é uma linguagem de programação ficcional, criada para

No entanto, aqui há uma lista de recursos comuns a outras linguagens de programação e que são suportados por esta linguagem:

* Comandos condicionais (`if ... then`, `else`, `endif`)
* Comandos de repetição (`while ... endw`)
* Declaração e atribuição de variáveis
* Tipagem estática de funções, variáveis e parâmetros (`int`, `string` e `boolean`)
* Operadores matemáticos e lógicos básicos (`+`, `-`, `==`, `<=`, `||`, `&&`, etc)
* Declaração e chamada de funções (com e sem parâmetros e/ou retorno)
* Comentários de uma linha e multibloco (`//` e `/* ... */`)
* Necessidade de declaração de função principal sem parâmetros (`def main`)
* Existência de funções pré-definidas de entrada/saída de dados
* Entre outros

Alguns exemplos de códigos escritos em K se encontram em `examples/inputs`, neste repositório.

### Execução do projeto

A execução do compilador utiliza dois arquivos: um de entrada, com o código-fonte em K a ser compilado, e um de saída, com o código-fonte em C ou as mensagens de erro de compilação. Caso o arquivo de saída esteja em falta, o próprio programa o criará.

Para passar os arquivos para o programa, basta utilizar os argumentos de execução padrão do Java (`String [] Args`) na ordem anteriormente citada.

### Processo de compilação

Durante a compilação, o programa buscará e salvará quaisquer tipos de erros sintáticos, léxicos ou semânticos, baseando-se na gramática original da linguagem K. Estes erros serão, então, escritos no arquivo de saída do programa. Caso nenhum erro tenha sido encontrado, o programa gerará o código-fonte em C correspondente ao código-fonte original.


## Exemplos

* Código com erros sintáticos

Arquivo de entrada:

```
def main{
```

Saída do compilador:

```
teste.in:2: statement inválido
def main{

teste.in:2: } esperado
def main{
```

* Código com erros sintáticos (corrigido)

Arquivo de entrada:

```
def main{}
```

Saída do compilador

```
#include <stdio.h>

void main(){
}

```

Mais exemplos podem ser encontrados na pasta `examples` deste repositório.

## Autores

* [José Vitor Novaes Santos](https://github.com/devNatron)
* [Marcus Vinicius Natrielli Garcia](https://github.com/Infinitemarcus)
* [Victor Fernandes de Oliveira Brayner](https://github.com/victorfob)

