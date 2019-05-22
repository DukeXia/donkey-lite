package com.donkeycode.boot.annotation;

public enum OperateType {

    ADD(1), MODIFY(2), DELETE(3), DELETES(4);

    int value;

    OperateType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
