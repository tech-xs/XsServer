package tech.xs.common.audio.mp3;

import tech.xs.common.util.BytesUtil;

/**
 * Mp3文件协议头
 *
 * @author 沈家文
 * @date 2021/7/8 17:15
 */
public class Header {

    /**
     * header
     */
    private String header;

    /**
     * 版本
     */
    private int version;

    /**
     * 副版本号
     */
    private int revision;

    private char flag;

    private int size;

    public Header() {

    }

    public Header(byte[] data) {
        this.header = new String(BytesUtil.sub(data, 0, 3));
        this.version = data[3];
        this.revision = data[4];
        this.flag = (char) data[5];
        this.size = BytesUtil.toInt(data, 6, 4);
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public char getFlag() {
        return flag;
    }

    public void setFlag(char flag) {
        this.flag = flag;
    }
}
