package tech.xs.common.audio.mp3;

import tech.xs.common.util.BytesUtil;

/**
 * 标签
 *
 * @author 沈家文
 * @date 2021/5/13 19:16
 */
public class Label {

    private String id;

    private String name;

    private int size;

    private byte[] value;

    private int startIndex;

    private int endIndex;

    public Label() {

    }

    public static Label build(byte[] data, int startIndex) {
        Label label = new Label();
        label.setStartIndex(startIndex);
        String id = new String(BytesUtil.sub(data, startIndex, startIndex + 4));
        switch (id) {
            case "TIT2": {
                label.setName("标题");
                break;
            }
            case "TPE1": {
                label.setName("作者");
                break;
            }
            case "TALB": {
                label.setName("专辑");
                break;
            }
            case "TRCK": {
                label.setName("音轨格式");
                break;
            }
            case "TYER": {
                label.setName("年代");
                break;
            }
            case "TCON": {
                label.setName("类型");
                break;
            }
            case "COMM": {
                label.setName("备注");
                break;
            }
            case "TXXX": {
                label.setName("用户定义的文字信息");
                break;
            }
            case "TPOS": {
                label.setName("标签");
                break;
            }
            case "APIC": {
                label.setName("附图片");
                break;
            }
            default: {
                break;
            }
        }
        if (Constant.LABEL_END.equals(id)) {
            return null;
        }
        label.setId(id);
        label.setSize(BytesUtil.toInt(data, startIndex + 4, 4));
        label.setEndIndex(startIndex + 10 + label.getSize());
        label.setValue(BytesUtil.sub(data, startIndex + 13, label.getEndIndex()));
        return label;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }
}
