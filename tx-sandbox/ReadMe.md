# ACID
ACID — это аббревиатура, которая используется для описания четырех ключевых свойств, которым должна соответствовать каждая транзакция в системах управления базами данных (СУБД) для обеспечения их надежности и корректности. Эти свойства включают:

## Atomicity (Атомарность)
Транзакция выполняется полностью или не выполняется вовсе. Если какая-то часть транзакции не может быть завершена, 
то вся транзакция откатывается, и база данных возвращается в исходное состояние.

## Consistency (Согласованность)
Транзакция переводит базу данных из одного согласованного состояния в другое. Это означает, что все правила и ограничения,
установленные для базы данных, остаются соблюденными как до, так и после выполнения транзакции.

## Isolation (Изоляция)
Одновременные транзакции выполняются независимо друг от друга. Промежуточные состояния транзакции невидимы для
других транзакций. Это предотвращает проблемы, связанные с параллельным выполнением транзакций, такие как взаимные
блокировки или чтение несогласованных данных.

## Durability (Устойчивость)
После завершения транзакции и фиксации (коммита) ее результаты сохраняются в базе данных даже в случае сбоя системы.
Это достигается с помощью механизмов журналирования и репликации данных.