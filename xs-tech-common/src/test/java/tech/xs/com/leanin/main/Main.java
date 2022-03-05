package tech.xs.common;

import cn.hutool.core.io.FileUtil;
import tech.xs.common.audio.mp3.Mp3;

public class Main {

    public static void main(String[] args) {

        byte[] bytes = FileUtil.readBytes("C:\\Users\\13246\\Downloads\\aaa.mp3");
        System.out.println(new Mp3(bytes));
    }
}
