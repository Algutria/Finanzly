package com.finanzlymobile.finanzlymobile;

import android.os.Parcel;
import android.os.Parcelable;

class Operation implements Parcelable {
    private String id;
    private String name;
    private double value;
    private Type type;

    public Operation(){}

    public Operation(String name, double value, Type type) {
        this.id = id;

        this.name = name;
        this.value = value;
        this.type = type;
    }

    protected Operation(Parcel in) {
        id = in.readString();
        name = in.readString();
        value = in.readDouble();
        type = Type.valueOf(in.readString());
    }

    public static final Creator<Operation> CREATOR = new Creator<Operation>() {
        @Override
        public Operation createFromParcel(Parcel in) {
            return new Operation(in);
        }

        @Override
        public Operation[] newArray(int size) {
            return new Operation[size];
        }
    };

    @Override
    public String toString() {
        return "Operation{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", type=" + type +
                '}';
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeDouble(value);
        dest.writeString(type.name());
    }

    public enum Type {
        INCOME, EXPENSE
    }
}
