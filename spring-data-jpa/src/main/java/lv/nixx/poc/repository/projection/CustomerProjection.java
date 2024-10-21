package lv.nixx.poc.repository.projection;


/*
В данном интерфейсе определяем поля, в которое будет происходить
маппинг результата выполнения запроса.
 */
public interface CustomerProjection {

    Long getId();

    String getNameSurname();

}
