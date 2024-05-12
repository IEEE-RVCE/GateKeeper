package org.ieeervce.gatekeeper.entity;

public enum RoleValue {
    SocietyExecom(0),
    MainExecom(1),
    FacultyAdvisor(2),
    FinanceHead(3),
    BranchCounsellor(4),
    Admin(5);

    private int value;

    RoleValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
