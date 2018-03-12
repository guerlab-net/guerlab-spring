package net.guerlab.spring.cloud.commons.list;

import java.util.Collections;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 列表对象
 *
 * @author guer
 *
 * @param <T>
 *            数据类型
 */
@ApiModel(value = "列表对象", description = "包含数据列表，数据总数，查询内容数量")
public class ListObject<T> {

    @SuppressWarnings("rawtypes")
    public static ListObject EMPTY = new EmptyListObject<>();

    @SuppressWarnings("unchecked")
    public static final <T> ListObject<T> empty() {
        return EMPTY;
    }

    private static class EmptyListObject<E> extends ListObject<E> {

        @Override
        public void setList(
                List<E> list) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setCount(
                long count) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setPageSize(
                long pageSize) {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * 数据列表
     */
    @ApiModelProperty(value = "数据列表", required = true)
    private List<T> list = Collections.emptyList();

    /**
     * 数据总数
     */
    @ApiModelProperty(value = "数据总数", required = true)
    private long count;

    /**
     * 查询内容数量
     */
    @ApiModelProperty(value = "查询内容数量", required = true)
    private long pageSize = 10;

    /**
     * 无参构造
     */
    public ListObject() {
        /*
         * not to do something
         */
    }

    /**
     * 通过分页尺寸构造对象
     *
     * @param pageSize
     *            分页尺寸
     */
    public ListObject(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 通过分页尺寸、数据总数构造对象
     *
     * @param pageSize
     *            分页尺寸
     * @param count
     *            数据总数
     */
    public ListObject(long pageSize, long count) {
        this.pageSize = pageSize;
        this.count = count;
    }

    /**
     * 通过分页尺寸、数据总数、数据读取命令构造对象
     *
     * @param pageSize
     *            分页尺寸
     * @param count
     *            数据总数
     * @param command
     *            数据读取命令
     */
    public ListObject(long pageSize, long count, ReadDataListCommand<T> command) {
        this(pageSize, count);

        if (count <= 0 || command == null) {
            return;
        }

        List<T> dataList = command.getData();
        if (dataList != null) {
            list = dataList;
        }
    }

    /**
     * 返回数据列表
     *
     * @return 数据列表
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 设置数据列表
     *
     * @param list
     *            数据列表
     */
    public void setList(
            List<T> list) {
        this.list = list;
    }

    /**
     * 返回数据总数
     *
     * @return 数据总数
     */
    public long getCount() {
        return count;
    }

    /**
     * 设置数据总数
     *
     * @param count
     *            数据总数
     */
    public void setCount(
            long count) {
        this.count = count;
    }

    /**
     * 返回分页尺寸
     *
     * @return 分页尺寸
     */
    public long getPageSize() {
        return pageSize;
    }

    /**
     * 设置分页尺寸
     *
     * @param pageSize
     *            分页尺寸
     */
    public void setPageSize(
            long pageSize) {
        this.pageSize = pageSize;
    }
}
