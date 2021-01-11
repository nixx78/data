package lv.nixx.samples.json.domain.subclass;

import lombok.Data;

@Data
public class Child2 extends Parent {
    String child2Field;

    public Child2() {
        super();
    }

    public Child2(String parentField, String child2Field) {
        super(parentField, Type.TYPE_2);
        this.child2Field = child2Field;
    }
}
