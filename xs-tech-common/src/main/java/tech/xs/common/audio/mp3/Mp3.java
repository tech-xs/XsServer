package tech.xs.common.audio.mp3;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ByteUtil;
import tech.xs.common.util.BytesUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * MP3实体类
 *
 * @author 沈家文
 * @date 2021/7/8 17:14
 */
public class Mp3 {

    private Header header;

    private List<Label> infoList = new ArrayList<>();

    public Mp3(byte[] data) {
        this.header = new Header(data);
        Label label;
        int startIndex = 10;
        while ((label = Label.build(data, startIndex)) != null) {
            if (label != null) {
                infoList.add(label);
                startIndex = label.getEndIndex();
            }
        }
        int dataStartIndex = 20;
        if (!infoList.isEmpty()) {
            dataStartIndex = infoList.get(infoList.size() - 1).getEndIndex() + 10;
        }

        byte[] sub = BytesUtil.sub(data, dataStartIndex, dataStartIndex + 20);
        return;
    }

}
