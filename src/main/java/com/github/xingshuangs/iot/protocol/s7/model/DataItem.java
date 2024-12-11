package com.github.xingshuangs.iot.protocol.s7.model;


import com.github.xingshuangs.iot.protocol.s7.enums.EDataVariableType;
import com.github.xingshuangs.iot.protocol.s7.enums.EReturnCode;
import com.github.xingshuangs.iot.utils.ShortUtil;
import lombok.Data;

import java.util.Arrays;

/**
 * 返回数据
 *
 * @author xingshuang
 */
@Data
public class DataItem implements IByteArray {

    /**
     * 返回码 <br>
     * 字节大小：1 <br>
     * 字节序数：0
     */
    private EReturnCode returnCode = EReturnCode.SUCCESS;

    /**
     * 变量类型 <br>
     * 字节大小：1 <br>
     * 字节序数：1
     */
    private EDataVariableType variableType = EDataVariableType.BYTE_WORD_DWORD;

    /**
     * 数据长度 <br>
     * 字节大小：2 <br>
     * 字节序数：2-3
     */
    private int count = 0x0000;

    /**
     * 数据内容
     */
    private byte[] data = new byte[0];

    @Override
    public int byteArrayLength() {
        return 4 + this.data.length;
    }

    @Override
    public byte[] toByteArray() {
        byte[] res = new byte[4 + this.data.length];
        byte[] countBytes = ShortUtil.toByteArray((short) this.count);

        res[0] = this.returnCode.getCode();
        res[1] = this.variableType.getCode();
        res[2] = countBytes[0];
        res[3] = countBytes[1];

        if (this.data.length > 0) {
            System.arraycopy(this.data, 0, res, 4, this.data.length);
        }
        return res;
    }

    public static DataItem fromBytes(final byte[] data) {
        DataItem dataItem = new DataItem();
        dataItem.returnCode = EReturnCode.from(data[0]);
        dataItem.variableType = EDataVariableType.from(data[1]);
        dataItem.count = ShortUtil.toUInt16(data, 2);
        dataItem.data = Arrays.copyOfRange(data, 4, 4 + dataItem.count);
        return dataItem;
    }
}
