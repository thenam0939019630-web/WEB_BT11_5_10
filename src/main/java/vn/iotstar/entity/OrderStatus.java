package vn.iotstar.entity;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class OrderStatus {
    public static final Map<Integer, String> VALUES;
    static {
        Map<Integer, String> values = new LinkedHashMap<>();
        values.put(1, "Đơn hàng mới");
        values.put(2, "Đã xác nhận");
        values.put(3, "Chuẩn bị hàng");
        values.put(4, "Vận chuyển");
        values.put(5, "Giao hàng");
        values.put(6, "Đã giao");
        values.put(7, "Đơn hàng hủy");
        values.put(8, "Đơn hàng hoàn");
        VALUES = Collections.unmodifiableMap(values);
    }
    private OrderStatus() { }
    public static String label(Integer status) {
        return status != null && status == 0 ? "Giỏ hàng" : VALUES.getOrDefault(status, "Không xác định");
    }
}
