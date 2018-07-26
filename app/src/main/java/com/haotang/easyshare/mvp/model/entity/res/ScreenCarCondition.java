package com.haotang.easyshare.mvp.model.entity.res;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/26 13:57
 */
public class ScreenCarCondition {
    private int id;
    private int classId;
    private String name;
    private boolean select;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public ScreenCarCondition() {
    }

    public ScreenCarCondition(int id, int classId, String name, boolean select) {
        this.id = id;
        this.classId = classId;
        this.name = name;
        this.select = select;
    }
}
