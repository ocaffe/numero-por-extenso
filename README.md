#Número por extenso em Java

Eis mais um código para escrever números por extenso.

Mas este aqui tem um diferencial importante: **ele funciona!**

##Ubiquitous language

Para efeito deste projeto, entendemos que os números são organizados em **classes**:

Cada classe compreende 3 algarismos (1ª classe = sem nome, 2ª classe = milhar, 3ª classe = milhão).

E, conforme, sua posição dentro da classe, um algarismo corresponde a uma determinada **casa**: unidade, dezena, centena.

Por exemplo, considere o número 1.234.567:
<pre>
Classes:     milhão  milhar  sem nome
Algarismos:    1     2 3 4    5 6 7
Casas:         U     C D U    C D U

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
* O algarismo 1, quando na casa da centena, é representado por "**cem**" quando não há dezena nem unidade; senão é representado por "**cento**":
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

Todas estas regras e também exceções estão cobertas por testes unitários.
