package lv.nixx.samples.json.domain.subclass;

import lombok.Data;

@Data
public class Child1 extends  Parent {
    String child1Field;

    Child1() {
        super();
    }

    public Child1(String parentField, String child1Field) {
        super(parentField, Type.TYPE_1);
        this.child1Field = child1Field;
    }
}
