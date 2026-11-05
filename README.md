# Cantina CESAR (PoC)



## O que é o app
Nos 15 minutos de intervalo, a fila da cantina come metade do tempo. Quem quer um item rápido (coxinha) espera na mesma fila de quem pediu um item demorado (tapioca).

O **Cantina CESAR** é um app de console com dois modos:

1. **Fazer um pedido:** o aluno diz quantos minutos faltam e o app responde, item a item, **"ainda dá tempo?"** (já reservando 3 min para comer). O tempo é calculado pela **fila mais lenta** (fila rápida e fila de preparo trabalham em paralelo). Depois vêm pagamento (Pix, cartão ou vale) e status PAGO → EM PREPARO → PRONTO.
2. **Simulador do intervalo:** 30 alunos chegam ao longo de 15 min; mostra quantos conseguem comer com **fila única** e com **filas separadas** (mesmos 2 atendentes nos dois cenários), numa turma e na média de 1000 turmas.



## Decisões da stack
| Decisão | Escolha | Motivo |
|---|---|---|
| Linguagem | Java 17 | OO deixa os princípios do cap. 5 visíveis no código |
| Interface | Console | PoC rápida: foco no projeto, não na tela |
| Build | `javac` puro | sem dependências, roda em qualquer máquina com JDK |
| Dados | Em memória | sem banco nem deploy |
| IA | Claude | gerou a base; a equipe revisa e explica cada parte |

## Papel dos integrantes
| Integrante | Papel |
|---|---|
| Athos | Código principal e repositório no GitHub |
| Dani | Vídeo: gravação, edição e legenda |
| Arthur | Classes de itens e filas |
| Almeida | Classes de pagamento |
| Pierre | README e documentação (`docs/`) |
| Caio | Testes e conferência dos princípios no código |
| André | Roteiro do vídeo e pesquisa (entrevistas, estimativas) |

## Como rodar (Java 17+)
Pré-requisito: JDK 17 ou mais novo (`java -version` e `javac -version`). Dentro da pasta que contém `src/`:
```bash
javac -encoding UTF-8 -d out src/*.java
java -Dfile.encoding=UTF-8 -cp out Main
```
No PowerShell, troque `src/*.java` por `(Get-ChildItem src\*.java)`.

**Testes** (sem biblioteca externa):
```bash
javac -encoding UTF-8 -d out src/*.java test/*.java
java -cp out TestesCantina
```

## Onde estão os princípios no código
| Princípio | Onde | Como aparece |
|---|---|---|
| Responsabilidade Única | `Main` (só monta), `Console` (só lê), `Tela` (só mostra), `FluxoDePedido` (só orquestra), `PoliticaDeTempo` (regra do "dá tempo?"), `Cardapio` (itens) | o `Main` deixou de ter tela, regra de tempo e cardápio |
| Aberto/Fechado | `Fila`, `EstimadorDeTempo`, `Item`, `Pagamento`, `Modo` | `EstimadorPorFilas` e `ModoSimulador` entraram como classes novas, sem alterar o fluxo do pedido |
| Inversão de Dependências | `PoliticaDeTempo` depende de `EstimadorDeTempo`; `FluxoDePedido` depende de interfaces e catálogos | só o `Main` conhece as classes concretas |
| Segregação de Interfaces | `Item`, `Pagamento`, `Fila`, `EstimadorDeTempo`, `Modo` | todas com 1 a 4 métodos |
| Ocultamento de informação | `Pedido` (itens e status privados), `CartaoCredito.limite`, `ValeRefeicao.saldo` | `pedido.itens()` devolve cópia imutável; status só muda por `pagarCom` e `avancar` |
| Coesão | `StatusPedido` (transições num lugar só), `Dinheiro` (formatação) | cada classe usa todos os seus atributos |
| Acoplamento | `FluxoDePedido` não conhece `Coxinha`, `Pix` nem `FilaRapida` | trocar a fila no `Main` não toca no fluxo |
| Lei de Demeter | `pedido.pagarCom(pagamento)`, `pedido.itensCom(item)` | ninguém encadeia `pedido.getX().getY()` |
| Substituição de Liskov | `FilaUnica`, `FilaRapida`, `FilaPreparo` e os dois estimadores | qualquer um entra no lugar do outro sem quebrar `PoliticaDeTempo` nem `Simulador` |
| Composição em vez de herança | `PoliticaDeTempo` tem um estimador; `EstimadorPorFilas` tem filas | nenhuma classe de domínio herda de outra (só implementam interfaces) |
| Integridade conceitual | itens, pagamentos e filas seguem o mesmo padrão | catálogos `Cardapio` e `MeiosDePagamento` são análogos |
| Métricas | `docs/ANTES_E_DEPOIS.md` | complexidade ciclomática do `Main`: 11 → 2 |

Mais detalhes: `docs/ESTRUTURA.md` (classes e fluxo), `docs/ANTES_E_DEPOIS.md` 