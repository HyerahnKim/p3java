package org.example;

public class B {
    private A memberA;
    private C[] memberC;

    // Constructor
    public B(A memberA, C[] memberC) {
        this.memberA = memberA;
        this.memberC = memberC;
    }

    // Getter A
    public A getMemberA() {
        return memberA;
    }

    // Getter C
    public C[] getMemberCArray() {
        return memberC;
    }
}
