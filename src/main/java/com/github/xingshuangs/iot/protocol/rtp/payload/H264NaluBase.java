package com.github.xingshuangs.iot.protocol.rtp.payload;


import com.github.xingshuangs.iot.protocol.common.IObjectByteArray;
import com.github.xingshuangs.iot.protocol.common.buff.ByteWriteBuff;
import lombok.Data;

/**
 * 基类数据包
 *
 * @author xingshuang
 */
@Data
public class H264NaluBase implements IObjectByteArray {

    /**
     * 头
     */
    protected H264NaluHeader header;

    @Override
    public int byteArrayLength() {
        return this.header.byteArrayLength();
    }

    @Override
    public byte[] toByteArray() {
        return ByteWriteBuff.newInstance(this.byteArrayLength())
                .putBytes(this.header.toByteArray())
                .getData();
    }
}
