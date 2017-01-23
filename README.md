#Número por extenso em Java

Eis mais um código para escrever números por extenso.

Mas este aqui tem um diferencial importante: **ele funciona!**

##Organização dos números

Um número é organizado em **classes**: (1ª classe = sem nome, 2ª classe = milhar, 3ª classe = milhão, etc).

Cada classe compreende até 3 **algarismos** e, conforme sua posição dentro da classe, um algarismo corresponde a uma determinada **casa**: unidade, dezena, centena.

Por exemplo, considere o número 1.234.567:
<pre>
Classes:     milhão  milhar  sem nome
Algarismos:      1   2 3 4    5 6 7
Casas:           U   C D U    C D U

(sendo U = unidade, D = dezena, C = centena)
</pre>

##Regras de representação por extenso

* Cada algarismo tem uma representação por extenso conforme o seu valor:
<pre>1 = um
2 = dois
9 = nove</pre>
* A representação do algarismo varia conforme a sua casa: 
<pre>1 = um, 10 = dez, 100 = cem
9 = nove, 90 = noventa, 900 = novecentos</pre>
* A própria classe, exceto a primeira, também possui representação por extenso ("*mil*", "*milhão*", "*bilhão*"...): 
<pre>2.000 = dois mil
2.000.000 = dois milhões
2.000.000.000 = dois bilhões</pre>
* Dentro da classe, os algarismos são conectados com "**e**":
<pre>21 = vinte e um
121 = cento e vinte e um</pre>
* As classes são conectadas com **vírgula**:
<pre>1.121.121 = um milhão, cento e vinte e um mil, cento e vinte um</pre>

###Exceções

* Números de 11 a 19 são representados por uma única palavra, não usam o conector "**e**":
<pre>11 = onze, 12 doze, 19 = dezenove</pre>
* O algarismo 1, quando na casa da centena, é representado por "**cem**" quando não há valor na dezena nem na unidade; senão é representado por "**cento**":
<pre>100 = cem
101 = cento e um
110 = cento e dez</pre>
* A unidade da segunda classe só tem representação se for maior do que 1 (porque ninguém quer ecrever "*um* mil"):
<pre>1.000 = mil
2.000 = dois mil</pre>
* A representação da classe, exceto a segunda (a de milhar), flexiona em número: 
<pre>1.000 = mil
2.000 = dois mil
1.000.000 = um milhão
2.000.000 = dois milhões</pre>
* Classes com zero em todas as casas não possuem representação: 
<pre>1.000.000.000.121 = um trilhão, cento e vinte e um</pre>
* A última classe à direita, que tiver valor, é conectada com "**e**" em vez de vírgula se a centena for zero: 
<pre>1.010 = mil e dez
1.010.000 = um milhão e dez mil</pre>
* A última classe à direita, que tiver valor, também é conectada com "**e**" se a centena for maior que zero e não houver valor na dezena nem na unidade: 
<pre>1.100 = mil e cem
1.100.000 = um milhão e cem mil</pre>

###Qualificadores

Um número pode ser qualificado, e o qualificador pode flexionar em número:
<pre>1 dia = um dia
2 dias = dois dias</pre>

###Casas decimais

Um número qualificado pode conter casas decimais, cuja qualificação também pode flexionar em número; e o conector entre a parte inteira e a parte decimal é "e":
<pre>0,01 = um centavo
1,01 = um real e um centavo
2,20 = dois reais e vinte centavos</pre>

A quantidade de casas decimais pode variar:
<pre>1,123 = um quilo, cento e vinte e três gramas</pre>

#O projeto

Não há dependência a nenhuma biblioteca externa ao Java e toda a lógica está implementada em um único arquivo, [NumeroPorExtenso.java](https://github.com/ocaffe/numero-por-extenso/blob/master/src/main/java/org/extenso/NumeroPorExtenso.java). Você pode simplesmente adicionar este arquivo em qualquer projeto seu e produzir um número por extenso  invocando o método `get(número)`, assim:

    System.out.println(NumeroPorExtenso.get(123)); // imprime "cento e vinte e três".

Ou passando um decimal e seus qualificadores de parte inteira e decimal, tanto no plural quanto no singular. Assim:

    // imprime "cento e vinte e três reais e quarenta e cinco centavos".
    System.out.println(NumeroPorExtenso.get(new BigDecimal("123.45"), "real", "reais", "centavo", "centavos"));

Você também pode compilar este projeto e consumir por linha de comando o aplicativo gerado, assim:

    java -jar extenso.jar 123,45
    
A [main class](https://github.com/ocaffe/numero-por-extenso/blob/master/src/main/java/org/extenso/Main.java) do aplicativo está programada para imprimir em reais, então a chamada acima vai imprimir *"cento e vinte e três reais e quarenta e cinco centavos"*.

Todas as regras descritas neste README estão cobertas por testes unitários escritos no arquivo [NumeroPorExtensoTest.java](https://github.com/ocaffe/numero-por-extenso/blob/master/src/test/java/org/extenso/NumeroPorExtensoTest.java).
