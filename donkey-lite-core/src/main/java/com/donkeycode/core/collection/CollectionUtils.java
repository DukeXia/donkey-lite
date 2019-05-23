package com.donkeycode.core.collection;

import java.lang.reflect.Array;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.Validate;

/**
 * 集合处理工具类
 *
 * @author yanjun.xue
 * @since 2018年12月31日
 */
public class CollectionUtils {

    private CollectionUtils() {
        // 静态类不可实例化
    }

    /**
     * 以 delimiter 为分隔符将集合转换为字符串
     *
     * @param collection 集合
     * @param delimiter  分隔符
     * @return 连接后的字符串
     */
    public static String join(Collection<String> collection, String delimiter) {
        Validate.notEmpty(collection);
        Validate.notBlank(delimiter, "delimiter is null.");

        return collection.stream().collect(Collectors.joining(delimiter));
    }

    /**
     * 以 conjunction 为分隔符将数组转换为字符串
     *
     * @param array     数组
     * @param delimiter 分隔符
     * @return 连接后的字符串
     */
    public static String join(String[] array, String delimiter) {
        Validate.notEmpty(array);
        Validate.notBlank(delimiter, "delimiter is null.");

        return Stream.of(array).collect(Collectors.joining(delimiter));
    }

    /**
     * 切取部分数据
     *
     * @param <T>             集合元素类型
     * @param surplusAlaDatas 原数据
     * @param partSize        每部分数据的长度
     * @return 切取出的数据或null
     */
    public static <T> List<T> popPart(Stack<T> surplusAlaDatas, int partSize) {
        if (isEmpty(surplusAlaDatas)) {
            return null;
        }

        final List<T> currentAlaDatas = new ArrayList<T>();
        int size = surplusAlaDatas.size();
        // 切割
        if (size > partSize) {
            for (int i = 0; i < partSize; i++) {
                currentAlaDatas.add(surplusAlaDatas.pop());
            }
        } else {
            for (int i = 0; i < size; i++) {
                currentAlaDatas.add(surplusAlaDatas.pop());
            }
        }
        return currentAlaDatas;
    }

    /**
     * 新建一个HashMap
     *
     * @param <T>
     * @param <K>
     * @return HashMap对象
     */
    public static <T, K> HashMap<T, K> newHashMap() {
        return new HashMap<T, K>();
    }

    /**
     * 新建一个HashMap
     *
     * @param size 初始大小，由于默认负载因子0.75，传入的size会实际初始大小为size / 0.75
     * @return HashMap对象
     */
    public static <T, K> HashMap<T, K> newHashMap(int size) {
        return new HashMap<T, K>((int) (size / 0.75));
    }

    /**
     * 新建一个HashSet
     *
     * @param ts 元素数组
     * @return HashSet对象
     */
    @SafeVarargs
    public static <T> HashSet<T> newHashSet(T... ts) {
        HashSet<T> set = new HashSet<T>();
        for (T t : ts) {
            set.add(t);
        }
        return set;
    }

    /**
     * 新建一个HashSet
     *
     * @return HashSet对象
     */
    public static <T> HashSet<T> newHashSet(Collection<T> collection) {
        return new HashSet<T>(collection);
    }

    /**
     * 新建一个ArrayList
     *
     * @param values 数组
     * @return ArrayList对象
     */
    @SafeVarargs
    public static <T> ArrayList<T> newArrayList(T... values) {
        ArrayList<T> arrayList = new ArrayList<T>();
        for (T t : values) {
            arrayList.add(t);
        }
        return arrayList;
    }

    /**
     * 新建一个ArrayList
     *
     * @param collection 集合
     * @return ArrayList对象
     */
    public static <T> ArrayList<T> newArrayList(Collection<T> collection) {
        return new ArrayList<T>(collection);
    }

    /**
     * 将新元素添加到已有数组中<br/>
     * 添加新元素会生成一个新的数组，不影响原数组
     *
     * @param buffer      已有数组
     * @param newElements 新元素
     * @return 新数组
     */
    @SafeVarargs
    public static <T> T[] append(T[] buffer, T... newElements) {
        if (isEmpty(newElements)) {
            return buffer;
        }

        T[] t = resize(buffer, buffer.length + newElements.length);
        System.arraycopy(newElements, 0, t, buffer.length, newElements.length);
        return t;
    }

    /**
     * 生成一个新的重新设置大小的数组
     *
     * @param buffer        原数组
     * @param newSize       新的数组大小
     * @param componentType 数组元素类型
     * @return 调整后的新数组
     */
    public static <T> T[] resize(T[] buffer, int newSize, Class<?> componentType) {
        T[] newArray = newArray(componentType, newSize);
        if (isNotEmpty(buffer)) {
            System.arraycopy(buffer, 0, newArray, 0, Math.min(buffer.length, newSize));
        }
        return newArray;
    }

    /**
     * 新建一个空数组
     *
     * @param componentType 元素类型
     * @param newSize       大小
     * @return 空数组
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] newArray(Class<?> componentType, int newSize) {
        return (T[]) Array.newInstance(componentType, newSize);
    }

    /**
     * 生成一个新的重新设置大小的数组<br/>
     * 新数组的类型为原数组的类型
     *
     * @param buffer  原数组
     * @param newSize 新的数组大小
     * @return 调整后的新数组
     */
    public static <T> T[] resize(T[] buffer, int newSize) {
        return resize(buffer, newSize, buffer.getClass().getComponentType());
    }

    /**
     * 将多个数组合并在一起<br>
     * 忽略null的数组
     *
     * @param arrays 数组集合
     * @return 合并后的数组
     */
    @SafeVarargs
    public static <T> T[] addAll(T[]... arrays) {
        if (arrays.length == 1) {
            return arrays[0];
        }

        int length = 0;
        for (T[] array : arrays) {
            if (array == null) {
                continue;
            }
            length += array.length;
        }
        T[] result = newArray(arrays.getClass().getComponentType().getComponentType(), length);

        length = 0;
        for (T[] array : arrays) {
            if (array == null) {
                continue;
            }
            System.arraycopy(array, 0, result, length, array.length);
            length += array.length;
        }
        return result;
    }

    /**
     * 克隆数组
     *
     * @param array 被克隆的数组
     * @return 新数组
     */
    public static <T> T[] clone(T[] array) {
        Validate.notEmpty(array);
        return array.clone();
    }

    /**
     * 生成一个数字列表<br>
     * 自动判定正序反序
     *
     * @param excludedEnd 结束的数字（不包含）
     * @return 数字列表
     */
    public static int[] range(int excludedEnd) {
        return range(0, excludedEnd, 1);
    }

    /**
     * 生成一个数字列表<br>
     * 自动判定正序反序
     *
     * @param includedStart 开始的数字（包含）
     * @param excludedEnd   结束的数字（不包含）
     * @return 数字列表
     */
    public static int[] range(int includedStart, int excludedEnd) {
        return range(includedStart, excludedEnd, 1);
    }

    /**
     * 生成一个数字列表<br>
     * 自动判定正序反序
     *
     * @param includedStart 开始的数字（包含）
     * @param excludedEnd   结束的数字（不包含）
     * @param step          步进
     * @return 数字列表
     */
    public static int[] range(int includedStart, int excludedEnd, int step) {
        if (includedStart > excludedEnd) {
            includedStart = excludedEnd;
        }

        step = step <= 0 ? 1 : step;

        int deviation = excludedEnd - includedStart;
        int length = deviation / step;
        if (deviation % step != 0) {
            length += 1;
        }
        int[] range = new int[length];
        for (int i = 0; i < length; i++) {
            range[i] = includedStart;
            includedStart += step;
        }
        return range;
    }

    /**
     * 截取数组的部分
     *
     * @param list  被截取的数组
     * @param start 开始位置（包含）
     * @param end   结束位置（不包含）
     * @return 截取后的数组，当开始位置超过最大时，返回null
     */
    public static <T> List<T> sub(List<T> list, int start, int end) {

        Validate.notEmpty(list);

        start = start < 0 ? 0 : start;
        end = end < 0 ? 0 : end;

        if (start > end) {
            int tmp = start;
            start = end;
            end = tmp;
        }

        final int size = list.size();
        if (end > size) {
            if (start >= size) {
                return null;
            }
            end = size;
        }

        return list.subList(start, end);
    }

    /**
     * 截取集合的部分
     *
     * @param list  被截取的数组
     * @param start 开始位置（包含）
     * @param end   结束位置（不包含）
     * @return 截取后的数组，当开始位置超过最大时，返回null
     */
    public static <T> List<T> sub(Collection<T> list, int start, int end) {
        if (isEmpty(list)) {
            return null;
        }

        return sub(new ArrayList<T>(list), start, end);
    }

    /**
     * 数组是否为空
     *
     * @param array 数组
     * @return 是否为空
     */
    public static <T> boolean isEmpty(final T[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 数组是否为空
     *
     * @param array 数组
     * @return 是否为空
     */
    public static boolean isEmpty(final long[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 数组是否为空
     *
     * @param array 数组
     * @return 是否为空
     */
    public static boolean isEmpty(final short[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 数组是否为空
     *
     * @param array 数组
     * @return 是否为空
     */
    public static boolean isEmpty(final char[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 数组是否为空
     *
     * @param array 数组
     * @return 是否为空
     */
    public static boolean isEmpty(final byte[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 数组是否为空
     *
     * @param array 数组
     * @return 是否为空
     */
    public static boolean isEmpty(final double[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 数组是否为空
     *
     * @param array 数组
     * @return 是否为空
     */
    public static boolean isEmpty(final float[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 数组是否为空
     *
     * @param array 数组
     * @return 是否为空
     */
    public static boolean isEmpty(final boolean[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 集合是否为空
     *
     * @param collection 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Map是否为空
     *
     * @param map 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Iterable是否为空
     *
     * @param iterable Iterable对象
     * @return 是否为空
     */
    public static boolean isEmpty(Iterable<?> iterable) {
        return null == iterable || isEmpty(iterable.iterator());
    }

    /**
     * Iterator是否为空
     *
     * @param Iterator Iterator对象
     * @return 是否为空
     */
    public static boolean isEmpty(Iterator<?> Iterator) {
        return null == Iterator || false == Iterator.hasNext();
    }

    /**
     * 数组是否为非空
     *
     * @param array 数组
     * @return 是否为非空
     */
    public static <T> boolean isNotEmpty(final T[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * 数组是否为非空
     *
     * @param array 数组
     * @return 是否为非空
     */
    public static boolean isNotEmpty(final long[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * 数组是否为非空
     *
     * @param array 数组
     * @return 是否为非空
     */
    public static boolean isNotEmpty(final int[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * 数组是否为非空
     *
     * @param array 数组
     * @return 是否为非空
     */
    public static boolean isNotEmpty(final short[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * 数组是否为非空
     *
     * @param array 数组
     * @return 是否为非空
     */
    public static boolean isNotEmpty(final char[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * 数组是否为非空
     *
     * @param array 数组
     * @return 是否为非空
     */
    public static boolean isNotEmpty(final byte[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * 数组是否为非空
     *
     * @param array 数组
     * @return 是否为非空
     */
    public static boolean isNotEmpty(final double[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * 数组是否为非空
     *
     * @param array 数组
     * @return 是否为非空
     */
    public static boolean isNotEmpty(final float[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * 数组是否为非空
     *
     * @param array 数组
     * @return 是否为非空
     */
    public static boolean isNotEmpty(final boolean[] array) {
        return (array != null && array.length != 0);
    }

    /**
     * 集合是否为非空
     *
     * @param collection 集合
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return false == isEmpty(collection);
    }

    /**
     * Map是否为非空
     *
     * @param map 集合
     * @return 是否为非空
     */
    public static <T> boolean isNotEmpty(Map<?, ?> map) {
        return false == isEmpty(map);
    }

    /**
     * Iterable是否为空
     *
     * @param iterable Iterable对象
     * @return 是否为空
     */
    public static boolean isNotEmpty(Iterable<?> iterable) {
        return null != iterable && isNotEmpty(iterable.iterator());
    }

    /**
     * Iterator是否为空
     *
     * @param Iterator Iterator对象
     * @return 是否为空
     */
    public static boolean isNotEmpty(Iterator<?> Iterator) {
        return null != Iterator && Iterator.hasNext();
    }

    /**
     * 映射键值（参考Python的zip()函数）<br>
     * 例如：<br>
     * keys = [a,b,c,d]<br>
     * values = [1,2,3,4]<br>
     * 则得到的Map是 {a=1, b=2, c=3, d=4}<br>
     * 如果两个数组长度不同，则只对应最短部分
     *
     * @param keys   键列表
     * @param values 值列表
     * @return Map
     */
    public static <T, K> Map<T, K> zip(T[] keys, K[] values) {
        if (isEmpty(keys) || isEmpty(values)) {
            return null;
        }

        final int size = Math.min(keys.length, values.length);
        final Map<T, K> map = new HashMap<T, K>((int) (size / 0.75));
        for (int i = 0; i < size; i++) {
            map.put(keys[i], values[i]);
        }

        return map;
    }

    /**
     * 映射键值（参考Python的zip()函数）<br>
     * 例如：<br>
     * keys = a,b,c,d<br>
     * values = 1,2,3,4<br>
     * delimiter = , 则得到的Map是 {a=1, b=2, c=3, d=4}<br>
     * 如果两个数组长度不同，则只对应最短部分
     *
     * @param keys   键列表
     * @param values 值列表
     * @return Map
     */
    public static Map<String, String> zip(String keys, String values, String delimiter) {
        List<String> keyList = Stream.of(keys.split(delimiter)).collect(Collectors.toList());
        List<String> valueList = Stream.of(values.split(delimiter)).collect(Collectors.toList());
        if (valueList.size() != keyList.size()) {
            throw new IllegalArgumentException(String.format("Param keys:values [%s]:[%s] unconformity.", keys, values));
        }

        Map<String, String> map = new HashMap<>(keyList.size());
        for (int i = 0; i < keyList.size(); i++) {
            map.put(keyList.get(i), valueList.get(i));
        }
        return map;
    }

    /**
     * 映射键值（参考Python的zip()函数）<br>
     * 例如：<br>
     * keys = [a,b,c,d]<br>
     * values = [1,2,3,4]<br>
     * 则得到的Map是 {a=1, b=2, c=3, d=4}<br>
     * 如果两个数组长度不同，则只对应最短部分
     *
     * @param keys   键列表
     * @param values 值列表
     * @return Map
     */
    public static <T, K> Map<T, K> zip(Collection<T> keys, Collection<K> values) {
        if (isEmpty(keys) || isEmpty(values)) {
            return null;
        }

        final List<T> keyList = new ArrayList<T>(keys);
        final List<K> valueList = new ArrayList<K>(values);

        final int size = Math.min(keys.size(), values.size());
        final Map<T, K> map = new HashMap<T, K>((int) (size / 0.75));
        for (int i = 0; i < size; i++) {
            map.put(keyList.get(i), valueList.get(i));
        }

        return map;
    }

    /**
     * 数组中是否包含元素
     *
     * @param array 数组
     * @param value 被检查的元素
     * @return 是否包含
     */
    public static <T> boolean contains(T[] array, T value) {
        final Class<?> componetType = array.getClass().getComponentType();
        boolean isPrimitive = false;
        if (null != componetType) {
            isPrimitive = componetType.isPrimitive();
        }
        for (T t : array) {
            if (t == value) {
                return true;
            } else if (false == isPrimitive && null != value && value.equals(t)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将Entry集合转换为HashMap
     *
     * @param entryCollection entry集合
     * @return Map
     */
    public static <T, K> HashMap<T, K> toMap(Collection<Entry<T, K>> entryCollection) {
        HashMap<T, K> map = new HashMap<T, K>();
        for (Entry<T, K> entry : entryCollection) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    /**
     * 将集合转换为排序后的TreeSet
     *
     * @param collection 集合
     * @param comparator 比较器
     * @return treeSet
     */
    public static <T> TreeSet<T> toTreeSet(Collection<T> collection, Comparator<T> comparator) {
        final TreeSet<T> treeSet = new TreeSet<T>(comparator);
        for (T t : collection) {
            treeSet.add(t);
        }
        return treeSet;
    }

    /**
     * 排序集合
     *
     * @param collection 集合
     * @param comparator 比较器
     * @return treeSet
     */
    public static <T> List<T> sort(Collection<T> collection, Comparator<T> comparator) {
        List<T> list = new ArrayList<T>(collection);
        Collections.sort(list, comparator);
        return list;
    }

    /**
     * 将基本类型数组包装为包装类型
     *
     * @param values 基本类型数组
     * @return 包装类型数组
     */
    public static Integer[] wrap(int... values) {
        final int length = values.length;
        Integer[] array = new Integer[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i];
        }
        return array;
    }

    /**
     * 将基本类型数组包装为包装类型
     *
     * @param values 基本类型数组
     * @return 包装类型数组
     */
    public static Long[] wrap(long... values) {
        final int length = values.length;
        Long[] array = new Long[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i];
        }
        return array;
    }

    /**
     * 将基本类型数组包装为包装类型
     *
     * @param values 基本类型数组
     * @return 包装类型数组
     */
    public static Character[] wrap(char... values) {
        final int length = values.length;
        Character[] array = new Character[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i];
        }
        return array;
    }

    /**
     * 将基本类型数组包装为包装类型
     *
     * @param values 基本类型数组
     * @return 包装类型数组
     */
    public static Byte[] wrap(byte... values) {
        final int length = values.length;
        Byte[] array = new Byte[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i];
        }
        return array;
    }

    /**
     * 将基本类型数组包装为包装类型
     *
     * @param values 基本类型数组
     * @return 包装类型数组
     */
    public static Short[] wrap(short... values) {
        final int length = values.length;
        Short[] array = new Short[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i];
        }
        return array;
    }

    /**
     * 将基本类型数组包装为包装类型
     *
     * @param values 基本类型数组
     * @return 包装类型数组
     */
    public static Float[] wrap(float... values) {
        final int length = values.length;
        Float[] array = new Float[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i];
        }
        return array;
    }

    /**
     * 将基本类型数组包装为包装类型
     *
     * @param values 基本类型数组
     * @return 包装类型数组
     */
    public static Double[] wrap(double... values) {
        final int length = values.length;
        Double[] array = new Double[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i];
        }
        return array;
    }

    /**
     * 将基本类型数组包装为包装类型
     *
     * @param values 基本类型数组
     * @return 包装类型数组
     */
    public static Boolean[] wrap(boolean... values) {
        final int length = values.length;
        Boolean[] array = new Boolean[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i];
        }
        return array;
    }

    /**
     * 对象是否为数组对象
     *
     * @param obj 对象
     * @return 是否为数组对象
     */
    public static boolean isArray(Object obj) {
        if (null == obj) {
            throw new NullPointerException("Object check for isArray is null");
        }
        return obj.getClass().isArray();
    }

    /**
     * Iterator转换为Enumeration Adapt the specified <code>Iterator</code> to the
     * <code>Enumeration</code> interface.
     *
     * @param iter Iterator
     * @return Enumeration
     */
    public static <E> Enumeration<E> asEnumeration(final Iterator<E> iter) {
        return new Enumeration<E>() {
            @Override
            public boolean hasMoreElements() {
                return iter.hasNext();
            }

            @Override
            public E nextElement() {
                return iter.next();
            }
        };
    }

    /**
     * Enumeration转换为Iterator<br>
     * Adapt the specified <code>Enumeration</code> to the <code>Iterator</code>
     * interface
     *
     * @param e Enumeration
     * @return Iterator
     */
    public static <E> Iterator<E> asIterator(final Enumeration<E> e) {
        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return e.hasMoreElements();
            }

            @Override
            public E next() {
                return e.nextElement();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static <T> void repeatList(List<T> list) {
        Set<T> set = new HashSet<T>(list);
        list.clear();
        list.addAll(set);
    }

    public static <T> List<List<T>> splitList(List<T> list, int pageSize) {

        Validate.notEmpty(list);

        int listSize = list.size();
        int page = (listSize + (pageSize - 1)) / pageSize;

        List<List<T>> listArray = new ArrayList<>();

        for (int i = 0; i < page; i++) {

            List<T> subList = new ArrayList<>();

            for (int j = 0; j < listSize; j++) {
                int pageIndex = ((j + 1) + (pageSize - 1)) / pageSize;
                if (pageIndex == (i + 1)) {
                    subList.add(list.get(j));
                }
                if ((j + 1) == ((j + 1) * pageSize)) {
                    break;
                }
            }
            listArray.add(subList);
        }
        return listArray;
    }


    public static <E> void forEach(
        Iterable<? extends E> elements, BiConsumer<Integer, ? super E> action) {
        Objects.requireNonNull(elements);
        Objects.requireNonNull(action);

        int index = 0;
        for (E element : elements) {
            action.accept(index++, element);
        }
    }
}
