package com.hsic.sy.adapter;

public class Model {
    public String name;
    public int iconRes;
    public String id;

    public Model(String name, int iconRes,String id) {
        this.name = name;
        this.iconRes = iconRes;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
}
